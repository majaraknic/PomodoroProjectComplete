package eu.execom.pomodoro.web;

import eu.execom.pomodoro.model.Pomodoro;
import eu.execom.pomodoro.model.PomodoroHistory;
import eu.execom.pomodoro.model.Team;
import eu.execom.pomodoro.model.User;
import eu.execom.pomodoro.model.enumerations.Status;
import eu.execom.pomodoro.service.PomodoroHistoryService;
import eu.execom.pomodoro.service.PomodoroService;
import eu.execom.pomodoro.service.TeamService;
import eu.execom.pomodoro.service.UserService;
import eu.execom.pomodoro.web.dto.PomodoroDto;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/pomodoro")
public class PomodoroController {

    private static final ModelMapper MODEL_MAPPER = new ModelMapper();

    @Autowired
    private PomodoroService pomodoroService;

    @Autowired
    private PomodoroHistoryService pomodoroHistoryService;

    @Autowired
    private UserService userService;

    @Autowired
    private TeamService teamService;

    @GetMapping
    public ResponseEntity<List<PomodoroDto>> getAll() {
        List<Pomodoro> pomodoros = pomodoroService.getAll();
        List<PomodoroDto> pomodoroDtos = pomodoros.stream().map(PomodoroDto::new).collect(Collectors.toList());
        return new ResponseEntity<>(pomodoroDtos, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PomodoroDto> getById(@PathVariable Long id) {
        Pomodoro pomodoro = pomodoroService.getById(id);
        if (pomodoro.getStatus().equals(Status.ACTIVE_TASK)) {
            pomodoro.setStartDateTime(pomodoro.getStartDateTime().plusMinutes(25));
        } else if (pomodoro.getStatus().equals(Status.ACTIVE_PAUSE)) {
            pomodoro.setStartDateTime(pomodoro.getStartDateTime().plusMinutes(5));
        }
        return new ResponseEntity(new PomodoroDto(pomodoro), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<PomodoroDto> create(@RequestBody PomodoroDto pomodoroDto) {
        Pomodoro pomodoro = MODEL_MAPPER.map(pomodoroDto, Pomodoro.class);
        Pomodoro result = pomodoroService.save(pomodoro);

        return new ResponseEntity(new PomodoroDto(result), HttpStatus.CREATED);
    }

    @PutMapping("/{userId}")
    public ResponseEntity<PomodoroDto> update(@RequestBody PomodoroDto pomodoroDto, @PathVariable Long userId) {
        Pomodoro pomodoro = MODEL_MAPPER.map(pomodoroDto, Pomodoro.class);
        Pomodoro result = pomodoroService.save(pomodoro);

        User currentUser = this.userService.getById(userId);
        Team currentTeam = this.teamService.getByUserId(currentUser.getId());
        PomodoroHistory ph = new PomodoroHistory();
        ph.setUser(currentUser);
        ph.setTeam(currentTeam);
        ph.setActivityTime(pomodoroDto.getStartDateTime());
        if (result.getStatus().equals(Status.ACTIVE_TASK)) {
            ph.setActivity("Started new task");
            this.pomodoroHistoryService.save(new PomodoroHistory((ph)));
            result.setStartDateTime(result.getStartDateTime().plusMinutes(25));
        } else if (result.getStatus().equals(Status.ACTIVE_PAUSE)) {
            ph.setActivity("Started pause");
            this.pomodoroHistoryService.save(new PomodoroHistory((ph)));
            result.setStartDateTime(result.getStartDateTime().plusMinutes(5));
        } else if (result.getStatus().equals(Status.STOPPED)) {
            ph.setActivity("Stopped his task");
            this.pomodoroHistoryService.save(new PomodoroHistory((ph)));
        }
        return new ResponseEntity(new PomodoroDto(result), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity delete(@PathVariable Long id) {
        pomodoroService.delete(id);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }
}

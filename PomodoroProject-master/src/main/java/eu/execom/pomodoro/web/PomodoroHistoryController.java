package eu.execom.pomodoro.web;

import eu.execom.pomodoro.model.Pomodoro;
import eu.execom.pomodoro.model.PomodoroHistory;
import eu.execom.pomodoro.service.PomodoroHistoryService;
import eu.execom.pomodoro.service.PomodoroService;
import eu.execom.pomodoro.web.dto.PomodoroDto;
import eu.execom.pomodoro.web.dto.PomodoroHistoryDto;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/pomodoro-history")
public class PomodoroHistoryController {
    private static final ModelMapper MODEL_MAPPER = new ModelMapper();

    @Autowired
    private PomodoroHistoryService pomodoroHistoryService;

    @GetMapping("/{teamId}/{userId}")
    public ResponseEntity<List<PomodoroHistoryDto>> findAllTeammatesActivities(@PathVariable Long teamId, @PathVariable Long userId) {
        List<PomodoroHistory> pomodorosHistory = pomodoroHistoryService.getTeammatesActivities(teamId, userId);
        List<PomodoroHistoryDto> pomodoroHistoryDtos = pomodorosHistory.stream().map(PomodoroHistoryDto::new).collect(Collectors.toList());
        return new ResponseEntity<>(pomodoroHistoryDtos, HttpStatus.OK);
    }

}

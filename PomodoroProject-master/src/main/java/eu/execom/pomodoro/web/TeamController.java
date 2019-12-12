package eu.execom.pomodoro.web;


import eu.execom.pomodoro.exceptions.UserAlreadyInTeamException;
import eu.execom.pomodoro.model.Team;
import eu.execom.pomodoro.model.User;
import eu.execom.pomodoro.service.TeamService;
import eu.execom.pomodoro.service.UserService;
import eu.execom.pomodoro.web.dto.TeamDto;
import eu.execom.pomodoro.web.dto.TeamMemberDto;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/team")
public class TeamController {

    private static final ModelMapper MODEL_MAPPER = new ModelMapper();

    @Autowired
    private TeamService teamService;

    @Autowired
    private UserService userService;

    @GetMapping
    public ResponseEntity<List<TeamDto>> getAll() {
        List<Team> teams = teamService.getAll();
        List<TeamDto> teamDtos = teams.stream().map(TeamDto::new).collect(Collectors.toList());
        return new ResponseEntity<>(teamDtos, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TeamDto> getById(@PathVariable Long id) {
        Team team = teamService.getById(id);
        return new ResponseEntity(new TeamDto(team), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<TeamDto> create(@RequestBody TeamDto teamDto) {
        Team team = MODEL_MAPPER.map(teamDto, Team.class);

        Team result = teamService.save(team);

        return new ResponseEntity(new TeamDto(result), HttpStatus.CREATED);
    }

    @PutMapping
    public ResponseEntity<TeamDto> update(@RequestBody TeamDto teamDto) {
        Team team = MODEL_MAPPER.map(teamDto, Team.class);
        Team result = teamService.save(team);
        return new ResponseEntity(new TeamDto(result), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity delete(@PathVariable Long id) {
        teamService.delete(id);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/email")
    public ResponseEntity<TeamDto> getTeamByUserEmail(@RequestParam String email) {
        User currentUser = userService.getByEmail(email);
        Team team = teamService.getByUserId(currentUser.getId());
        TeamDto teamDto = MODEL_MAPPER.map(team, TeamDto.class);
        return new ResponseEntity(teamDto, HttpStatus.OK);
    }

    @PostMapping("/add-user")
    public ResponseEntity<TeamDto> addNewTeamMember(@RequestBody TeamMemberDto teamMemberDto) {
        User user = userService.getByEmail(teamMemberDto.getEmail());
        Team team = teamService.getById(teamMemberDto.getTeamId());
        List<Team> allTeams = teamService.getAll();
        Team teamRet;
        Boolean alreadyInTeam = false;

        for (Team t: allTeams) {
            for (User u : t.getUsers()) {
                if (u.getEmail().equals(teamMemberDto.getEmail())) {
                    alreadyInTeam = true;
                    break;
                }
            }
        }
        if (alreadyInTeam) {
            throw new UserAlreadyInTeamException("This user is already in team.");
        } else {
            team.getUsers().add(user);
            teamRet = teamService.update(team);
        }
        return new ResponseEntity(new TeamDto(teamRet), HttpStatus.OK);
    }

    @PostMapping("/delete-user")
    public ResponseEntity<TeamDto> leaveTeam(@RequestBody TeamMemberDto teamMemberDto) {
        User user = userService.getByEmail(teamMemberDto.getEmail());
        Team team = teamService.getById(teamMemberDto.getTeamId());
        team.getUsers().remove(user);
        Team teamRet = teamService.update(team);
        return new ResponseEntity(new TeamDto(teamRet), HttpStatus.OK);
    }
}

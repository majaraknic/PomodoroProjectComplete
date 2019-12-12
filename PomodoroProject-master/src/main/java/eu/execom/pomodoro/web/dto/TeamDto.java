package eu.execom.pomodoro.web.dto;

import eu.execom.pomodoro.model.Team;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

/* Data transfer object for entity Team */

@Component
public class TeamDto {

    private Long id;

    private String name;

    private List<UserDto> users;

    public TeamDto() {
        super();
    }

    public TeamDto(Team team) {
        this.id = team.getId();
        this.name = team.getName();
        this.users = team.getUsers().stream().map(UserDto::new).collect(Collectors.toList());
    }

    public List<UserDto> getUsers() {
        return users;
    }

    public void setUsers(List<UserDto> users) {
        this.users = users;
    }
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}

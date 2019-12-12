package eu.execom.pomodoro.web.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import eu.execom.pomodoro.model.Pomodoro;
import eu.execom.pomodoro.model.User;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

/* Data transfer object for entity User */

@Component
public class UserDto {

    private Long id;

    private String email;

    private String fullName;

    private String password;

    private PomodoroDto pomodoro;

    private static final ModelMapper MODEL_MAPPER = new ModelMapper();

    public UserDto() {
        super();
    }

    public UserDto(User user) {
        this.id = user.getId();
        this.email = user.getEmail();
        this.password = user.getPassword();
        this.fullName = user.getFullName();
        this.pomodoro = MODEL_MAPPER.map(user.getPomodoro(), PomodoroDto.class);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public PomodoroDto getPomodoro() {
        return pomodoro;
    }

    public void setPomodoro(PomodoroDto pomodoro) {
        this.pomodoro = pomodoro;
    }
}

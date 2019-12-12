package eu.execom.pomodoro.web.dto;

import eu.execom.pomodoro.model.PomodoroHistory;
import eu.execom.pomodoro.model.Team;
import eu.execom.pomodoro.model.User;
import org.modelmapper.ModelMapper;

import javax.persistence.*;
import java.time.LocalDateTime;

public class PomodoroHistoryDto {

    private static final ModelMapper MODEL_MAPPER = new ModelMapper();

    private Long id;

    private TeamDto team;

    private UserDto user;

    private LocalDateTime activityTime;

    private String activity;

    public PomodoroHistoryDto(PomodoroHistory pomodoroHistory) {
        this.id = pomodoroHistory.getId();
        this.team = MODEL_MAPPER.map(pomodoroHistory.getTeam(), TeamDto.class);
        this.user = MODEL_MAPPER.map(pomodoroHistory.getUser(), UserDto.class);
        this.activityTime = pomodoroHistory.getActivityTime();
        this.activity = pomodoroHistory.getActivity();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public TeamDto getTeam() {
        return team;
    }

    public void setTeam(TeamDto team) {
        this.team = team;
    }

    public UserDto getUser() {
        return user;
    }

    public void setUser(UserDto user) {
        this.user = user;
    }

    public LocalDateTime getActivityTime() {
        return activityTime;
    }

    public void setActivityTime(LocalDateTime activityTime) {
        this.activityTime = activityTime;
    }

    public String getActivity() {
        return activity;
    }

    public void setActivity(String activity) {
        this.activity = activity;
    }
}

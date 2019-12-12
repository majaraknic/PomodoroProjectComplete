package eu.execom.pomodoro.model;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
public class PomodoroHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Long id;

    @OneToOne(cascade = CascadeType.ALL)
    private Team team;

    @OneToOne(cascade = CascadeType.ALL)
    private User user;

    @Column
    private LocalDateTime activityTime;

    @Column
    private String activity;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Team getTeam() {
        return team;
    }

    public void setTeam(Team team) {
        this.team = team;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
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

    public PomodoroHistory(PomodoroHistory pomodoroHistory) {
        this.id = pomodoroHistory.id;
        this.team = pomodoroHistory.team;
        this.user = pomodoroHistory.user;
        this.activityTime = pomodoroHistory.activityTime;
        this.activity = pomodoroHistory.activity;
    }

    public PomodoroHistory() {

    }
}

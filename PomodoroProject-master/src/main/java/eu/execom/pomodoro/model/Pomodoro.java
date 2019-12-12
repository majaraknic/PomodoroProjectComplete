package eu.execom.pomodoro.model;

import eu.execom.pomodoro.model.enumerations.Status;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
public class Pomodoro {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Long id;

    @Column
    private LocalDateTime startDateTime;

    @Column
    private Status status;

    public Pomodoro() {
        super();
    }

    public Pomodoro(Pomodoro pomodoro) {
        this.id = pomodoro.getId();
        this.startDateTime = pomodoro.getStartDateTime();
        this.status = pomodoro.getStatus();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public LocalDateTime getStartDateTime() {
        return startDateTime;
    }

    public void setStartDateTime(LocalDateTime startDateTime) {
        this.startDateTime = startDateTime;
    }
}


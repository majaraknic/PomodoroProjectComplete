package eu.execom.pomodoro.web.dto;

import eu.execom.pomodoro.model.Pomodoro;
import eu.execom.pomodoro.model.enumerations.Status;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

/* Data transfer object for entity Pomodoro */

@Component
public class PomodoroDto {

    private Long id;

    private LocalDateTime startDateTime;

    private Status status;

    public PomodoroDto() {
        super();
    }

    public PomodoroDto(Pomodoro pomodoro) {
        this.id = pomodoro.getId();
        this.status = pomodoro.getStatus();
        this.startDateTime = pomodoro.getStartDateTime();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDateTime getStartDateTime() {
        return startDateTime;
    }

    public void setStartDateTime(LocalDateTime startDateTime) {
        this.startDateTime = startDateTime;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }
}

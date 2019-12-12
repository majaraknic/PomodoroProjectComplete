package eu.execom.pomodoro.repository;

import eu.execom.pomodoro.model.Pomodoro;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PomodoroRepository extends JpaRepository<Pomodoro, Long> {
}

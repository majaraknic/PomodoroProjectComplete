package eu.execom.pomodoro.repository;

import eu.execom.pomodoro.model.PomodoroHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PomodoroHistoryRepository extends JpaRepository<PomodoroHistory, Long> {

    @Query(
            value = "SELECT * FROM pomodoro_history ph WHERE ph.team_id = :teamId AND ph.user_id != :userId",
            nativeQuery = true)
    List<PomodoroHistory> findAllTeammatesActivities(@Param("teamId") Long teamId, @Param("userId") Long userId);

}

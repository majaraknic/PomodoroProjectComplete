package eu.execom.pomodoro.repository;

import eu.execom.pomodoro.model.Team;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TeamRepository extends JpaRepository<Team, Long> {
    boolean existsByName(String name);

    Team getByUsersId(Long usersId);
}

package eu.execom.pomodoro.repository;

import eu.execom.pomodoro.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Long> {

    boolean existsByEmail(String email);

    boolean existsById(Long id);

    User findByEmail(String email);

//    List<User> getByTeamId(Long teamId);

//    User deleteByTeamId(Long teamId);

    User getByEmail(String email);
}

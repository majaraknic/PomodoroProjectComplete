package eu.execom.pomodoro.service;

import eu.execom.pomodoro.exceptions.NoEntityException;
import eu.execom.pomodoro.exceptions.SameStringException;
import eu.execom.pomodoro.model.Team;
import eu.execom.pomodoro.model.User;
import eu.execom.pomodoro.repository.TeamRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TeamService {

    @Autowired
    private TeamRepository teamRepository;

    private Team savedTeam;

    public List<Team> getAll() {
        return teamRepository.findAll();
    }

    public Team getById(Long id) {
        return teamRepository.getOne(id);
    }

    public Team save(Team team) {

        if(teamRepository.existsByName(team.getName())) {
            throw new SameStringException("Teams must have different names!");
        }
        savedTeam = teamRepository.save(team);
        return savedTeam;
    }

    public void delete(Long id) {
        if (!teamRepository.existsById(id)) {
            throw new NoEntityException("Team with this id doesn't exist in database.");
        }
        teamRepository.deleteById(id);
    }

    public Team update(Team team) {
        return teamRepository.save(team);
    }

    public Team getByUserId(Long id) {
        Team team = teamRepository.getByUsersId(id);
        if (team == null) {
            throw new NoEntityException("User is not a member of any team.");
        }
        return team;
    }
}

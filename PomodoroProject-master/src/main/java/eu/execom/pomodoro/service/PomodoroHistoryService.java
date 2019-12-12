package eu.execom.pomodoro.service;


import eu.execom.pomodoro.exceptions.NoEntityException;
import eu.execom.pomodoro.model.Pomodoro;
import eu.execom.pomodoro.model.PomodoroHistory;
import eu.execom.pomodoro.model.enumerations.Status;
import eu.execom.pomodoro.repository.PomodoroHistoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class PomodoroHistoryService {

    @Autowired
    private PomodoroHistoryRepository pomodoroHistoryRepository;

    public PomodoroHistory save(PomodoroHistory pomodoroHistory) {

        return pomodoroHistoryRepository.save(pomodoroHistory);
    }

    public List<PomodoroHistory> getTeammatesActivities(Long teamId, Long userId) {
        List<PomodoroHistory> ret = pomodoroHistoryRepository.findAllTeammatesActivities(teamId, userId);

        if (ret.size() == 0 || ret == null) {
            throw new NoEntityException("There is no history for team members.");
        }
        return ret;
    }
}

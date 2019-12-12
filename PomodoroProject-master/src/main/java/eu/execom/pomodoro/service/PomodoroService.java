package eu.execom.pomodoro.service;

import eu.execom.pomodoro.exceptions.NoEntityException;
import eu.execom.pomodoro.model.Pomodoro;
import eu.execom.pomodoro.model.User;
import eu.execom.pomodoro.model.enumerations.Status;
import eu.execom.pomodoro.repository.PomodoroRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class PomodoroService {

    @Autowired
    private PomodoroRepository pomodoroRepository;

    public List<Pomodoro> getAll() {
        return pomodoroRepository.findAll();
    }

    public Pomodoro getById(Long id) {
        return pomodoroRepository.getOne(id);
    }

    public Pomodoro save(Pomodoro pomodoro) {
        if(pomodoro.getStatus().equals(Status.ACTIVE_TASK)) {
            pomodoro.setStartDateTime(LocalDateTime.now());
        }
        pomodoroRepository.save(pomodoro);
        return pomodoroRepository.save(pomodoro);
    }

    public Pomodoro createNewPomodoro() {
        Pomodoro pomodoro = new Pomodoro();
        pomodoro.setStatus(Status.STOPPED);
        return pomodoroRepository.save(pomodoro);
    }

    public void delete(Long id) {
        if (!pomodoroRepository.existsById(id)) {
            throw new NoEntityException("Pomodoro with this id doesn't exist in database.");
        }
        pomodoroRepository.deleteById(id);
    }
}

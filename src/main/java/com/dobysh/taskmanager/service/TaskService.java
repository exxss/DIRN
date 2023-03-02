package com.dobysh.taskmanager.service;

import com.dobysh.taskmanager.dto.TaskDTO;
import com.dobysh.taskmanager.mapper.TaskMapper;
import com.dobysh.taskmanager.model.Task;
import com.dobysh.taskmanager.repository.ProjectRepository;
import com.dobysh.taskmanager.repository.TaskRepository;
import org.springframework.stereotype.Service;
import org.webjars.NotFoundException;

@Service
public class TaskService extends GenericService<Task, TaskDTO> {

    private final ProjectRepository projectRepository;

    protected TaskService(TaskRepository taskRepository, TaskMapper taskMapper,ProjectRepository projectRepository) {
        super(taskRepository, taskMapper);
        this.projectRepository = projectRepository;
    }

//    public FilmDTO addDirector(Long filmId, Long directorId) {
//        Film film = repository.findById(filmId)
//                .orElseThrow(() -> new NotFoundException("Фильма с таким ID не найдено"));
//        Director director = directorRepository.findById(directorId)
//                .orElseThrow(() -> new NotFoundException("Режиссёра с таким ID не найдено"));
//        film.getDirectors().add(director);
//        return mapper.toDto(repository.save(film));
//    }
}


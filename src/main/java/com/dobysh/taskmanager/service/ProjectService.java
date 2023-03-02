package com.dobysh.taskmanager.service;

import com.dobysh.taskmanager.dto.ProjectDTO;
import com.dobysh.taskmanager.mapper.ProjectMapper;
import com.dobysh.taskmanager.model.Project;
import com.dobysh.taskmanager.repository.ProjectRepository;
import com.dobysh.taskmanager.repository.TaskRepository;
import org.springframework.stereotype.Service;
import org.webjars.NotFoundException;

@Service
public class ProjectService extends GenericService<Project, ProjectDTO> {

    private final TaskRepository taskRepository;

    protected ProjectService(ProjectRepository projectRepository, ProjectMapper projectMapper, TaskRepository taskRepository) {
        super(projectRepository, projectMapper);
        this.taskRepository = taskRepository;
    }
//
//    public ProjectDTO addFilm(Long filmId, Long directorId) {
//        Project director = repository.findById(directorId)
//                .orElseThrow(() -> new NotFoundException("Режиссера с таким ID не найдено"));
//        Film film = filmRepository.findById(filmId)
//                .orElseThrow(() -> new NotFoundException("Фильма с таким ID не найдено"));
//        director.getFilms().add(film);
//        return mapper.toDto(repository.save(director));
//    }
}


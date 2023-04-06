package com.dobysh.taskmanager.service;

import com.dobysh.taskmanager.constants.Errors;
import com.dobysh.taskmanager.dto.ProjectDTO;
import com.dobysh.taskmanager.dto.TaskDTO;
import com.dobysh.taskmanager.dto.TasksWithProjectDTO;
import com.dobysh.taskmanager.mapper.ProjectMapper;
import com.dobysh.taskmanager.mapper.TasksWithProjectMapper;
import com.dobysh.taskmanager.model.Project;
import com.dobysh.taskmanager.model.Task;
import com.dobysh.taskmanager.model.User;
import com.dobysh.taskmanager.repository.ProjectRepository;
import com.dobysh.taskmanager.repository.TaskRepository;
import com.dobysh.taskmanager.repository.UserRepository;
import com.dobysh.taskmanager.service.userdetails.CustomUserDetails;
import jakarta.security.auth.message.AuthException;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.Hibernate;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.Http403ForbiddenEntryPoint;
import org.springframework.stereotype.Service;
import org.webjars.NotFoundException;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.*;

@Service
@Slf4j
public class ProjectService extends GenericService<Project, ProjectDTO> {

    private UserRepository userRepository;
    private ProjectMapper projectMapper;
    private ProjectRepository projectRepository;
    private TasksWithProjectMapper tasksWithProjectMapper;
    private TaskRepository taskRepository;

    protected ProjectService(ProjectRepository projectRepository,
                             ProjectMapper projectMapper,
                             UserRepository userRepository, ProjectRepository projectRepository1, TasksWithProjectMapper tasksWithProjectMapper, TaskRepository taskRepository) {
        super(projectRepository, projectMapper);
        this.userRepository = userRepository;
        this.projectMapper = projectMapper;
        this.projectRepository = projectRepository1;
        this.tasksWithProjectMapper = tasksWithProjectMapper;
        this.taskRepository = taskRepository;
    }

    public List<TasksWithProjectDTO> viewAllTasksProject(Long id){
        Project project = projectRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Фильма с таким ID не найдено"));
        List<Task> mainList = new ArrayList<>(project.getTasks());
        return tasksWithProjectMapper.toDTOs(mainList);
    }

    public List<ProjectDTO> viewAllUserProjects(){
        CustomUserDetails userLoginDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Optional<User> user = userRepository.findById(Long.valueOf(userLoginDetails.getUserId()));
        if(user.isPresent()) {
            Hibernate.initialize(user.get().getProjects());
            List<ProjectDTO> projectDTOS = new ArrayList<>();
            for (Project project : user.get().getProjects()){
                    projectDTOS.add(projectMapper.toDto(project));
            }
            return projectDTOS;
        }
        else {
            return Collections.emptyList();
        }
    }

    @Override
    public ProjectDTO create(ProjectDTO newObject) {
        CustomUserDetails userLoginDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        newObject.setUserId(Long.valueOf(userLoginDetails.getUserId()));
        newObject.setCreatedBy(SecurityContextHolder.getContext().getAuthentication().getName());
        newObject.setCreatedWhen(LocalDateTime.now());
        return super.create(newObject);
    }
    public void addTask(Long taskId, Long projectId){
        Project project = repository.findById(projectId).get();
        Task task = taskRepository.findById(taskId).get();
        project.getTasks().add(task);
        mapper.toDto(repository.save(project));
    }

    @Override
    public ProjectDTO update(ProjectDTO object) {
        CustomUserDetails userLoginDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        object.setUserId(Long.valueOf(userLoginDetails.getUserId()));
        Project project = projectRepository.findById(object.getId()).get();
        Set<Long> ids = new HashSet<>();
        project.getTasks().forEach(d -> ids.add(d.getId()));
        object.setTasksIds(ids);
        object.setCreatedBy(project.getCreatedBy());
        object.setCreatedWhen(project.getCreatedWhen());
        return super.update(object);
    }

    @Override
    public ProjectDTO getOne(Long id) {
        CustomUserDetails userLoginDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = userRepository.findById(Long.valueOf(userLoginDetails.getUserId())).
                orElseThrow(() -> new NotFoundException("Элемент по этому ID не найден"));
        Project project = projectRepository.findById(id).
                orElseThrow(() -> new NotFoundException("Элемент по этому ID не найден"));
        if(Objects.equals(user.getId(), project.getUser().getId())){
            return super.getOne(id);
        }
        else try {
            throw new AuthException(HttpStatus.FORBIDDEN + ": " + Errors.Projects.PROJECT_FORBIDDEN_ERROR);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}


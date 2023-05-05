package com.dobysh.taskmanager.service;

import com.dobysh.taskmanager.dto.ProjectDTO;
import com.dobysh.taskmanager.dto.TasksWithProjectDTO;
import com.dobysh.taskmanager.mapper.ProjectMapper;
import com.dobysh.taskmanager.mapper.TasksWithProjectMapper;
import com.dobysh.taskmanager.model.Project;
import com.dobysh.taskmanager.model.Task;
import com.dobysh.taskmanager.model.User;
import com.dobysh.taskmanager.repository.GenericRepository;
import com.dobysh.taskmanager.repository.ProjectRepository;
import com.dobysh.taskmanager.repository.TaskRepository;
import com.dobysh.taskmanager.repository.UserRepository;
import com.dobysh.taskmanager.service.userdetails.CustomUserDetails;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.Hibernate;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.webjars.NotFoundException;

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
                .orElseThrow(() -> new NotFoundException("Проекта с таким ID не найдено"));
        List<Task> mainList = new ArrayList<>(project.getTasks());
        return tasksWithProjectMapper.toDTOs(mainList);
    }

    public List<ProjectDTO> viewAllUserProjects(){
        CustomUserDetails userLoginDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if(userLoginDetails.getUserId() != null){
        Optional<User> user = userRepository.findById(Long.valueOf(userLoginDetails.getUserId()));
        assert userLoginDetails.getUserId() != null;
        if(user.isPresent()) {
            Hibernate.initialize(user.get().getProjects());
            List<ProjectDTO> projectDTOS = new ArrayList<>();
            for (Project project : user.get().getProjects()){
                    projectDTOS.add(projectMapper.toDto(project));
            }
            return projectDTOS;
        }}
        return Collections.emptyList();
    }

    @Override
    public ProjectDTO create(ProjectDTO newObject) {
        CustomUserDetails userLoginDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        newObject.setUserId(Long.valueOf(userLoginDetails.getUserId()));
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
}


package com.dobysh.taskmanager.service;

import com.dobysh.taskmanager.dto.TaskDTO;
import com.dobysh.taskmanager.mapper.TaskMapper;
import com.dobysh.taskmanager.model.Status;
import com.dobysh.taskmanager.model.Task;
import com.dobysh.taskmanager.model.User;
import com.dobysh.taskmanager.repository.ProjectRepository;
import com.dobysh.taskmanager.repository.TaskRepository;
import com.dobysh.taskmanager.repository.UserRepository;
import com.dobysh.taskmanager.service.userdetails.CustomUserDetails;
import com.dobysh.taskmanager.utils.DateFormatter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.webjars.NotFoundException;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Service
@Slf4j
public class TaskService extends GenericService<Task, TaskDTO> {

    private TaskRepository taskRepository;
    private TaskMapper taskMapper;
    private UserRepository userRepository;
    private ProjectRepository projectRepository;
    private ProjectService projectService;

    protected TaskService(TaskRepository taskRepository, TaskMapper taskMapper, UserRepository userRepository, ProjectRepository projectRepository, ProjectService projectService) {
        super(taskRepository, taskMapper);
        this.taskRepository = taskRepository;
        this.taskMapper = taskMapper;
        this.userRepository = userRepository;
        this.projectRepository = projectRepository;
        this.projectService = projectService;
    }

    public List<TaskDTO> viewPlanedTasks(){
        return getTasksByUserIdAndStatus(Status.PLANNED);
    }
    public List<TaskDTO> viewInProgressTasks(){
        return getTasksByUserIdAndStatus(Status.IN_PROGRESS);
    }
    public List<TaskDTO> viewCompletedTasks(){
        return getTasksByUserIdAndStatus(Status.COMPLETED);
    }
    public List<TaskDTO> viewCanceledTasks(){
        return getTasksByUserIdAndStatus(Status.CANCELED);
    }

    public void updateStatus(Long id,Status status) {
        Task task = taskRepository.findById(id).orElseThrow(() -> new NotFoundException("Задачи с заданным id не существует"));
        task.setStatus(status);
        taskRepository.save(task);
    }

    public List<TaskDTO> getTasksByUserIdAndStatus(Status status){
        CustomUserDetails userLoginDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = userRepository.findById(Long.valueOf(userLoginDetails.getUserId())).orElseThrow(() -> new NotFoundException("Пользователь не найден"));
        List<Task> taskList = new ArrayList<>();
        for (Task task : user.getTasks()){
            if(task.getStatus().equals(status))
                taskList.add(task);
        }
        return taskMapper.toDTOs(taskList);
    }

    @Override
    public TaskDTO create(TaskDTO newObject) {
        CustomUserDetails userLoginDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        newObject.setUserId(Long.valueOf(userLoginDetails.getUserId()));
        newObject.setStatus(Status.PLANNED);
        log.info(newObject.getExpirationDate().toString());
        log.info(newObject.toString());
        return super.create(newObject);
    }

    @Override
    public TaskDTO update(TaskDTO object) {
        CustomUserDetails userLoginDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        object.setUserId(Long.valueOf(userLoginDetails.getUserId()));
        Task task = taskRepository.findById(object.getId()).get();
        object.setStatus(task.getStatus());
        object.setCreatedBy(task.getCreatedBy());
        object.setCreatedWhen(task.getCreatedWhen());

        return super.update(object);
    }
}


package com.dobysh.taskmanager.service;

import com.dobysh.taskmanager.dto.TaskDTO;
import com.dobysh.taskmanager.mapper.TaskMapper;
import com.dobysh.taskmanager.model.Project;
import com.dobysh.taskmanager.model.Status;
import com.dobysh.taskmanager.model.Task;
import com.dobysh.taskmanager.model.User;
import com.dobysh.taskmanager.repository.ProjectRepository;
import com.dobysh.taskmanager.repository.TaskRepository;
import com.dobysh.taskmanager.repository.UserRepository;
import com.dobysh.taskmanager.service.userdetails.CustomUserDetails;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.Hibernate;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.webjars.NotFoundException;

import java.time.LocalDateTime;
import java.util.*;

@Service
@Slf4j
public class TaskService extends GenericService<Task, TaskDTO> {

    private TaskRepository taskRepository;
    private TaskMapper taskMapper;
    private UserRepository userRepository;
    private ProjectRepository projectRepository;

    protected TaskService(TaskRepository taskRepository, TaskMapper taskMapper, UserRepository userRepository, ProjectRepository projectRepository) {
        super(taskRepository, taskMapper);
        this.taskRepository = taskRepository;
        this.taskMapper = taskMapper;
        this.userRepository = userRepository;
        this.projectRepository = projectRepository;
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
        Task task = taskRepository.findById(id).orElseThrow(() -> new NotFoundException("Фильма с заданным id не существует"));
        task.setStatus(status);
        taskRepository.save(task);
    }

    public List<TaskDTO> getTasksByUserIdAndStatus(Status status){
        CustomUserDetails userLoginDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Optional<User> user = userRepository.findById(Long.valueOf(userLoginDetails.getUserId()));
        if(user.isPresent()) {
            Hibernate.initialize(user.get().getTasks());
            List<TaskDTO> taskDTOList = new ArrayList<>();
            for (Task task : user.get().getTasks()){
                if(task.getStatus().equals(status))
                    taskDTOList.add(taskMapper.toDto(task));
            }
            return taskDTOList;
        }
        else {
            return Collections.emptyList();
        }
    }

    @Override
    public TaskDTO create(TaskDTO newObject) {
        CustomUserDetails userLoginDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        newObject.setUserId(Long.valueOf(userLoginDetails.getUserId()));
        newObject.setStatus(Status.PLANNED);
        newObject.setCreatedBy(SecurityContextHolder.getContext().getAuthentication().getName());
        newObject.setCreatedWhen(LocalDateTime.now());
        return super.create(newObject);
    }

    @Override
    public TaskDTO update(TaskDTO object) {
        CustomUserDetails userLoginDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        object.setUserId(Long.valueOf(userLoginDetails.getUserId()));
        Task task = taskRepository.findById(object.getId()).get();
        object.setStatus(task.getStatus());
        object.setProjectId(task.getProject().getId());
        object.setCreatedBy(task.getCreatedBy());
        object.setCreatedWhen(task.getCreatedWhen());
        return super.update(object);
    }
//    TODO сделать изменение задачи в запланированной и в процессе, а так же в проекте
}


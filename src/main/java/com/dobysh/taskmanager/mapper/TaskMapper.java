package com.dobysh.taskmanager.mapper;

import com.dobysh.taskmanager.dto.TaskDTO;
import com.dobysh.taskmanager.model.GenericModel;
import com.dobysh.taskmanager.model.Task;
import com.dobysh.taskmanager.repository.ProjectRepository;
import com.dobysh.taskmanager.repository.UserTasksRepository;
import jakarta.annotation.PostConstruct;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class TaskMapper extends GenericMapper<Task, TaskDTO> {

    private final ModelMapper modelMapper;
    private final ProjectRepository projectRepository;
    private final UserTasksRepository userTasksRepository;

    protected TaskMapper(ModelMapper modelMapper, ProjectRepository projectRepository, UserTasksRepository userTasksRepository) {
        super(modelMapper, Task.class, TaskDTO.class);
        this.modelMapper = modelMapper;
        this.projectRepository = projectRepository;
        this.userTasksRepository = userTasksRepository;
    }

    @PostConstruct
    @Override
    public void setupMapper() {
        modelMapper.createTypeMap(Task.class, TaskDTO.class)
                .addMappings(m -> m.skip(TaskDTO::setProjects)).setPostConverter(toDtoConverter())
                .addMappings(m -> m.skip(TaskDTO::setUserTasks)).setPostConverter(toDtoConverter());
        modelMapper.createTypeMap(TaskDTO.class, Task.class)
                .addMappings(m -> m.skip(Task::setProjects)).setPostConverter(toEntityConverter())
                .addMappings(m -> m.skip(Task::setUserTasks)).setPostConverter(toEntityConverter());
    }

    @Override
    protected void mapSpecificFields(TaskDTO source, Task destination) {
        if (!Objects.isNull(source.getProjects())) {
            destination.setProjects(new HashSet<>(projectRepository.findAllById(source.getProjects())));
        }
        else {
            destination.setProjects(Collections.emptySet());
        }
        if (!Objects.isNull(source.getUserTasks())) {
            destination.setUserTasks(new HashSet<>(userTasksRepository.findAllById(source.getUserTasks())));
        }
        else {
            destination.setUserTasks(Collections.emptySet());
        }

    }

    @Override
    protected void mapSpecificFields(Task source, TaskDTO destination) {
        destination.setProjects(getIds(source));
    }

    protected Set<Long> getIds(Task task) {
        return Objects.isNull(task) || Objects.isNull(task.getProjects())
                ? null
                : task.getProjects().stream()
                .map(GenericModel::getId)
                .collect(Collectors.toSet());
    }
}



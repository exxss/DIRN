package com.dobysh.taskmanager.mapper;

import com.dobysh.taskmanager.dto.UserDTO;
import com.dobysh.taskmanager.model.GenericModel;
import com.dobysh.taskmanager.model.User;
import com.dobysh.taskmanager.repository.ProjectRepository;
import com.dobysh.taskmanager.repository.TaskRepository;
import jakarta.annotation.PostConstruct;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.stream.Collectors;

@Component
public class UserMapper extends GenericMapper<User, UserDTO> {

    private final ModelMapper modelMapper;
    private final TaskRepository taskRepository;
    private final ProjectRepository projectRepository;

    protected UserMapper(ModelMapper modelMapper, TaskRepository taskRepository, ProjectRepository projectRepository) {
        super(modelMapper, User.class, UserDTO.class);
        this.modelMapper = modelMapper;
        this.taskRepository = taskRepository;
        this.projectRepository = projectRepository;
    }

    @PostConstruct
    protected void setupMapper() {
        modelMapper.createTypeMap(User.class, UserDTO.class)
                .addMappings(m -> m.skip(UserDTO::setTasksIds)).setPostConverter(toDtoConverter())
                .addMappings(m -> m.skip(UserDTO::setProjectsIds)).setPostConverter(toDtoConverter());
        modelMapper.createTypeMap(UserDTO.class, User.class)
                .addMappings(m -> m.skip(User::setTasks)).setPostConverter(toEntityConverter())
                .addMappings(m -> m.skip(User::setProjects)).setPostConverter(toEntityConverter());
    }

    @Override
    protected void mapSpecificFields(UserDTO source, User destination) {
        if (!Objects.isNull(source.getTasksIds())) {
            destination.setTasks(new ArrayList<>(taskRepository.findAllById(source.getTasksIds())) {
            });
        }
        else {
            destination.setTasks(Collections.emptyList());
        }
        if (!Objects.isNull(source.getProjectsIds())) {
            destination.setProjects(new ArrayList<>(projectRepository.findAllById(source.getProjectsIds())) {
            });
        }
        else {
            destination.setProjects(Collections.emptyList());
        }
    }

    @Override
    protected void mapSpecificFields(User source, UserDTO destination) {
        destination.setTasksIds(Objects.isNull(source) || Objects.isNull(source.getTasks()) ? null
                : source.getTasks().stream()
                .map(GenericModel::getId)
                .collect(Collectors.toSet()));
        destination.setProjectsIds(Objects.isNull(source) || Objects.isNull(source.getProjects()) ? null
                : source.getProjects().stream()
                .map(GenericModel::getId)
                .collect(Collectors.toSet()));
    }


    @Override
    protected Set<Long> getIds(User entity) {
        return Objects.isNull(entity) || Objects.isNull(entity.getTasks())
                ? null
                : entity.getTasks().stream()
                .map(GenericModel::getId)
                .collect(Collectors.toSet());
    }
}


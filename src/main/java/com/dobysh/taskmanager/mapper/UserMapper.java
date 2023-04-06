package com.dobysh.taskmanager.mapper;

import com.dobysh.taskmanager.dto.UserDTO;
import com.dobysh.taskmanager.model.GenericModel;
import com.dobysh.taskmanager.model.Task;
import com.dobysh.taskmanager.model.User;
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

    protected UserMapper(ModelMapper modelMapper, TaskRepository taskRepository) {
        super(modelMapper, User.class, UserDTO.class);
        this.modelMapper = modelMapper;
        this.taskRepository = taskRepository;
    }

    @PostConstruct
    protected void setupMapper() {
        modelMapper.createTypeMap(User.class, UserDTO.class)
                .addMappings(m -> m.skip(UserDTO::setTasksIds)).setPostConverter(toDtoConverter());
        modelMapper.createTypeMap(UserDTO.class, User.class)
                .addMappings(m -> m.skip(User::setTasks)).setPostConverter(toEntityConverter());
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
    }

    @Override
    protected void mapSpecificFields(User source, UserDTO destination) {
        destination.setTasksIds(Objects.isNull(source) || Objects.isNull(source.getTasks()) ? null
                : source.getTasks().stream()
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


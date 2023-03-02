package com.dobysh.taskmanager.mapper;

import com.dobysh.taskmanager.dto.UserDTO;
import com.dobysh.taskmanager.model.GenericModel;
import com.dobysh.taskmanager.model.User;
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
public class UserMapper extends GenericMapper<User, UserDTO> {

    private final ModelMapper modelMapper;
    private final UserTasksRepository userTasksRepository;

    protected UserMapper(ModelMapper modelMapper, UserTasksRepository userTasksRepository) {
        super(modelMapper, User.class, UserDTO.class);
        this.modelMapper = modelMapper;
        this.userTasksRepository = userTasksRepository;
    }

    @PostConstruct
    protected void setupMapper() {
        modelMapper.createTypeMap(User.class, UserDTO.class)
                .addMappings(m -> m.skip(UserDTO::setTasks)).setPostConverter(toDtoConverter());
        modelMapper.createTypeMap(UserDTO.class, User.class)
                .addMappings(m -> m.skip(User::setUserTasks)).setPostConverter(toEntityConverter());
    }

    @Override
    protected void mapSpecificFields(UserDTO source, User destination) {
        if (!Objects.isNull(source.getTasks())) {
            destination.setUserTasks(new HashSet<>(userTasksRepository.findAllById(source.getTasks())));
        }
        else {
            destination.setUserTasks(Collections.emptySet());
        }
    }

    @Override
    protected void mapSpecificFields(User source, UserDTO destination) {
        destination.setTasks(Objects.isNull(source) || Objects.isNull(source.getUserTasks()) ? null
                : source.getUserTasks().stream()
                .map(GenericModel::getId)
                .collect(Collectors.toSet()));
    }


    @Override
    protected Set<Long> getIds(User entity) {
        return Objects.isNull(entity) || Objects.isNull(entity.getUserTasks())
                ? null
                : entity.getUserTasks().stream()
                .map(GenericModel::getId)
                .collect(Collectors.toSet());
    }
}


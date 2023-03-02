package com.dobysh.taskmanager.mapper;


import com.dobysh.taskmanager.dto.UserTasksDTO;
import com.dobysh.taskmanager.model.UserTasks;
import com.dobysh.taskmanager.repository.TaskRepository;
import com.dobysh.taskmanager.repository.UserRepository;
import jakarta.annotation.PostConstruct;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;
import org.webjars.NotFoundException;

import java.util.Set;

@Component
public class UserTasksMapper extends GenericMapper<UserTasks, UserTasksDTO> {

    private final ModelMapper modelMapper;
    private final TaskRepository taskRepository;
    private final UserRepository userRepository;

    protected UserTasksMapper(ModelMapper modelMapper, TaskRepository taskRepository, UserRepository userRepository) {
        super(modelMapper, UserTasks.class, UserTasksDTO.class);
        this.modelMapper = modelMapper;
        this.taskRepository = taskRepository;
        this.userRepository = userRepository;
    }

    @PostConstruct
    public void setupMapper() {
        super.modelMapper.createTypeMap(UserTasks.class, UserTasksDTO.class)
                .addMappings(m -> m.skip(UserTasksDTO::setUserId)).setPostConverter(toDtoConverter())
                .addMappings(m -> m.skip(UserTasksDTO::setTaskId)).setPostConverter(toDtoConverter());

        super.modelMapper.createTypeMap(UserTasksDTO.class, UserTasks.class)
                .addMappings(m -> m.skip(UserTasks::setUser)).setPostConverter(toEntityConverter())
                .addMappings(m -> m.skip(UserTasks::setTask)).setPostConverter(toEntityConverter());
    }

    @Override
    protected void mapSpecificFields(UserTasksDTO source, UserTasks destination) {
        destination.setTask(taskRepository.findById(source.getTaskId()).orElseThrow(() -> new NotFoundException("Задача не найден")));
        destination.setUser(userRepository.findById(source.getUserId()).orElseThrow(() -> new NotFoundException("Пользователя не найдено")));
    }

    @Override
    protected void mapSpecificFields(UserTasks source, UserTasksDTO destination) {
        destination.setUserId(source.getUser().getId());
        destination.setTaskId(source.getTask().getId());
    }

    @Override
    protected Set<Long> getIds(UserTasks entity) {
        throw new UnsupportedOperationException("Метод недоступен");
    }

}


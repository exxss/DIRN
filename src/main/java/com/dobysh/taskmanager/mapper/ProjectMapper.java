package com.dobysh.taskmanager.mapper;

import com.dobysh.taskmanager.dto.ProjectDTO;
import com.dobysh.taskmanager.model.GenericModel;
import com.dobysh.taskmanager.model.Project;
import com.dobysh.taskmanager.repository.TaskRepository;
import com.dobysh.taskmanager.repository.UserRepository;
import jakarta.annotation.PostConstruct;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;
import org.webjars.NotFoundException;

import java.util.Collections;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class ProjectMapper extends GenericMapper<Project, ProjectDTO> {

    private final ModelMapper modelMapper;
    private final TaskRepository taskRepository;
    private final UserRepository userRepository;

    protected ProjectMapper(ModelMapper modelMapper, TaskRepository taskRepository, UserRepository userRepository) {
        super(modelMapper, Project.class, ProjectDTO.class);
        this.modelMapper = modelMapper;
        this.taskRepository = taskRepository;
        this.userRepository = userRepository;
    }

    @PostConstruct
    protected void setupMapper() {
        modelMapper.createTypeMap(Project.class, ProjectDTO.class)
                .addMappings(m -> m.skip(ProjectDTO::setTasksIds)).setPostConverter(toDtoConverter())
                .addMappings(m -> m.skip(ProjectDTO::setUserId)).setPostConverter(toDtoConverter());
        modelMapper.createTypeMap(ProjectDTO.class, Project.class)
                .addMappings(m -> m.skip(Project::setTasks)).setPostConverter(toEntityConverter())
                .addMappings(m -> m.skip(Project::setUser)).setPostConverter(toEntityConverter());
    }

    @Override
    protected void mapSpecificFields(ProjectDTO source, Project destination) {
        if (!Objects.isNull(source.getTasksIds())) {
            destination.setTasks(new HashSet<>(taskRepository.findAllById(source.getTasksIds())));
        }
        else {
            destination.setTasks(Collections.emptySet());
        }
        destination.setUser(userRepository.findById(source.getUserId()).orElseThrow(() -> new NotFoundException("Пользователь не найден")));
    }

    @Override
    protected void mapSpecificFields(Project source, ProjectDTO destination) {
        destination.setTasksIds(Objects.isNull(source) || Objects.isNull(source.getTasks()) ? null
                : source.getTasks().stream()
                .map(GenericModel::getId)
                .collect(Collectors.toSet()));
        destination.setUserId(source.getUser().getId());

    }


    @Override
    protected Set<Long> getIds(Project entity) {
        return null;
    }
}


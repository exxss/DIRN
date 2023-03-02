package com.dobysh.taskmanager.mapper;

import com.dobysh.taskmanager.dto.ProjectDTO;
import com.dobysh.taskmanager.model.GenericModel;
import com.dobysh.taskmanager.model.Project;
import com.dobysh.taskmanager.repository.TaskRepository;

import jakarta.annotation.PostConstruct;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class ProjectMapper extends GenericMapper<Project, ProjectDTO> {

    private final ModelMapper modelMapper;
    private final TaskRepository taskRepository;

    protected ProjectMapper(ModelMapper modelMapper, TaskRepository taskRepository) {
        super(modelMapper, Project.class, ProjectDTO.class);
        this.modelMapper = modelMapper;
        this.taskRepository = taskRepository;
    }

    @PostConstruct
    protected void setupMapper() {
        modelMapper.createTypeMap(Project.class, ProjectDTO.class)
                .addMappings(m -> m.skip(ProjectDTO::setTasks)).setPostConverter(toDtoConverter());
        modelMapper.createTypeMap(ProjectDTO.class, Project.class)
                .addMappings(m -> m.skip(Project::setTasks)).setPostConverter(toEntityConverter());
    }

    @Override
    protected void mapSpecificFields(ProjectDTO source, Project destination) {
        if (!Objects.isNull(source.getTasks())) {
            destination.setTasks(new HashSet<>(taskRepository.findAllById(source.getTasks())));
        }
        else {
            destination.setTasks(Collections.emptySet());
        }
    }

    @Override
    protected void mapSpecificFields(Project source, ProjectDTO destination) {
        destination.setTasks(Objects.isNull(source) || Objects.isNull(source.getTasks()) ? null
                : source.getTasks().stream()
                .map(GenericModel::getId)
                .collect(Collectors.toSet()));
    }

    @Override
    protected Set<Long> getIds(Project entity) {
        return null;
    }
}


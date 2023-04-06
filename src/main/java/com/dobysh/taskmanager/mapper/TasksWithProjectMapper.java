package com.dobysh.taskmanager.mapper;

import com.dobysh.taskmanager.dto.TasksWithProjectDTO;
import com.dobysh.taskmanager.model.Task;
import com.dobysh.taskmanager.repository.ProjectRepository;
import jakarta.annotation.PostConstruct;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.Set;

@Component
public class TasksWithProjectMapper extends GenericMapper<Task,TasksWithProjectDTO>{

    private final ProjectRepository projectRepository;

    protected TasksWithProjectMapper(ModelMapper mapper,
                                    ProjectRepository projectRepository
    ) {
        super(mapper, Task.class, TasksWithProjectDTO.class);
        this.projectRepository = projectRepository;
    }

    @Override
    @PostConstruct
    protected void setupMapper() {
        modelMapper.createTypeMap(Task.class, TasksWithProjectDTO.class)
                .addMappings(m -> m.skip(TasksWithProjectDTO::setProjectId)).setPostConverter(toDtoConverter());

        modelMapper.createTypeMap(TasksWithProjectDTO.class, Task.class)
                .addMappings(m -> m.skip(Task::setProject)).setPostConverter(toEntityConverter());
    }

    @Override
    protected void mapSpecificFields(TasksWithProjectDTO source, Task destination) {
        destination.setProject(projectRepository.findById(source.getProjectId()).get());
    }

    @Override
    protected void mapSpecificFields(Task source, TasksWithProjectDTO destination) {
        destination.setProjectId(source.getProject().getId());
    }

    @Override
    protected Set<Long> getIds(Task entity) {
        throw new UnsupportedOperationException("Метод недоступен");
    }
}

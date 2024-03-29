package com.dobysh.taskmanager.mapper;

import com.dobysh.taskmanager.dto.TaskDTO;
import com.dobysh.taskmanager.model.Task;
import com.dobysh.taskmanager.repository.ProjectRepository;
import com.dobysh.taskmanager.repository.UserRepository;
import com.dobysh.taskmanager.utils.DateFormatter;
import jakarta.annotation.PostConstruct;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;
import org.webjars.NotFoundException;

import java.time.LocalDate;
import java.util.Objects;
import java.util.Set;

@Component
@Log4j2
public class TaskMapper extends
        GenericMapper<Task, TaskDTO> {

    private final ModelMapper modelMapper;
    private final ProjectRepository projectRepository;
    private final UserRepository userRepository;

    protected TaskMapper(ModelMapper modelMapper, ProjectRepository projectRepository, UserRepository userRepository) {
        super(modelMapper, Task.class, TaskDTO.class);
        this.modelMapper = modelMapper;
        this.projectRepository = projectRepository;
        this.userRepository = userRepository;
    }

    @PostConstruct
    @Override
    public void setupMapper() {
        modelMapper.createTypeMap(Task.class, TaskDTO.class)
                .addMappings(m -> m.skip(TaskDTO::setProjectId)).setPostConverter(toDtoConverter())
                .addMappings(m -> m.skip(TaskDTO::setUserId)).setPostConverter(toDtoConverter());
        modelMapper.createTypeMap(TaskDTO.class, Task.class)
                .addMappings(m -> m.skip(Task::setProject)).setPostConverter(toEntityConverter())
                .addMappings(m -> m.skip(Task::setUser)).setPostConverter(toEntityConverter())
                .addMappings(m -> m.skip(Task::setExpirationDate)).setPostConverter(toEntityConverter());
    }

    @Override
    protected void mapSpecificFields(TaskDTO source, Task destination) {
        if(source.getProjectId() != null) {
            destination.setProject(projectRepository.findById(source.getProjectId()).orElseThrow(() -> new NotFoundException("Проект не найден")));
        }
        if (Objects.equals(source.getExpirationDate(), "")) {
            destination.setExpirationDate(null);
        }
        if (!(Objects.equals(source.getExpirationDate(), ""))){
            destination.setExpirationDate(DateFormatter.formatStringToDate(source.getExpirationDate()));
        }
        destination.setUser(userRepository.findById(source.getUserId()).orElseThrow(() -> new NotFoundException("Пользователь не найден")));
    }

    @Override
    protected void mapSpecificFields(Task source, TaskDTO destination) {
        if (source.getProject() != null) {
        destination.setProjectId(source.getProject().getId());
        }
        destination.setUserId(source.getUser().getId());
    }

    @Override
    protected Set<Long> getIds(Task entity) {
        throw new UnsupportedOperationException("Метод недоступен");
    }
}



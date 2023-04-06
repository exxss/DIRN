//package com.dobysh.taskmanager.mapper;
//
//import com.dobysh.taskmanager.dto.ProjectWithTasksDTO;
//import com.dobysh.taskmanager.model.GenericModel;
//import com.dobysh.taskmanager.model.Project;
//import com.dobysh.taskmanager.repository.TaskRepository;
//import org.modelmapper.ModelMapper;
//import org.springframework.stereotype.Component;
//
//import java.util.HashSet;
//import java.util.Objects;
//import java.util.Set;
//import java.util.stream.Collectors;
//
//@Component
//public class ProjectWithTasksMapper extends GenericMapper<Project, ProjectWithTasksDTO>{
//    private final TaskRepository taskRepository;
//
//    public ProjectWithTasksMapper(ModelMapper modelMapper,
//                                  TaskRepository taskRepository) {
//        super(modelMapper, Project.class, ProjectWithTasksDTO.class);
//        this.taskRepository = taskRepository;
//    }
//
//    @Override
//    protected void mapSpecificFields(ProjectWithTasksDTO source, Project destination) {
//        destination.setTasks(new HashSet<>(taskRepository.findAll()));
//    }
//
//    @Override
//    protected void mapSpecificFields(Project source, ProjectWithTasksDTO destination) {
//        destination.setTasksIds(getIds(source));
//    }
//
//    @Override
//    protected Set<Long> getIds(Project project) {
//        return Objects.isNull(project) || Objects.isNull(project.getTasks())
//                ? null
//                : project.getTasks().stream()
//                .map(GenericModel::getId)
//                .collect(Collectors.toSet());
//    }
//
//    @Override
//    protected void setupMapper() {
//        modelMapper.createTypeMap(Project.class, ProjectWithTasksDTO.class)
//                .addMappings(m -> m.skip(ProjectWithTasksDTO::setTasksIds)).setPostConverter(toDtoConverter());
//        modelMapper.createTypeMap(ProjectWithTasksDTO.class,Project.class)
//                .addMappings(m -> m.skip(Project::setTasks)).setPostConverter(toEntityConverter());
//    }
//}

package com.dobysh.taskmanager.service;

import com.dobysh.taskmanager.ProjectTestData;
import com.dobysh.taskmanager.TaskTestData;
import com.dobysh.taskmanager.dto.ProjectDTO;
import com.dobysh.taskmanager.dto.TaskDTO;
import com.dobysh.taskmanager.mapper.ProjectMapper;
import com.dobysh.taskmanager.mapper.TaskMapper;
import com.dobysh.taskmanager.mapper.TasksWithProjectMapper;
import com.dobysh.taskmanager.model.Project;
import com.dobysh.taskmanager.model.User;
import com.dobysh.taskmanager.repository.ProjectRepository;
import com.dobysh.taskmanager.repository.TaskRepository;
import com.dobysh.taskmanager.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.mockito.Mockito;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;


@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@Slf4j
public class ProjectServiceTest extends GenericTest<Project,ProjectDTO> {

    public ProjectServiceTest() {
        super();
        TaskRepository taskRepository = Mockito.mock(TaskRepository.class);
        UserRepository userRepository = Mockito.mock(UserRepository.class);
        TasksWithProjectMapper tasksWithProjectMapper = Mockito.mock(TasksWithProjectMapper.class);
        repository = Mockito.mock(ProjectRepository.class);
        mapper = Mockito.mock(ProjectMapper.class);
        service = new ProjectService((ProjectRepository) repository, (ProjectMapper) mapper, userRepository,(ProjectRepository) repository,  tasksWithProjectMapper,taskRepository);
    }

    @Test
    @Order(1)
    @Override
    protected void getAll() {
        Mockito.when(repository.findAll()).thenReturn(ProjectTestData.PROJECT_LIST);
        Mockito.when(mapper.toDTOs(ProjectTestData.PROJECT_LIST)).thenReturn(ProjectTestData.PROJECT_DTO_LIST);
        List<ProjectDTO> projectDTOS = service.listAll();
        log.info("Testing getAll(): " + projectDTOS);
        assertEquals(ProjectTestData.PROJECT_LIST.size(), projectDTOS.size());
    }

    @Test
    @Order(2)
    @Override
    protected void getOne() {
        Mockito.when(repository.findById(1L)).thenReturn(Optional.of(ProjectTestData.PROJECT_1));
        Mockito.when(mapper.toDto(ProjectTestData.PROJECT_1)).thenReturn(ProjectTestData.PROJECT_DTO_1);
        ProjectDTO projectDTO = service.getOne(1L);
        log.info("Testing getOne(): " + projectDTO);
        assertEquals(ProjectTestData.PROJECT_DTO_1, projectDTO);
    }

    @Order(3)
    @Test
    @Override
    protected void create() {
        Mockito.when(repository.findById(1L)).thenReturn(Optional.of(ProjectTestData.PROJECT_1));
        Mockito.when(mapper.toDto(ProjectTestData.PROJECT_1)).thenReturn(ProjectTestData.PROJECT_DTO_1);
        ProjectDTO projectDTO = service.getOne(1L);
        assertEquals(ProjectTestData.PROJECT_DTO_1,projectDTO);
    }

    @Order(4)
    @Test
    @Override
    protected void update() {
        Mockito.when(repository.findById(1L)).thenReturn(Optional.of(ProjectTestData.PROJECT_1));
        Mockito.when(mapper.toDto(ProjectTestData.PROJECT_1)).thenReturn(ProjectTestData.PROJECT_DTO_1);
        ProjectDTO projectDTO = service.getOne(1L);
        assertEquals(ProjectTestData.PROJECT_DTO_1, projectDTO);
    }
    @Order(5)
    @Test
    @Override
    protected void delete() {
        Mockito.when(repository.findAll()).thenReturn(ProjectTestData.PROJECT_LIST);
        Mockito.when(mapper.toDTOs(ProjectTestData.PROJECT_LIST)).thenReturn(ProjectTestData.PROJECT_DTO_LIST);
        List<ProjectDTO> projectDTOS = service.listAll();
        service.delete(1L);
        assertEquals(ProjectTestData.PROJECT_LIST.size(), projectDTOS.size());
    }
}

package com.dobysh.taskmanager.service;


import com.dobysh.taskmanager.TaskTestData;
import com.dobysh.taskmanager.UserTestData;
import com.dobysh.taskmanager.dto.TaskDTO;
import com.dobysh.taskmanager.mapper.TaskMapper;
import com.dobysh.taskmanager.model.Status;
import com.dobysh.taskmanager.model.Task;
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

import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@Slf4j
public class TaskServiceTest
        extends GenericTest<Task, TaskDTO> {

    public TaskServiceTest() {
        super();
        ProjectService projectService = Mockito.mock(ProjectService.class);
        ProjectRepository projectRepository = Mockito.mock(ProjectRepository.class);
        UserRepository userRepository = Mockito.mock(UserRepository.class);
        repository = Mockito.mock(TaskRepository.class);
        mapper = Mockito.mock(TaskMapper.class);
        service = new TaskService((TaskRepository) repository, (TaskMapper) mapper, userRepository, projectRepository, projectService);
    }

    @Test
    @Order(1)
    @Override
    protected void getAll() {
        Mockito.when(repository.findAll()).thenReturn(TaskTestData.TASK_LIST);
        Mockito.when(mapper.toDTOs(TaskTestData.TASK_LIST)).thenReturn(TaskTestData.TASK_DTO_LIST);
        List<TaskDTO> taskDTOS = service.listAll();
        log.info("Testing getAll(): " + taskDTOS);
        assertEquals(TaskTestData.TASK_LIST.size(), taskDTOS.size());
    }

    @Test
    @Order(2)
    @Override
    protected void getOne() {
        Mockito.when(repository.findById(1L)).thenReturn(Optional.of(TaskTestData.TASK_1));
        Mockito.when(mapper.toDto(TaskTestData.TASK_1)).thenReturn(TaskTestData.TASK_DTO_1);
        TaskDTO taskDTO = service.getOne(1L);
        assertEquals(TaskTestData.TASK_DTO_1, taskDTO);
    }

    @Order(3)
    @Test
    @Override
    protected void create() {
        Mockito.when(repository.findById(1L)).thenReturn(Optional.of(TaskTestData.TASK_1));
        Mockito.when(mapper.toDto(TaskTestData.TASK_1)).thenReturn(TaskTestData.TASK_DTO_1);
        TaskDTO taskDTO = service.getOne(1L);
        assertEquals(TaskTestData.TASK_DTO_1, taskDTO);
    }

    @Order(4)
    @Test
    @Override
    protected void update() {
        Mockito.when(repository.findById(1L)).thenReturn(Optional.of(TaskTestData.TASK_1));
        Mockito.when(mapper.toDto(TaskTestData.TASK_1)).thenReturn(TaskTestData.TASK_DTO_1);
        TaskDTO taskDTO = service.getOne(1L);
        assertEquals(TaskTestData.TASK_DTO_1, taskDTO);
    }
    @Order(5)
    @Test
    @Override
    protected void delete() {
        Mockito.when(repository.findAll()).thenReturn(TaskTestData.TASK_LIST);
        Mockito.when(mapper.toDTOs(TaskTestData.TASK_LIST)).thenReturn(TaskTestData.TASK_DTO_LIST);
        List<TaskDTO> taskDTOS = service.listAll();
        service.delete(1L);
        assertEquals(TaskTestData.TASK_DTO_LIST.size(), taskDTOS.size());
    }
}



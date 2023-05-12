package com.dobysh.taskmanager.MVCcontroller;

import com.dobysh.taskmanager.dto.ProjectDTO;
import com.dobysh.taskmanager.dto.TaskDTO;
import com.dobysh.taskmanager.model.Status;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.*;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.security.test.context.support.WithMockUser;

import java.time.LocalDateTime;
import java.util.HashSet;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@Slf4j
public class MVCTaskControllerTest extends CommonTestMVC{
    @Test
    @DisplayName("Создание задачи через MVC контроллер, тестирование 'tasks/add'")
    @Order(0)
    @WithAnonymousUser
    @Override
    protected void createObject() throws Exception {
        log.info("Тест по созданию задачи через MVC начат успешно");
        TaskDTO taskDTO = new TaskDTO("taskTitle1", "description1", Status.PLANNED, 1L, 1L,LocalDateTime.now());

        mvc.perform(post("/tasks/add")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .flashAttr("taskForm", taskDTO)
                        .accept(MediaType.APPLICATION_JSON_VALUE)
                        .with(csrf()))
                .andDo(print())
                .andExpect(status().is3xxRedirection());
        log.info("Тест по созданию задачи через MVC закончен успешно");
    }

    @Test
    @DisplayName("изменение задачи через MVC контроллер, тестирование 'projects/update'")
    @Order(1)
    @WithAnonymousUser
    @Override
    protected void updateObject() throws Exception {
        log.info("Тест по изменению задачи через MVC начат успешно");
        TaskDTO taskDTO = new TaskDTO("taskTitle1", "description1", Status.PLANNED, 1L, 1L, LocalDateTime.now());
        taskDTO.setTaskTitle("taskTitleUpdate");

        mvc.perform(post("/tasks/update/1")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .flashAttr("taskForm", taskDTO)
                        .accept(MediaType.APPLICATION_JSON_VALUE)
                        .with(csrf()))
                .andDo(print())
                .andExpect(status().is3xxRedirection());
        log.info("Тест по изменению задачи через MVC закончен успешно");
    }


    @Test
    @DisplayName("Удаление задачи через MVC контроллер")
    @Order(2)
    @WithAnonymousUser
    @Override
    protected void deleteObject() throws Exception {
        log.info("Тест по удалению задачи через MVC начат успешно");
        TaskDTO taskDTO = new TaskDTO("taskTitle1", "description1", Status.PLANNED, 1L, 1L,LocalDateTime.now());

        mvc.perform(post("/tasks/delete/1")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .accept(MediaType.APPLICATION_JSON_VALUE)
                        .with(csrf()))
                .andDo(print())
                .andExpect(status().is3xxRedirection());
        log.info("Тест по удалению задачи через MVC закончен успешно");
    }

    @Test
    @DisplayName("Просмотр всех проектов через MVC контроллер")
    @Order(3)
    @WithAnonymousUser
    @Override
    protected void listAll() throws Exception {
        log.info("Тест по выбору всех проектов через MVC начат");
        mvc.perform(get("/tasks/planned")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .accept(MediaType.APPLICATION_JSON_VALUE)
                )
                .andDo(print())
                .andExpect(status().is3xxRedirection())
                .andReturn();
    }
}

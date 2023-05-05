package com.dobysh.taskmanager.MVCcontroller;

import com.dobysh.taskmanager.dto.ProjectDTO;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.*;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.security.test.context.support.WithMockUser;

import java.util.HashSet;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@Slf4j
public class MVCProjectControllerTest extends CommonTestMVC{

    @Test
    @DisplayName("Создание проекта через MVC контроллер, тестирование 'projects/add'")
    @Order(0)
    @WithMockUser(username = "admin", roles = "ADMIN", password = "12345")
    @Override
    protected void createObject() throws Exception {
        log.info("Тест по созданию проекта через MVC начат успешно");
        ProjectDTO projectDTO = new ProjectDTO("projectName2", new HashSet<>(), 1L);

        mvc.perform(post("/projects/add")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .flashAttr("projectForm", projectDTO)
                        .accept(MediaType.APPLICATION_JSON_VALUE)
                        .with(csrf()))
                .andDo(print())
                .andExpect(status().isForbidden());
        log.info("Тест по созданию проекта через MVC закончен успешно");
    }

    @Test
    @DisplayName("изменение проекта через MVC контроллер, тестирование 'projects/update'")
    @Order(1)
    @WithMockUser(username = "admin", roles = "ADMIN", password = "12345")
    @Override
    protected void updateObject() throws Exception {
        log.info("Тест по изменению проекта через MVC начат успешно");
        ProjectDTO projectDTO = new ProjectDTO("projectName2", new HashSet<>(), 1L);
        projectDTO.setProjectName("123");

        mvc.perform(post("/projects/update")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .flashAttr("projectForm", projectDTO)
                        .accept(MediaType.APPLICATION_JSON_VALUE)
                        .with(csrf()))
                .andDo(print())
                .andExpect(status().isForbidden());
        log.info("Тест по изменению проекта через MVC закончен успешно");
    }


    @Test
    @DisplayName("Удаление проекта через MVC контроллер")
    @Order(2)
    @WithAnonymousUser
    @Override
    protected void deleteObject() throws Exception {
        log.info("Тест по удалению проекта через MVC начат успешно");
        ProjectDTO projectDTO = new ProjectDTO("projectName2", new HashSet<>(), 1L);
        projectDTO.setProjectName("123");

        mvc.perform(post("/projects/delete/1")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .accept(MediaType.APPLICATION_JSON_VALUE)
                        .with(csrf()))
                .andDo(print())
                .andExpect(status().is3xxRedirection());
        log.info("Тест по удалению проекта через MVC закончен успешно");
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

package com.dobysh.taskmanager.MVCcontroller;

import com.dobysh.taskmanager.dto.RoleDTO;
import com.dobysh.taskmanager.dto.UserDTO;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.*;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

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
public class MVCUserControllerTest extends CommonTestMVC{
    @Test
    @DisplayName("Регистрация пользователя через MVC контроллер, тестирование '/registration'")
    @Order(0)
    @WithAnonymousUser
    @Override
    protected void createObject() throws Exception {
        log.info("Тест по регистрации пользователя через MVC начат успешно");
        UserDTO userDTO = new UserDTO( "login", "password", "firstName", "lastName", "email", new RoleDTO(), new HashSet<>(), new HashSet<>(), "changePasswordToken", false);

        mvc.perform(post("/registration")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .flashAttr("userForm", userDTO)
                        .accept(MediaType.APPLICATION_JSON_VALUE)
                        .with(csrf()))
                .andDo(print())
                .andExpect(status().is3xxRedirection());
        log.info("Тест по регистрации пользователя через MVC закончен успешно");
    }

    @Test
    @DisplayName("изменение пользователя через MVC контроллер, тестирование 'profile/updateProfile'")
    @Order(1)
    @WithAnonymousUser
    @Override
    protected void updateObject() throws Exception {
        log.info("Тест по изменению пользователя через MVC начат успешно");
        UserDTO userDTO = new UserDTO( "login", "password", "firstName", "lastName", "email", new RoleDTO(), new HashSet<>(), new HashSet<>(), "changePasswordToken", false);
        userDTO.setFirstName("updateFirstName");
        mvc.perform(post("/users/profile/update/1")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .flashAttr("projectForm", userDTO)
                        .accept(MediaType.APPLICATION_JSON_VALUE)
                        .with(csrf()))
                .andDo(print())
                .andExpect(status().is3xxRedirection());
        log.info("Тест по изменению пользователя через MVC закончен успешно");
    }


    @Test
    @Order(2)
    @WithMockUser(username = "admin", roles = "ADMIN", password = "12345")
    @Override
    protected void deleteObject() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/users/delete/1"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/users/list"));
    }

    @Test
    @Order(3)
    @WithMockUser(username = "admin", roles = "ADMIN", password = "12345")
    protected void restore() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/users/restore/1"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/users/list"));
    }


    @Test
    @Order(4)
    @WithMockUser(username = "admin", roles = "ADMIN", password = "12345")
    @Override
    protected void listAll() throws Exception {
        mvc.perform(get("/users/list")
                        .param("page", "1")
                        .param("size", "5")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .accept(MediaType.APPLICATION_JSON_VALUE)
                )
                .andDo(print())
                .andExpect(status().is2xxSuccessful())
                .andReturn();
    }

}

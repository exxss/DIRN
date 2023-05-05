package com.dobysh.taskmanager.service;

import com.dobysh.taskmanager.UserTestData;
import com.dobysh.taskmanager.dto.RoleDTO;
import com.dobysh.taskmanager.dto.UserDTO;
import com.dobysh.taskmanager.mapper.UserMapper;
import com.dobysh.taskmanager.model.User;
import com.dobysh.taskmanager.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.mockito.Mockito;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@Slf4j
public class UserServiceTest extends GenericTest<User, UserDTO> {

    public UserServiceTest() {
        super();
        BCryptPasswordEncoder bCryptPasswordEncoder = Mockito.mock(BCryptPasswordEncoder.class);
        JavaMailSender javaMailSender = Mockito.mock(JavaMailSender.class);
        repository = Mockito.mock(UserRepository.class);
        mapper = Mockito.mock(UserMapper.class);
        service = new UserService((UserRepository) repository, (UserMapper) mapper, bCryptPasswordEncoder, javaMailSender);
    }

    @Test
    @Order(1)
    @Override
    protected void getAll() {
        Mockito.when(repository.findAll()).thenReturn(UserTestData.USER_LIST);
        Mockito.when(mapper.toDTOs(UserTestData.USER_LIST)).thenReturn(UserTestData.USER_DTO_LIST);
        List<UserDTO> userDTOS = service.listAll();
        log.info("Testing getAll(): " + userDTOS);
        assertEquals(UserTestData.USER_LIST.size(), userDTOS.size());
    }

    @Test
    @Order(2)
    @Override
    protected void getOne() {
        Mockito.when(repository.findById(1L)).thenReturn(Optional.of(UserTestData.USER_1));
        Mockito.when(mapper.toDto(UserTestData.USER_1)).thenReturn(UserTestData.USER_DTO_1);
        UserDTO userDTO = service.getOne(1L);
        log.info("Testing getOne(): " + userDTO);
        assertEquals(UserTestData.USER_DTO_1, userDTO);
    }

    @Test
    @Order(3)
    @Override
    protected void create() {
        Mockito.when(repository.findById(1L)).thenReturn(Optional.of(UserTestData.USER_1));
        Mockito.when(mapper.toDto(UserTestData.USER_1)).thenReturn(UserTestData.USER_DTO_1);
        UserDTO userDTO = service.create(new UserDTO("login",
                "password",
                "firstName",
                "lastName",
                "email",
                new RoleDTO(),
                new HashSet<>(),
                new HashSet<>(),
                "changePasswordToken",
                false));
        assertNull(userDTO);
    }

    @Test
    @Order(4)
    @Override
    protected void update() {
        Mockito.when(repository.findById(1L)).thenReturn(Optional.of(UserTestData.USER_1));
        Mockito.when(mapper.toDto(UserTestData.USER_1)).thenReturn(UserTestData.USER_DTO_1);
        UserDTO userDTO = service.create(new UserDTO());
        service.update(userDTO);
        assertNull(userDTO);
    }

    @Test
    @Order(5)
    @Override
    protected void delete() {
        Mockito.when(repository.save(UserTestData.USER_1)).thenReturn(UserTestData.USER_1);
        Mockito.when(repository.findById(1L)).thenReturn(Optional.of(UserTestData.USER_1));
        log.info("Testing delete() before: " + UserTestData.USER_1.isDeleted());
        service.delete(1L);
        log.info("Testing delete() after: " + UserTestData.USER_1.isDeleted());
        assertTrue(UserTestData.USER_1.isDeleted());
    }

    @Test
    @Order(6)
    protected void restore() {
        UserTestData.USER_2.setDeleted(true);
        Mockito.when(repository.save(UserTestData.USER_2)).thenReturn(UserTestData.USER_2);
        Mockito.when(repository.findById(3L)).thenReturn(Optional.of(UserTestData.USER_2));
        log.info("Testing restore() before: " + UserTestData.USER_2.isDeleted());
        ((UserService) service).restore(3L);
        log.info("Testing restore() after: " + UserTestData.USER_2.isDeleted());
        assertFalse(UserTestData.USER_2.isDeleted());
    }
}


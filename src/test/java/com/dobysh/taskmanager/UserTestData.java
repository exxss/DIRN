package com.dobysh.taskmanager;

import com.dobysh.taskmanager.dto.RoleDTO;
import com.dobysh.taskmanager.dto.UserDTO;
import com.dobysh.taskmanager.model.Project;
import com.dobysh.taskmanager.model.Role;
import com.dobysh.taskmanager.model.User;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public interface UserTestData {

    UserDTO USER_DTO_1 = new UserDTO(
            "login",
            "password",
            "firstName",
            "lastName",
            "email",
            new RoleDTO(),
            new HashSet<>(),
            new HashSet<>(),
            "changePasswordToken",
            false
    );
    UserDTO USER_DTO_2 = new UserDTO(
            "login",
            "password",
            "firstName",
            "lastName",
            "email",
            new RoleDTO(),
            new HashSet<>(),
            new HashSet<>(),
            "changePasswordToken",
            false
    );

    List<UserDTO> USER_DTO_LIST = List.of(USER_DTO_1,USER_DTO_2);

    User USER_1 = new User(
            "login",
            "password",
            "firstName",
            "lastName",
            "email",
            "changePasswordToken",
            new Role(),
            new ArrayList<>(),
            new ArrayList<>()
    );
    User USER_2 = new User(
            "login",
            "password",
            "firstName",
            "lastName",
            "email",
            "changePasswordToken",
            new Role(),
            new ArrayList<>(),
            new ArrayList<>()
    );

    List<User> USER_LIST = List.of(USER_1,USER_2);
}

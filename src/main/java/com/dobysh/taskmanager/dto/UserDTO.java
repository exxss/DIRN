package com.dobysh.taskmanager.dto;

import com.dobysh.taskmanager.model.Project;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO extends GenericDTO {
    private String login;
    private String password;
    private String firstName;
    private String lastName;
    private String email;
    private RoleDTO role;
    private Set<Long> projectsIds;
    private Set<Long> tasksIds;
    private String changePasswordToken;
    private boolean isDeleted;
}

package com.dobysh.taskmanager.model;


import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.OrderBy;

import java.util.List;

@Entity
@Table(name = "users")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@SequenceGenerator(name = "default_gen", sequenceName = "users_id_seq", allocationSize = 1)
public class User
        extends GenericModel {

    @Column(name = "login", nullable = false)
    private String login;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "first_name", nullable = false)
    private String firstName;

    @Column(name = "last_name", nullable = false)
    private String lastName;

    @Column(name = "email", nullable = false)
    private String email;

    @Column(name = "change_password_token")
    private String changePasswordToken;

    @ManyToOne(cascade = {CascadeType.MERGE})
    @JoinColumn(name = "role_id", nullable = false,
            foreignKey = @ForeignKey(name = "FK_USER_ROLES"))
    private Role role;

    @OneToMany
    @OrderBy(clause = "id DESC")
    @JoinTable(name = "users_tasks",
            joinColumns = @JoinColumn(name = "user_id"), foreignKey = @ForeignKey(name = "FK_TASKS_USERS"),
            inverseJoinColumns = @JoinColumn(name = "task_id"), inverseForeignKey = @ForeignKey(name = "FK_USERS_TASKS"))
    private List<Task> tasks;

    @OneToMany
    @OrderBy(clause = "id DESC")
    @JoinTable(name = "users_projects",
            joinColumns = @JoinColumn(name = "user_id"), foreignKey = @ForeignKey(name = "FK_PROJECTS_USERS"),
            inverseJoinColumns = @JoinColumn(name = "project_id"), inverseForeignKey = @ForeignKey(name = "FK_USERS_PROJECTS"))
    private List<Project> projects;
}


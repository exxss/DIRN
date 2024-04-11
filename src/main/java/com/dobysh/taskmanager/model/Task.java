package com.dobysh.taskmanager.model;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;
import jakarta.validation.constraints.Pattern;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Entity
@Table(name = "tasks")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@SequenceGenerator(name = "default_gen", sequenceName = "tasks_id_seq", allocationSize = 1)
@ToString
public class Task extends GenericModel {

    @Column(name = "title", nullable = false)
    private String taskTitle;

    @Column(name = "description")
    private String description;

    @Column(name = "status", nullable = false)
    @Enumerated
    private Status status;

    @Column(name = "priority")
    @Enumerated
    private Priority priority;

    @ManyToOne
    @JoinTable(name = "tasks_projects",
            joinColumns = @JoinColumn(name = "task_id"), foreignKey = @ForeignKey(name = "FK_TASKS_PROJECTS"),
            inverseJoinColumns = @JoinColumn(name = "project_id"), inverseForeignKey = @ForeignKey(name = "FK_PROJECTS_TASKS"))
    private Project project;

    @ManyToOne
    @JoinTable(name = "users_tasks",
            joinColumns = @JoinColumn(name = "task_id"), foreignKey = @ForeignKey(name = "FK_TASKS_USERS"),
            inverseJoinColumns = @JoinColumn(name = "user_id"), inverseForeignKey = @ForeignKey(name = "FK_USERS_TASKS"))
    private User user;

    @Column(name = "expiration_date")
    private LocalDate expirationDate;
}

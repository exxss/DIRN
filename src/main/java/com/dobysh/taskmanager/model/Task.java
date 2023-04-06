package com.dobysh.taskmanager.model;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.Set;

@Entity
@Table(name = "tasks")
@Getter
@Setter
@NoArgsConstructor
@SequenceGenerator(name = "default_gen", sequenceName = "tasks_seq", allocationSize = 1)
@JsonIdentityInfo(generator = ObjectIdGenerators.IntSequenceGenerator.class, property = "id")
@ToString
public class Task extends GenericModel {

    @Column(name = "title", nullable = false)
    private String taskTitle;

    @Column(name = "description")
    private String description;

    @Column(name = "status", nullable = false)
    @Enumerated
    private Status status;

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
//   TODO Добавить поле окончание задачи
}

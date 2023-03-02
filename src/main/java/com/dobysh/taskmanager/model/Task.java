package com.dobysh.taskmanager.model;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Entity
@Table(name = "tasks")
@Getter
@Setter
@NoArgsConstructor
@SequenceGenerator(name = "default_gen", sequenceName = "tasks_seq", allocationSize = 1)
@JsonIdentityInfo(generator = ObjectIdGenerators.IntSequenceGenerator.class, property = "id")
public class Task extends GenericModel {

    @Column(name = "title", nullable = false)
    private String taskTitle;

    @Column(name = "description", nullable = false)
    private String description;

    @Column(name = "status", nullable = false)
    @Enumerated
    private Status status;

    @ManyToMany
    @JoinTable(name = "tasks_projects",
            joinColumns = @JoinColumn(name = "task_id"), foreignKey = @ForeignKey(name = "FK_TASKS_PROJECTS"),
            inverseJoinColumns = @JoinColumn(name = "project_id"), inverseForeignKey = @ForeignKey(name = "FK_PROJECTS_TASKS"))
    private Set<Project> projects;

    @OneToMany(mappedBy = "task")
    private Set<UserTasks> userTasks;
}

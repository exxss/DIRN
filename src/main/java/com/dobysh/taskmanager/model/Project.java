package com.dobysh.taskmanager.model;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Entity
@Table(name = "projects")
@Getter
@Setter
@NoArgsConstructor
@SequenceGenerator(name = "default_gen", sequenceName = "projects_seq", allocationSize = 1)
@JsonIdentityInfo(generator = ObjectIdGenerators.IntSequenceGenerator.class, property = "id")
public class Project extends GenericModel {

        @Column(name = "project_name", nullable = false)
        private String projectName;

        @ManyToMany
        @JoinTable(name = "tasks_projects",
                joinColumns = @JoinColumn(name = "project_id"), foreignKey = @ForeignKey(name = "FK_PROJECTS_TASKS"),
                inverseJoinColumns = @JoinColumn(name = "task_id"), inverseForeignKey = @ForeignKey(name = "FK_TASKS_PROJECTS"))
        private Set<Task> tasks;
}


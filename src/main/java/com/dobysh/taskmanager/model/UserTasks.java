package com.dobysh.taskmanager.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "user_tasks")
@Getter
@Setter
@NoArgsConstructor
@SequenceGenerator(name = "default_gen", sequenceName = "user_tasks_seq", allocationSize = 1)
public class UserTasks extends GenericModel {

    @ManyToOne
    @JoinColumn(name = "film_id", foreignKey = @ForeignKey(name = "FK_USER_TASKS_INFO_TASK"))
    private Task task;

    @ManyToOne
    @JoinColumn(name = "user_id", foreignKey = @ForeignKey(name = "FK_USER_TASKS_INFO_USER"))
    private User user;

    @Column(name = "task_date", nullable = false)
    private LocalDateTime taskDate;

    @Column(name = "time_complete", nullable = false)
    private Integer timeToComplete;

}


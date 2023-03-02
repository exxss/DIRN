package com.dobysh.taskmanager.dto;

import com.dobysh.taskmanager.model.Status;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TaskDTO extends GenericDTO {
    private String taskTitle;
    private String description;
    private Status status;
    private Set<Long> projects;
    private Set<Long> userTasks;
}

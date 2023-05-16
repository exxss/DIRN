package com.dobysh.taskmanager.dto;

import com.dobysh.taskmanager.model.Priority;
import com.dobysh.taskmanager.model.Status;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class TaskDTO extends GenericDTO {
    private String taskTitle;
    private String description;
    private Status status;
    private Priority priority;
    private Long projectId;
    private Long userId;
    private String expirationDate;

}

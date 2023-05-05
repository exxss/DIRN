package com.dobysh.taskmanager.dto;

import com.dobysh.taskmanager.model.Status;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class TaskDTO extends GenericDTO {
    private String taskTitle;
    private String description;
    private Status status;
    private Long projectId;
    private Long userId;

}

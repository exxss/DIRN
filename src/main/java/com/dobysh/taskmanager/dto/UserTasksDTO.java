package com.dobysh.taskmanager.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserTasksDTO extends GenericDTO {
    private Long taskId;
    private Long userId;
    private LocalDateTime taskDate;
    private Integer timeToComplete;
}

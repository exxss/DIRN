package com.dobysh.taskmanager.dto;

import lombok.*;

import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ProjectDTO extends GenericDTO {
    private String projectName;
    private Set<Long> tasksIds;
    private Long userId;
}

package com.dobysh.taskmanager.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
public class ProjectDTO extends GenericDTO {
    private String projectName;
    private Set<Long> tasks;
}

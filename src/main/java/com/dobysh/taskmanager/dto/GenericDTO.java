package com.dobysh.taskmanager.dto;

import lombok.*;
import java.time.LocalDateTime;


@Data
@NoArgsConstructor
public abstract class GenericDTO {
    private Long id;
    private String createdBy;
    private LocalDateTime createdWhen;
}



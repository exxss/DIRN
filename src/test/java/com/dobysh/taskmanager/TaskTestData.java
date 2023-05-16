package com.dobysh.taskmanager;

import com.dobysh.taskmanager.dto.ProjectDTO;
import com.dobysh.taskmanager.dto.TaskDTO;
import com.dobysh.taskmanager.dto.TasksWithProjectDTO;
import com.dobysh.taskmanager.model.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public interface TaskTestData {
    TaskDTO TASK_DTO_1 = new TaskDTO("taskTitle1",
            "description1",
            Status.PLANNED, Priority.PRIORITY_3,
            1L,
            1L, LocalDate.now().toString()
            );

    TaskDTO TASK_DTO_2 = new TaskDTO("taskTitle2",
            "description2",
            Status.IN_PROGRESS,Priority.PRIORITY_3,
            1L,
            1L,LocalDateTime.now().toString()
    );

    List<TaskDTO> TASK_DTO_LIST = Arrays.asList(TASK_DTO_1, TASK_DTO_2);

    Task TASK_1 = new Task("taskTitle1",
            "description1",
            Status.PLANNED,Priority.PRIORITY_3,
            new Project(),
            new User(),LocalDate.now()
    );
    Task TASK_2 = new Task("taskTitle2",
            "description2",
            Status.PLANNED,Priority.PRIORITY_3,
            new Project(),
            new User(),LocalDate.now()
    );

    List<Task> TASK_LIST = Arrays.asList(TASK_1, TASK_2);
    Set<ProjectDTO> PROJECT_DTOS = new HashSet<>(ProjectTestData.PROJECT_DTO_LIST);


}

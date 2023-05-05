package com.dobysh.taskmanager;

import com.dobysh.taskmanager.dto.ProjectDTO;
import com.dobysh.taskmanager.dto.TaskDTO;
import com.dobysh.taskmanager.dto.TasksWithProjectDTO;
import com.dobysh.taskmanager.model.Project;
import com.dobysh.taskmanager.model.Status;
import com.dobysh.taskmanager.model.Task;
import com.dobysh.taskmanager.model.User;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public interface TaskTestData {
    TaskDTO TASK_DTO_1 = new TaskDTO("taskTitle1",
            "description1",
            Status.PLANNED,
            1L,
            1L
            );

    TaskDTO TASK_DTO_2 = new TaskDTO("taskTitle2",
            "description2",
            Status.IN_PROGRESS,
            1L,
            1L
    );

    List<TaskDTO> TASK_DTO_LIST = Arrays.asList(TASK_DTO_1, TASK_DTO_2);

    Task TASK_1 = new Task("taskTitle1",
            "description1",
            Status.PLANNED,
            new Project(),
            new User()
    );
    Task TASK_2 = new Task("taskTitle2",
            "description2",
            Status.PLANNED,
            new Project(),
            new User()
    );

    List<Task> TASK_LIST = Arrays.asList(TASK_1, TASK_2);
    Set<ProjectDTO> PROJECT_DTOS = new HashSet<>(ProjectTestData.PROJECT_DTO_LIST);


}

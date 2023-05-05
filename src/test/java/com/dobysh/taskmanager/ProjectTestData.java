package com.dobysh.taskmanager;

import com.dobysh.taskmanager.dto.ProjectDTO;
import com.dobysh.taskmanager.dto.TaskDTO;
import com.dobysh.taskmanager.model.Project;
import com.dobysh.taskmanager.model.Status;
import com.dobysh.taskmanager.model.Task;
import com.dobysh.taskmanager.model.User;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

public interface ProjectTestData {
    ProjectDTO PROJECT_DTO_1 = new ProjectDTO("projectName1",
            new HashSet<>(),
            1L
    );

    ProjectDTO PROJECT_DTO_2 = new ProjectDTO("projectName2",
            new HashSet<>(),
            1L
    );

    List<ProjectDTO> PROJECT_DTO_LIST = Arrays.asList(PROJECT_DTO_1,PROJECT_DTO_2);

    Project PROJECT_1 = new Project("projectName1",
            new HashSet<>(),
            new User()
    );

    Project PROJECT_2 = new Project("projectName2",
            new HashSet<>(),
            new User()
    );

    List<Project> PROJECT_LIST = Arrays.asList(PROJECT_1,PROJECT_2);
}

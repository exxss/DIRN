package com.dobysh.taskmanager.controller;

import com.dobysh.taskmanager.dto.UserTasksDTO;
import com.dobysh.taskmanager.model.UserTasks;
import com.dobysh.taskmanager.service.UserTasksService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/userTasks")
@Tag(name = "Задачи пользователя", description = "Контроллер для работы с задачами пользователя")
public class UserTasksController extends GenericController<UserTasks, UserTasksDTO> {

    public UserTasksController(UserTasksService userTasksService) {
        super(userTasksService);
    }
}

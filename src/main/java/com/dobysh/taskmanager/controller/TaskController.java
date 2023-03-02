package com.dobysh.taskmanager.controller;

import com.dobysh.taskmanager.dto.TaskDTO;
import com.dobysh.taskmanager.model.Task;
import com.dobysh.taskmanager.service.TaskService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/films")
@Tag(name = "Фильмы",description = "Контроллер для работы с фильмами")
public class TaskController extends GenericController<Task, TaskDTO>{

    private final TaskService taskService;

    public TaskController(TaskService taskService) {
        super(taskService);
        this.taskService = taskService;
    }
//
//    @Operation(description = "Добавить режиссёра к фильму", method = "addDirector")
//    @RequestMapping(value = "/addDirector", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
//    public ResponseEntity<FilmDTO> addDirector(@RequestParam(value = "filmId") Long filmId,
//                                               @RequestParam(value = "directorId") Long directorId) {
//        return ResponseEntity.status(HttpStatus.OK).body(filmService.addDirector(filmId,directorId));
//    }
}

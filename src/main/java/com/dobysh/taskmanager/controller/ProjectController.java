package com.dobysh.taskmanager.controller;

import com.dobysh.taskmanager.dto.ProjectDTO;
import com.dobysh.taskmanager.model.Project;
import com.dobysh.taskmanager.service.ProjectService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/projects")
@Tag(name = "Проекты",description = "Контроллер для работы с проектами")
public class ProjectController extends GenericController<Project, ProjectDTO> {

        private final ProjectService projectService;

        public ProjectController(ProjectService projectService) {
                super(projectService);
                this.projectService = projectService;
        }

//        @Operation(description = "Добавить фильм к режиссёру", method = "addFilm")
//        @RequestMapping(value = "/addFilm", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
//        public ResponseEntity<DirectorDTO> addFilm(@RequestParam(value = "filmId") Long filmId,
//                                                @RequestParam(value = "directorId") Long directorId) {
//                return ResponseEntity.status(HttpStatus.OK).body(directorService.addFilm(filmId,directorId));
//        }
}

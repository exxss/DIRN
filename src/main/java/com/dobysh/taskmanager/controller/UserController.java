package com.dobysh.taskmanager.controller;

import com.dobysh.taskmanager.dto.UserDTO;
import com.dobysh.taskmanager.model.User;
import com.dobysh.taskmanager.service.UserService;
import com.dobysh.taskmanager.service.UserTasksService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
@Tag(name = "Пользователи",
        description = "Контроллер для работы с пользователями")
public class UserController
        extends GenericController<User, UserDTO> {

    private final UserService userService;
    private final UserTasksService userTasksService;

    public UserController(UserService userService, UserService userService1, UserTasksService userTasksService) {
        super(userService);
        this.userService = userService1;
        this.userTasksService = userTasksService;
    }

//    @Operation(description = "Взять фильм в аренду", method = "rentFilm")
//    @RequestMapping(value = "/rentFilm", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
//    public ResponseEntity<OrderDTO> rentFilm(@RequestParam(value = "userId") Long userId,
//                                            @RequestParam(value = "filmId") Long filmId,
//                                            @RequestParam(value = "rentPeriod") Integer rentPeriod) {
//        return ResponseEntity.status(HttpStatus.OK).body(orderService.rentFilm(userId,filmId,rentPeriod));
//    }
//
//    @Operation(description = "Купить фильм", method = "buyFilm")
//    @RequestMapping(value = "/buyFilm", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
//    public ResponseEntity<OrderDTO> buyFilm(@RequestParam(value = "userId") Long userId,
//                                            @RequestParam(value = "filmId") Long filmId) {
//        return ResponseEntity.status(HttpStatus.OK).body(orderService.buyFilm(userId,filmId));
//    }
//
//    @Operation(description = "Получить список всех арендованных/купленных фильмов", method = "getFilms")
//    @GetMapping(value = "/getFilms", produces = MediaType.APPLICATION_JSON_VALUE)
//    public ResponseEntity<Set<FilmDTO>> getFilmList(@RequestParam(value = "id") Long userId) {
//        return ResponseEntity.status(HttpStatus.CREATED).body(userService.getFilmList(userId));
//    }
}


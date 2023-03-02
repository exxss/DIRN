package com.dobysh.taskmanager.service;

import com.dobysh.taskmanager.dto.UserTasksDTO;
import com.dobysh.taskmanager.mapper.UserTasksMapper;
import com.dobysh.taskmanager.model.UserTasks;
import com.dobysh.taskmanager.repository.TaskRepository;
import com.dobysh.taskmanager.repository.UserRepository;
import com.dobysh.taskmanager.repository.UserTasksRepository;
import org.springframework.stereotype.Service;

@Service
public class UserTasksService extends GenericService<UserTasks, UserTasksDTO> {

    private final UserRepository userRepository;
    private final TaskRepository taskRepository;

    protected UserTasksService(UserTasksRepository userTasksRepository,
                               UserTasksMapper userTasksMapper,
                               UserRepository userRepository,
                               TaskRepository taskRepository){
        super(userTasksRepository,userTasksMapper);
        this.userRepository = userRepository;
        this.taskRepository = taskRepository;
    }

//    public OrderDTO rentFilm(Long userId, Long filmId, Integer rentPeriod){
//        User user = userRepository.findById(userId)
//                .orElseThrow(() -> new NotFoundException("Пользователь с таким ID не найден"));
//        Film film = filmRepository.findById(filmId)
//                .orElseThrow(() -> new NotFoundException("Фильм с таким ID не найден"));
//        Order order = new Order();
//        order.setRentPeriod(rentPeriod);
//        order.setUser(user);
//        order.setFilm(film);
//        order.setPurchase(false);
//        order.setCreatedBy("ADMIN");
//        order.setCreatedWhen(LocalDateTime.now());
//        order.setRentDate(LocalDateTime.now());
//        repository.save(order);
//        return mapper.toDto(order);
//    }
//
//    public OrderDTO buyFilm(Long userId, Long filmId){
//        User user = userRepository.findById(userId)
//                .orElseThrow(() -> new NotFoundException("Пользователь с таким ID не найден"));
//        Film film = filmRepository.findById(filmId)
//                .orElseThrow(() -> new NotFoundException("Фильм с таким ID не найден"));
//        Order order = new Order();
//        order.setRentPeriod(0);
//        order.setUser(user);
//        order.setFilm(film);
//        order.setPurchase(true);
//        order.setCreatedBy("ADMIN");
//        order.setCreatedWhen(LocalDateTime.now());
//        order.setRentDate(LocalDateTime.now());
//        repository.save(order);
//        return mapper.toDto(order);
//    }
}

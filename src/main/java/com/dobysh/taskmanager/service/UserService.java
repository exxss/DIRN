package com.dobysh.taskmanager.service;

import com.dobysh.taskmanager.dto.UserDTO;
import com.dobysh.taskmanager.mapper.TaskMapper;
import com.dobysh.taskmanager.mapper.UserMapper;
import com.dobysh.taskmanager.model.User;
import com.dobysh.taskmanager.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class UserService extends GenericService<User, UserDTO>{

    private final TaskMapper taskMapper;

    protected UserService(UserMapper userMapper,
                          UserRepository userRepository,
                          TaskMapper taskMapper) {
        super(userRepository, userMapper);
        this.taskMapper = taskMapper;
    }

//    @Override
//    public UserDTO create(UserDTO userDTO) {
//        RoleDTO roleDTO = new RoleDTO(1L,"ADMIN","for adm");
//        roleDTO.setId(1L);
//        userDTO.setRole(roleDTO);
//        userDTO.setCreatedWhen(LocalDateTime.now());
//        return mapper.toDto(repository.save(mapper.toEntity(userDTO)));
//    }
//
//    public Set<FilmDTO> getFilmList(Long userId) {
//        Set<FilmDTO> filmDTOS = new HashSet<>();
//        User user = repository.findById(userId)
//                .orElseThrow(() -> new NotFoundException("Пользователь с таким ID не найден"));
//        Set<Order> orders = user.getOrders();
//        for (Order order : orders) {
//                filmDTOS.add(filmMapper.toDto(order.getFilm()));
//        }
//        return filmDTOS;
//    }
}


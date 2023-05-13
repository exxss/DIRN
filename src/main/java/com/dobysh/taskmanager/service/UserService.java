package com.dobysh.taskmanager.service;

import com.dobysh.taskmanager.constants.MailConstants;
import com.dobysh.taskmanager.dto.RoleDTO;
import com.dobysh.taskmanager.dto.UserDTO;
import com.dobysh.taskmanager.mapper.UserMapper;
import com.dobysh.taskmanager.model.User;
import com.dobysh.taskmanager.repository.UserRepository;
import com.dobysh.taskmanager.utils.MailUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.webjars.NotFoundException;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
public class UserService extends GenericService<User, UserDTO>{

    private final JavaMailSender javaMailSender;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    protected UserService(UserRepository userRepository,
                          UserMapper userMapper,
                          BCryptPasswordEncoder bCryptPasswordEncoder,
                          JavaMailSender javaMailSender) {
        super(userRepository, userMapper);
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.javaMailSender = javaMailSender;
    }

    public UserDTO getUserByLogin(final String login) {
        return mapper.toDto(((UserRepository) repository).findUserByLogin(login));
    }

    public UserDTO getUserByEmail(final String email) {
        return mapper.toDto(((UserRepository) repository).findUserByEmail(email));
    }

    @Override
    public UserDTO create(UserDTO object) {
        RoleDTO roleDTO = new RoleDTO();
        roleDTO.setId(1L);//пользователь
        object.setRole(roleDTO);
        object.setCreatedBy("REGISTRATION FORM");
        object.setCreatedWhen(LocalDateTime.now());
        object.setPassword(bCryptPasswordEncoder.encode(object.getPassword()));
        return mapper.toDto(repository.save(mapper.toEntity(object)));
    }

    public void sendChangePasswordEmail(final UserDTO userDTO) {
        UUID uuid = UUID.randomUUID();
        userDTO.setChangePasswordToken(uuid.toString());
        update(userDTO);
        SimpleMailMessage mailMessage = MailUtils.createEmailMessage(userDTO.getEmail(),
                MailConstants.MAIL_SUBJECT_FOR_REMEMBER_PASSWORD,
                MailConstants.MAIL_MESSAGE_FOR_REMEMBER_PASSWORD + uuid);
        javaMailSender.send(mailMessage);
    }

    public void changePassword(final String uuid,
                               final String password) {
        UserDTO user = mapper.toDto(((UserRepository) repository).findUserByChangePasswordToken(uuid));
        user.setChangePasswordToken(null);
        user.setPassword(bCryptPasswordEncoder.encode(password));
        update(user);
    }

    public Page<UserDTO> findUsers(UserDTO userDTO,
                                   Pageable pageable) {
        Page<User> users = ((UserRepository) repository).searchUsers(userDTO.getFirstName(),
                userDTO.getLastName(),
                userDTO.getLogin(),
                pageable);
        List<UserDTO> result = mapper.toDTOs(users.getContent());
        return new PageImpl<>(result, pageable, users.getTotalElements());
    }


    @Override
    public void delete(Long id) {
        User user = repository.findById(id).orElseThrow(
                () -> new NotFoundException("Пользователя с заданным ID=" + id + " не существует"));
        markAsDeleted(user);
        repository.save(user);
    }

    public void restore(Long objectId) {
        User user = repository.findById(objectId).orElseThrow(
                () -> new NotFoundException("Пользователя с заданным ID=" + objectId + " не существует"));
        unMarkAsDeleted(user);
        repository.save(user);
    }

    public Page<UserDTO> listAll(Pageable pageable) {
        Page<User> objects = repository.findAll(pageable);
        List<UserDTO> result = mapper.toDTOs(objects.getContent());
        return new PageImpl<>(result, pageable, objects.getTotalElements());
    }
//    public List<String> getUserEmailsWithDelayedExpirationDate() {
//        return ((UserRepository) repository).getDelayedEmails();
//    }

}


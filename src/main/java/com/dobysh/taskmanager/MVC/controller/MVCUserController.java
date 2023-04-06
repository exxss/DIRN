package com.dobysh.taskmanager.MVC.controller;

import com.dobysh.taskmanager.constants.Errors;
import com.dobysh.taskmanager.dto.UserDTO;
import com.dobysh.taskmanager.exception.MyDeleteException;
import com.dobysh.taskmanager.service.UserService;
import com.dobysh.taskmanager.service.userdetails.CustomUserDetails;
import io.swagger.v3.oas.annotations.Hidden;
import jakarta.security.auth.message.AuthException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.websocket.server.PathParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import java.util.Objects;

import static com.dobysh.taskmanager.constants.UserRolesConstants.ADMIN;


@Controller
@Hidden
@RequestMapping("/users")
@Slf4j
public class MVCUserController {
    private final UserService userService;
    
    public MVCUserController(UserService userService) {
        this.userService = userService;
    }
    
    @GetMapping("/registration")
    public String registration(Model model) {
        model.addAttribute("userForm", new UserDTO());
        return "registration";
    }
    
    @PostMapping("/registration")
    public String registration(@ModelAttribute("userForm") UserDTO userDTO,
                               BindingResult bindingResult) {
        if (userDTO.getLogin().equalsIgnoreCase(ADMIN) || userService.getUserByLogin(userDTO.getLogin()) != null) {
            bindingResult.rejectValue("login", "error.login", "Такой логин уже существует");
            return "registration";
        }
        if (userService.getUserByEmail(userDTO.getEmail()) != null) {
            bindingResult.rejectValue("email", "error.email", "Такой email уже существует");
            return "registration";
        }
        userService.create(userDTO);
        return "redirect:/login";
    }
    
    @GetMapping("/remember-password")
    public String rememberPassword() {
        return "users/rememberPassword";
    }
    
    @PostMapping("/remember-password")
    public String rememberPassword(@ModelAttribute("changePasswordForm") UserDTO userDTO) {
        userDTO = userService.getUserByEmail(userDTO.getEmail());
        if (Objects.isNull(userDTO)) {
            return "redirect:/error/error-message?message=Пользователя с данным email не существует!";
        }
        else {
            userService.sendChangePasswordEmail(userDTO);
            return "redirect:/login";
        }
    }
    
    @GetMapping("/change-password")
    public String changePassword(@PathParam(value = "uuid") String uuid,
                                 Model model) {
        model.addAttribute("uuid", uuid);
        return "users/changePassword";
    }
    
    @PostMapping("/change-password")
    public String changePassword(@PathParam(value = "uuid") String uuid,
                                 @ModelAttribute("changePasswordForm") UserDTO userDTO) {
        userService.changePassword(uuid, userDTO.getPassword());
        return "redirect:/login";
    }
//    TODO передалать форму профиля пользователя
    @GetMapping("/profile/{id}")
    public String userProfile(@PathVariable Integer id,
                              Model model) throws AuthException {
        CustomUserDetails customUserDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (!Objects.isNull(customUserDetails.getUserId())) {
            if (!ADMIN.equalsIgnoreCase(customUserDetails.getUsername())) {
                if (!id.equals(customUserDetails.getUserId())) {
                    throw new AuthException(HttpStatus.FORBIDDEN + ": " + Errors.Users.USER_FORBIDDEN_ERROR);
                }
            }
        }
        model.addAttribute("user", userService.getOne(Long.valueOf(id)));
        return "profile/viewProfile";
    }
    
    @ExceptionHandler(AuthException.class)
    public RedirectView handleErrorAuth(HttpServletRequest req,
                                        Exception ex,
                                        RedirectAttributes redirectAttributes) {
        log.error("Запрос: " + req.getRequestURL() + " вызвал ошибку " + ex.getMessage());
        redirectAttributes.addFlashAttribute("exception", ex.getMessage());
        return new RedirectView("/users/list", true);
    }
//    TODO передалать форму профиля пользователя
    @GetMapping("/profile/update/{id}")
    public String updateProfile(@PathVariable Integer id,
                                Model model) throws AuthException {
        CustomUserDetails customUserDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (!id.equals(customUserDetails.getUserId())) {
            throw new AuthException(HttpStatus.FORBIDDEN + ": " + Errors.Users.USER_FORBIDDEN_ERROR);
        }
        model.addAttribute("userForm", userService.getOne(Long.valueOf(id)));
        return "profile/updateProfile";
    }
    //    TODO передалать форму профиля пользователя
    @PostMapping("/profile/update")
    public String updateProfile(@ModelAttribute("userForm") UserDTO userDTOFromUpdateForm,
                                BindingResult bindingResult) {
        UserDTO userEmailDuplicated = userService.getUserByEmail(userDTOFromUpdateForm.getEmail());
        UserDTO foundUser = userService.getOne(userDTOFromUpdateForm.getId());
        if (userEmailDuplicated != null && !Objects.equals(userEmailDuplicated.getEmail(), foundUser.getEmail())) {
            bindingResult.rejectValue("email", "error.email", "Такой email уже существует");
            return "profile/updateProfile";
        }
        foundUser.setFirstName(userDTOFromUpdateForm.getFirstName());
        foundUser.setLastName(userDTOFromUpdateForm.getLastName());
        foundUser.setEmail(userDTOFromUpdateForm.getEmail());
        userService.update(foundUser);
        return "redirect:/users/profile/" + userDTOFromUpdateForm.getId();
    }
    //    TODO передалать список всех пользователей
    @GetMapping("/list")
    public String listAllUsers(@RequestParam(value = "page", defaultValue = "1") int page,
                               @RequestParam(value = "size", defaultValue = "10") int pageSize,
                               @ModelAttribute(value = "exception") String exception,
                               Model model) {
        PageRequest pageRequest = PageRequest.of(page - 1, pageSize, Sort.by(Sort.Direction.ASC, "login"));
        Page<UserDTO> userPage = userService.listAll(pageRequest);
        model.addAttribute("users", userPage);
        model.addAttribute("exception", exception);
        return "users/viewAllUsers";
    }
    //    TODO передалать поиск пользователей
    @PostMapping("/search")
    public String searchUsers(@RequestParam(value = "page", defaultValue = "1") int page,
                              @RequestParam(value = "size", defaultValue = "5") int size,
                              @ModelAttribute("userSearchForm") UserDTO userDTO,
                              Model model) {
        PageRequest pageRequest = PageRequest.of(page - 1, size, Sort.by(Sort.Direction.ASC, "first_name"));
        model.addAttribute("users", userService.findUsers(userDTO, pageRequest));
        return "users/viewAllUsers";
    }
    //    TODO передалать удаление и восстановление пользователей
    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Long id) throws MyDeleteException {
        userService.delete(id);
        return "redirect:/users/list";
    }
    
    @GetMapping("/restore/{id}")
    public String restore(@PathVariable Long id) {
        userService.restore(id);
        return "redirect:/users/list";
    }
}

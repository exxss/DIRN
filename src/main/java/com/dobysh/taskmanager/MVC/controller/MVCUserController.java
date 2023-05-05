package com.dobysh.taskmanager.MVC.controller;

import com.dobysh.taskmanager.constants.Errors;
import com.dobysh.taskmanager.dto.UserDTO;
import com.dobysh.taskmanager.service.ProjectService;
import com.dobysh.taskmanager.service.UserService;
import com.dobysh.taskmanager.service.userdetails.CustomUserDetails;
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
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import java.util.Objects;
import java.util.UUID;

import static com.dobysh.taskmanager.constants.UserRolesConstants.ADMIN;


@Controller
@RequestMapping("/users")
@Slf4j
public class MVCUserController {
    private final UserService userService;
    private final ProjectService projectService;
    
    public MVCUserController(UserService userService, ProjectService projectService) {
        this.userService = userService;
        this.projectService = projectService;
    }

    @GetMapping("/registration")
    public String registration(Model model) {
        log.info("user registration");
        model.addAttribute("userForm", new UserDTO());
        return "registration";
    }
    
    @PostMapping("/registration")
    public String registration(@ModelAttribute("userForm") UserDTO userDTO,
                               BindingResult bindingResult) {
        log.info("user registration");
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
        log.info("user rememberPassword");
        return "users/rememberPassword";
    }
    
    @PostMapping("/remember-password")
    public String rememberPassword(@ModelAttribute("changePasswordForm") UserDTO userDTO) {
        log.info("user rememberPassword");
        userDTO = userService.getUserByEmail(userDTO.getEmail());
        if (Objects.isNull(userDTO)) {
            return "redirect:/error/error-message?message=Пользователя с данным email не существует!";
        }
        else {
            userService.sendChangePasswordEmail(userDTO);
            return "redirect:/login";
        }
    }

    @GetMapping("/change-password/user")
    public String changePassword(Model model) {
        log.info("user changePassword");
        CustomUserDetails customUserDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        UserDTO userDTO = userService.getOne(Long.valueOf(customUserDetails.getUserId()));
        UUID uuid = UUID.randomUUID();
        userDTO.setChangePasswordToken(uuid.toString());
        userService.update(userDTO);
        model.addAttribute("uuid", uuid);
        return "users/changePassword";
    }

    @GetMapping("/change-password")
    public String changePassword(@PathParam(value = "uuid") String uuid,
                                 Model model) {
        log.info("user changePassword");
        model.addAttribute("uuid", uuid);
        return "users/changePassword";
    }

    @PostMapping("/change-password")
    public String changePassword(@PathParam(value = "uuid") String uuid,
                                 @ModelAttribute("changePasswordForm") UserDTO userDTO) {
        log.info("user from email changePassword");
//        CustomUserDetails customUserDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        userService.changePassword(uuid, userDTO.getPassword());
//        return "redirect:/users/profile/" + customUserDetails.getUserId();
        return "redirect:/tasks/planned";
    }

    @GetMapping("/profile/{id}")
    public String userProfile(@PathVariable Integer id,
                              Model model) throws AuthException {
        log.info("user userProfile");
        CustomUserDetails customUserDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (!Objects.isNull(customUserDetails.getUserId())) {
            if (!ADMIN.equalsIgnoreCase(customUserDetails.getUsername())) {
                if (!id.equals(customUserDetails.getUserId())) {
                    throw new AuthException(HttpStatus.FORBIDDEN + ": " + Errors.Users.USER_FORBIDDEN_ERROR);
                }
            }
        }
        model.addAttribute("projects",projectService.viewAllUserProjects());
        model.addAttribute("user", userService.getOne(Long.valueOf(id)));
        return "profile/viewProfile";
    }

    @ExceptionHandler({RuntimeException.class,AuthException.class})
    public ModelAndView handle(Exception ex,HttpServletRequest req){
        ModelAndView model = new ModelAndView("error");
        model.addObject("exception", ex.getMessage());
        log.error("Запрос: " + req.getRequestURL() + " вызвал ошибку " + ex.getMessage());
        return model;
    }
    @GetMapping("/profile/update/{id}")
    public String updateProfile(@PathVariable Integer id,
                                Model model) throws AuthException {
        log.info("user updateProfile");
        CustomUserDetails customUserDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (!id.equals(customUserDetails.getUserId())) {
            throw new AuthException(HttpStatus.FORBIDDEN + ": " + Errors.Users.USER_FORBIDDEN_ERROR);
        }
        model.addAttribute("userForm", userService.getOne(Long.valueOf(id)));
        model.addAttribute("projects",projectService.viewAllUserProjects());
        return "profile/updateProfile";
    }
    @PostMapping("/profile/update")
    public String updateProfile(@ModelAttribute("userForm") UserDTO userDTOFromUpdateForm,
                                BindingResult bindingResult) {
        log.info("user updateProfile");
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
    @GetMapping("/list")
    public String listAllUsers(@RequestParam(value = "page", defaultValue = "1") int page,
                               @RequestParam(value = "size", defaultValue = "10") int pageSize,
                               @ModelAttribute(value = "exception") String exception,
                               Model model) {
        log.info("user listAllUsers");
        PageRequest pageRequest = PageRequest.of(page - 1, pageSize, Sort.by(Sort.Direction.ASC, "login"));
        Page<UserDTO> userPage = userService.listAll(pageRequest);
        model.addAttribute("users", userPage);
        model.addAttribute("exception", exception);
        return "users/viewAllUsers";
    }
    @PostMapping("/search")
    public String searchUsers(@RequestParam(value = "page", defaultValue = "1") int page,
                              @RequestParam(value = "size", defaultValue = "5") int size,
                              @ModelAttribute("userSearchForm") UserDTO userDTO,
                              Model model) {
        log.info("user searchUsers");
        PageRequest pageRequest = PageRequest.of(page - 1, size, Sort.by(Sort.Direction.ASC, "first_name"));
        model.addAttribute("users", userService.findUsers(userDTO, pageRequest));
        return "users/viewAllUsers";
    }
    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Long id) {
        log.info("user delete");
        userService.delete(id);
        return "redirect:/users/list";
    }
    
    @GetMapping("/restore/{id}")
    public String restore(@PathVariable Long id) {
        log.info("user restore");
        userService.restore(id);
        return "redirect:/users/list";
    }
}

package com.dobysh.taskmanager.MVC.controller;

import com.dobysh.taskmanager.constants.Errors;
import com.dobysh.taskmanager.dto.ProjectDTO;
import com.dobysh.taskmanager.dto.TaskDTO;
import com.dobysh.taskmanager.repository.ProjectRepository;
import com.dobysh.taskmanager.service.ProjectService;
import com.dobysh.taskmanager.service.TaskService;
import com.dobysh.taskmanager.service.UserService;
import com.dobysh.taskmanager.service.userdetails.CustomUserDetails;
import jakarta.security.auth.message.AuthException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;
import org.webjars.NotFoundException;

import static jakarta.servlet.RequestDispatcher.ERROR_STATUS_CODE;

@Controller
@RequestMapping("/projects")
@Slf4j
public class MVCProjectController {
    private final ProjectRepository projectRepository;

    private final ProjectService projectService;
    private final TaskService taskService;

    private final UserService userService;

    public MVCProjectController(ProjectService projectService, TaskService taskService,
                                ProjectRepository projectRepository, UserService userService) {
        this.projectService = projectService;
        this.taskService = taskService;
        this.projectRepository = projectRepository;
        this.userService = userService;
    }

    @GetMapping("/{id}")
    public String getOne(@PathVariable Long id,
                         Model model,@ModelAttribute("project") ProjectDTO projectDTO) throws AuthException {
        log.info("project getOne");
        CustomUserDetails userLoginDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        projectDTO = projectService.getOne(id);
        if((projectDTO.getUserId().intValue() != (userLoginDetails.getUserId()))){
            throw new AuthException(HttpStatus.FORBIDDEN + ": " + Errors.Projects.PROJECT_FORBIDDEN_ERROR);
        }
        model.addAttribute("projectsTask",projectService.viewAllTasksProject(id));
        model.addAttribute("projects",projectService.viewAllUserProjects());
        model.addAttribute("project",projectService.getOne(id));
        return "projects/viewProject";
    }

    @ExceptionHandler({RuntimeException.class,AuthException.class})
    public ModelAndView handle(Exception ex, HttpServletRequest req){
        ModelAndView model = new ModelAndView("error");
        model.addObject("exception", ex.getMessage());
        log.error("Запрос: " + req.getRequestURL() + " вызвал ошибку " + ex.getMessage());
        return model;
    }

    @PostMapping("/add")
    public String create(@ModelAttribute("projectForm") ProjectDTO projectDTO, HttpServletRequest request) {
        log.info("project create");
        projectService.create(projectDTO);
        return "redirect:" + request.getHeader("referer");
    }
    @PostMapping("/update/{id}")
    public String update(@PathVariable("id") Long id, @ModelAttribute("projectFormEdit") ProjectDTO projectDTO,HttpServletRequest request) {
        log.info("project update");
        projectService.update(projectDTO);
        return "redirect:" + request.getHeader("referer");
    }
    @GetMapping("/{projectId}/add-task")
    public String getProject(@PathVariable("projectId") Long projectId, Model model) {
        log.info("project getProject");
        model.addAttribute("project", projectService.getOne(projectId));
        return "projects/viewProject";
    }

    @PostMapping("/{projectId}/add-task")
    public String addTask(@PathVariable("projectId") Long projectId,@ModelAttribute("taskForm") TaskDTO taskDTO) {
        log.info("project addTask");
        projectService.addTask(taskService.create(taskDTO).getId(),projectId);
        return "redirect:/projects/" + projectId;
    }
    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Long id) {
        log.info("project delete");
        projectService.delete(id);
        return "redirect:/projects/" + id;
    }
}

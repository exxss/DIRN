package com.dobysh.taskmanager.MVC.controller;


import com.dobysh.taskmanager.constants.Errors;
import com.dobysh.taskmanager.dto.TaskDTO;
import com.dobysh.taskmanager.model.Status;
import com.dobysh.taskmanager.service.ProjectService;
import com.dobysh.taskmanager.service.TaskService;
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
@RequestMapping("tasks")
@Slf4j
public class MVCTaskController {

    private final TaskService taskService;
    private final ProjectService projectService;

    public MVCTaskController(TaskService taskService, ProjectService projectService) {
        this.taskService = taskService;
        this.projectService = projectService;
    }

    @GetMapping("/update/{id}")
    public String update(@PathVariable Long id,
                         Model model) throws AuthException {
        log.info("task update");
        CustomUserDetails userLoginDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        TaskDTO taskDTO = taskService.getOne(id);
        if((taskDTO.getUserId().intValue() != (userLoginDetails.getUserId()))){
            throw new AuthException(HttpStatus.FORBIDDEN + ": " + Errors.Tasks.TASK_FORBIDDEN_ERROR);
        }
        model.addAttribute("projects", projectService.viewAllUserProjects());
        model.addAttribute("taskId", id);
        model.addAttribute("task", taskService.getOne(id));
        return "tasks/updateTask";
    }

    @PostMapping("/update")
    public String update(@ModelAttribute("taskForm") TaskDTO taskDTO,HttpServletRequest request){
        log.info("task update");
        taskService.update(taskDTO);
        return "redirect:" + request.getHeader("referer");
    }
    @ExceptionHandler({RuntimeException.class,AuthException.class})
    public ModelAndView handle(Exception ex,HttpServletRequest req){
        ModelAndView model = new ModelAndView("error");
        model.addObject("exception", ex.getMessage());
        log.error("Запрос: " + req.getRequestURL() + " вызвал ошибку " + ex.getMessage());
        return model;
    }

    @GetMapping("/planned")
    public String getAllPlannedTasks(Model model){
        log.info("task getAllPlannedTasks");
        model.addAttribute("planedTasks",taskService.viewPlanedTasks());
        model.addAttribute("projects",projectService.viewAllUserProjects());
        return "tasks/planned";
    }

    @GetMapping("/inprogress")
    public String getAllInProgressTasks(Model model){
        log.info("task getAllInProgressTasks");
        model.addAttribute("inProgressTasks",taskService.viewInProgressTasks());
        model.addAttribute("projects",projectService.viewAllUserProjects());
        return "tasks/inprogress";
    }

    @GetMapping("/completed")
    public String getAllCompletedTasks(Model model){
        log.info("task getAllCompletedTasks");
        model.addAttribute("completedTasks",taskService.viewCompletedTasks());
        model.addAttribute("projects",projectService.viewAllUserProjects());
        return "tasks/completed";
    }

    @GetMapping("/canceled")
    public String getAllCanceledTasks(Model model){
        log.info("task getAllCanceled");
        model.addAttribute("canceledTasks",taskService.viewCanceledTasks());
        model.addAttribute("projects",projectService.viewAllUserProjects());
        return "tasks/canceled";
    }
    @GetMapping("/toInProgress/{id}")
    public String toProgress(@PathVariable Long id) {
        log.info("task toProgress");
        taskService.updateStatus(id, Status.IN_PROGRESS);
        return "redirect:/tasks/inprogress";
    }
    @GetMapping("/toCompleted/{id}")
    public String toCompleted(@PathVariable Long id) {
        log.info("task toCompleted");
        taskService.updateStatus(id, Status.COMPLETED);
        return "redirect:/tasks/inprogress";
    }
    @GetMapping("/toCanceled/{id}")
    public String toCanceled(@PathVariable Long id) {
        log.info("task toCanceled");
        taskService.updateStatus(id, Status.CANCELED);
        return "redirect:/tasks/inprogress";
    }
    @GetMapping("/returnToPlanned/{id}")
    public String returnToPlanned(@PathVariable Long id) {
        log.info("task returnToPlanned");
        taskService.updateStatus(id, Status.PLANNED);
        return "redirect:/tasks/canceled";
    }
    @GetMapping("/add")
    public String create() {
        log.info("task create");
        return "tasks/planned";
    }


    @PostMapping("/add")
    public String create(@ModelAttribute("taskForm") TaskDTO taskDTO) {
        log.info("task create");
        taskService.create(taskDTO);
        return "redirect:/tasks/planned";
    }
    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Long id, HttpServletRequest request) {
        log.info("task delete");
        taskService.delete(id);
        return "redirect:" + request.getHeader("referer");
    }
}

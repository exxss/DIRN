package com.dobysh.taskmanager.MVC.controller;



import com.dobysh.taskmanager.dto.ProjectDTO;
import com.dobysh.taskmanager.dto.TaskDTO;
import com.dobysh.taskmanager.exception.MyDeleteException;
import com.dobysh.taskmanager.model.Status;
import com.dobysh.taskmanager.model.Task;
import com.dobysh.taskmanager.service.ProjectService;
import com.dobysh.taskmanager.service.TaskService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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


    @GetMapping("/planned")
    public String getAllPlannedTasks(Model model){
        model.addAttribute("planedTasks",taskService.viewPlanedTasks());
        model.addAttribute("projects",projectService.viewAllUserProjects());
        return "tasks/planned";
    }

    @GetMapping("/inprogress")
    public String getAllInProgressTasks(Model model){
        model.addAttribute("inProgressTasks",taskService.viewInProgressTasks());
        model.addAttribute("projects",projectService.viewAllUserProjects());
        return "tasks/inprogress";
    }

    @GetMapping("/completed")
    public String getAllCompletedTasks(Model model){
        model.addAttribute("completedTasks",taskService.viewCompletedTasks());
        model.addAttribute("projects",projectService.viewAllUserProjects());
        return "tasks/completed";
    }

    @GetMapping("/canceled")
    public String getAll(Model model){
        model.addAttribute("canceledTasks",taskService.viewCanceledTasks());
        model.addAttribute("projects",projectService.viewAllUserProjects());
        return "tasks/canceled";
    }
    @GetMapping("/toInProgress/{id}")
    public String toProgress(@PathVariable Long id) {
        taskService.updateStatus(id, Status.IN_PROGRESS);
        return "redirect:/tasks/inprogress";
    }
    @GetMapping("/toCompleted/{id}")
    public String toCompleted(@PathVariable Long id) {
        taskService.updateStatus(id, Status.COMPLETED);
        return "redirect:/tasks/inprogress";
    }
    @GetMapping("/toCanceled/{id}")
    public String toCanceled(@PathVariable Long id) {
        taskService.updateStatus(id, Status.CANCELED);
        return "redirect:/tasks/inprogress";
    }
    @GetMapping("/returnToPlanned/{id}")
    public String returnToPlanned(@PathVariable Long id) {
        taskService.updateStatus(id, Status.PLANNED);
        return "redirect:/tasks/canceled";
    }
    @GetMapping("/add")
    public String create() {
        return "tasks/planned";
    }

    @PostMapping("/add")
    public String create(@ModelAttribute("taskForm") TaskDTO taskDTO) {
        taskService.create(taskDTO);
        return "redirect:/tasks/planned";
    }
    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Long id, HttpServletRequest request) throws MyDeleteException {
        taskService.delete(id);
        return "redirect:" + request.getHeader("referer");
    }
    @PostMapping("/update/{id}")
    public String update(@PathVariable("id") Long id, @ModelAttribute("taskFormEdit") TaskDTO taskDTO, HttpServletRequest request) {
        taskService.update(taskDTO);
        return "redirect:" + request.getHeader("referer");
    }

    //    TODO сделать перемещение по проектам
}

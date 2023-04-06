package com.dobysh.taskmanager.MVC.controller;

import com.dobysh.taskmanager.service.ProjectService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import static jakarta.servlet.RequestDispatcher.ERROR_REQUEST_URI;
import static jakarta.servlet.RequestDispatcher.ERROR_STATUS_CODE;

@Controller
@Slf4j
public class MVCErrorController
      implements ErrorController {

    ProjectService projectService;

    public MVCErrorController(ProjectService projectService) {
        this.projectService = projectService;
    }

    @RequestMapping("/error")
    public String handleError(HttpServletRequest httpServletRequest,
                              Model model) {
        log.error("Случилась беда! Ошибка {}",
                  httpServletRequest.getAttribute(ERROR_STATUS_CODE));
        model.addAttribute("exception",
                           "Ошибка " + httpServletRequest.getAttribute(ERROR_STATUS_CODE));
        model.addAttribute("projects",projectService.viewAllUserProjects());
        return "error";
    }
    
}

package com.dobysh.taskmanager.MVC.controller;

import io.swagger.v3.oas.annotations.Hidden;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@Slf4j
@Hidden
public class MVCLoginController {
    
    @GetMapping("/login")
    public String login() {
        log.info("login");
        return "login";
    }
    
}

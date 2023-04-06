package com.dobysh.taskmanager.MVC.controller;

import io.swagger.v3.oas.annotations.Hidden;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@Hidden
public class MVCLoginController {
    
    @GetMapping("/login")
    public String login() {
        return "login";
    }
    
}

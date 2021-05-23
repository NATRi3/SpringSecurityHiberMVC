package com.illilois.spring.controller;

import com.illilois.spring.entity.Role;
import com.illilois.spring.entity.User;
import com.illilois.spring.service.RoleService;
import com.illilois.spring.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.Set;

@Controller
@AllArgsConstructor
public class AppController {
    private final UserService userService;
    private final RoleService roleService;

    @GetMapping
    public String helloPage() {
        return "hello";
    }
}

package com.illilois.spring.controller;

import com.illilois.spring.entity.User;
import com.illilois.spring.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@AllArgsConstructor
@RequestMapping("/user")
public class UserController {
    private final UserService userService;

    @GetMapping("/{id}")
    public String getOneUser(@PathVariable("id") long id, Model model) {
        User currentUser = (User) SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getPrincipal();
        if (currentUser.getId() != id ) {
            return "redirect:/user/" + currentUser.getId();
        }
        model.addAttribute("userToShow", userService.getUserById(id));
        return "user";
    }
}

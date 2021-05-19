package com.illilois.spring.controller;

import com.illilois.spring.entity.Role;
import com.illilois.spring.entity.User;
import com.illilois.spring.service.RoleService;
import com.illilois.spring.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.Set;

@Controller
public class AppController {
    private final UserService userService;
    private final RoleService roleService;

    @Autowired
    public AppController(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }

    @GetMapping
    public String helloPage() {
        return "hello";
    }

    @GetMapping("/admin")
    public String getAllUsers(Model model) {
        model.addAttribute("usersToShow", userService.getAllUsers());
        return "admin";
    }

    @GetMapping("user/{id}")
    public String getOneUser(@PathVariable("id") long id, Model model) {
        User currentUser = (User) SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getPrincipal();
        if (currentUser.getId() != id && currentUser.getRoles().stream().noneMatch(role -> role.getName().contains("ADMIN"))) {
            return "redirect:/user/" + currentUser.getId();
        }
        model.addAttribute("userToShow", userService.getUserById(id));
        return "user";
    }

    @GetMapping("/admin/user/new")
    public String newUser(Model model) {
        model.addAttribute("newUser", new User());
        model.addAttribute("possibleRoles", roleService.getRoles());
        model.addAttribute("rolesSelected", new Long[]{});
        return "new_user";
    }

    @PostMapping("/admin")
    public String createUser(@RequestParam("rolesSelected") Long[] roleIds, @ModelAttribute("newUser") User user) {
        Set<Role> roles = new HashSet<>();
        for (Long idGiven : roleIds) {
            roles.add(roleService.getRoleById(idGiven));
        }
        user.setRoles(roles);
        userService.saveUser(user);
        return "redirect:/admin";
    }

    @GetMapping("/admin/user/{id}/edit")
    public String editUser(Model model, @PathVariable("id") long id) {
        model.addAttribute("possibleRoles", roleService.getRoles());
        model.addAttribute("rolesSelected", userService.getUserById(id).getRoles());
        model.addAttribute("userToUpdate", userService.getUserById(id));
        return "update_user";
    }

    @PatchMapping("/admin/user/{id}")
    public String updateUser(@ModelAttribute("userToUpdate") User user, @PathVariable("id") long id, @RequestParam("rolesSelected") Long[] roleIds) {
        for (Long idGiven : roleIds) {
            user.setRoles(Set.of(roleService.getRoleById(idGiven)));
        }
        userService.updateUser(id, user);
        return "redirect:/admin";
    }

    @DeleteMapping("/admin/user/{id}")
    public String deleteUser(@PathVariable("id") long id) {
        userService.deleteUser(id);
        return "redirect:/admin";
    }
}

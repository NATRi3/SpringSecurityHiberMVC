package com.illilois.spring.controller;

import com.illilois.spring.entity.Role;
import com.illilois.spring.entity.User;
import com.illilois.spring.service.RoleService;
import com.illilois.spring.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashSet;
import java.util.Set;

@Controller
@AllArgsConstructor
@RequestMapping("/admin")
public class AdminController {
    private final UserService userService;
    private final RoleService roleService;

    @GetMapping
    public String getAllUsers(Model model) {
        model.addAttribute("usersToShow", userService.getAllUsers());
        return "admin";
    }
    @GetMapping("/user/new")
    public String newUser(Model model) {
        model.addAttribute("newUser", new User());
        model.addAttribute("possibleRoles", roleService.getRoles());
        model.addAttribute("rolesSelected", new Long[]{});
        return "new_user";
    }

    @PostMapping
    public String createUser(@RequestParam("rolesSelected") Long[] roleIds, @ModelAttribute("newUser") User user) {
        Set<Role> roles = new HashSet<>();
        for (Long idGiven : roleIds) {
            roles.add(roleService.getRoleById(idGiven));
        }
        user.setRoles(roles);
        userService.saveUser(user);
        return "redirect:/admin";
    }

    @GetMapping("/user/{id}/edit")
    public String editUser(Model model, @PathVariable("id") long id) {
        model.addAttribute("possibleRoles", roleService.getRoles());
        model.addAttribute("rolesSelected", userService.getUserById(id).getRoles());
        model.addAttribute("userToUpdate", userService.getUserById(id));
        return "update_user";
    }

    @GetMapping("/user/{id}")
    public String getUser(Model model, @PathVariable("id") long id) {
        User currentUser = (User) SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getPrincipal();
        model.addAttribute("userToShow", userService.getUserById(id));
        return "user";
    }

    @PutMapping("/user/{id}")
    public String updateUser(@ModelAttribute("userToUpdate") User user, @PathVariable("id") long id, @RequestParam("rolesSelected") Long[] roleIds) {
        Set<Role> roles = new HashSet<>(roleIds.length);
        for (Long idGiven : roleIds) {
            roles.add(roleService.getRoleById(idGiven));
        }
        user.setRoles(roles);
        user.setId(id);
        userService.updateUser(user);
        return "redirect:/admin";
    }

    @PutMapping("/user/{id}/pass")
    public String updateUserPassword(@PathVariable("id") long id, @RequestParam("password") String password) {
        userService.updateUserPassword(id, password);
        return "redirect:/admin";
    }

    @DeleteMapping("/user/{id}")
    public String deleteUser(@PathVariable("id") long id) {
        userService.deleteUser(id);
        return "redirect:/admin";
    }
}

package com.illilois.spring.service;

import com.illilois.spring.entity.User;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;

public interface UserService{
    List<User> getAllUsers();

    void saveUser(User user);

    User getUserById(Long id);

    void deleteUser(Long id);

    void updateUser(User user);

    void updateUserPassword(Long id, String password);
}
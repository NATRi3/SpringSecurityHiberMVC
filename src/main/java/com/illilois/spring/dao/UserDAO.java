package com.illilois.spring.dao;

import com.illilois.spring.entity.User;

import java.util.List;

public interface UserDAO {
    List<User> getAllUsers();

    void saveUser(User user);

    User getUserById(Long id);

    User deleteUser(Long id);

    User getUserByUsername(String username);

    User updateUser(User user);

    void updateUserPassword(Long id, String password);
}


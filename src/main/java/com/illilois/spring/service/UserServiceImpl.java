package com.illilois.spring.service;

import com.illilois.spring.dao.UserDAO;
import com.illilois.spring.entity.User;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserDAO userDAO;
    private final PasswordEncoder encoder;
    private final SessionService sessionService;

    @Override
    public List<User> getAllUsers() {
        return userDAO.getAllUsers();
    }

    @Override
    @Transactional
    public void saveUser(User user) {
        user.setPassword(encoder.encode(user.getPassword()));
        userDAO.saveUser(user);
        sessionService.expireUserSessions(user.getUsername());
    }

    @Override
    public User getUserById(Long id) {
        return userDAO.getUserById(id);
    }

    @Override
    @Transactional
    public void deleteUser(Long id) {
        User user = userDAO.deleteUser(id);
        if (user != null) {
            sessionService.expireUserSessions(user.getUsername());
        }
    }

    @Override
    @Transactional
    public void updateUser(User user) {
        String username = userDAO.getUserById(user.getId()).getUsername();
        userDAO.updateUser(user);
        sessionService.expireUserSessions(username);
    }

    @Override
    @Transactional
    public void updateUserPassword(Long id, String password) {
        userDAO.updateUserPassword(id, encoder.encode(password));
    }
}
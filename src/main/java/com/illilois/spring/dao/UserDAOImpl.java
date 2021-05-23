package com.illilois.spring.dao;

import com.illilois.spring.entity.User;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
public class UserDAOImpl implements UserDAO {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<User> getAllUsers() {
        return entityManager.createQuery("from User u", User.class).getResultList();
    }

    @Override
    public void saveUser(User user) {
        entityManager.persist(user);
    }

    @Override
    public User getUserById(Long id) {
        return entityManager.find(User.class, id);
    }

    @Override
    public User deleteUser(Long id) {
        User user = entityManager.find(User.class, id);
        entityManager.remove(user);
        return user;
    }

    @Override
    public User getUserByUsername(String username) {
        return entityManager.createQuery("from User u where username = :username", User.class)
                .setParameter("username", username)
                .getSingleResult();
    }

    @Override
    public User updateUser(User user) {
        return entityManager.merge(user);
    }

    @Override
    public void updateUserPassword(Long id, String password) {
        entityManager.createQuery("update User set password = :password where id = :id")
                .setParameter("password", password)
                .setParameter("id", id)
                .executeUpdate();
    }
}
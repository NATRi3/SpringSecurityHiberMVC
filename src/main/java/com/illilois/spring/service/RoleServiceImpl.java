package com.illilois.spring.service;

import com.illilois.spring.dao.RoleDAO;
import com.illilois.spring.entity.Role;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@AllArgsConstructor
public class RoleServiceImpl implements RoleService {

    private final RoleDAO roleDAO;

    @Override
    public Role getRoleByName(String name) {
        return roleDAO.getRoleByName(name);
    }

    @Override
    @Transactional
    public void saveRole(Role role) {
        roleDAO.saveRole(role);
    }

    @Override
    public List<Role> getRoles() {
        return roleDAO.getRoles();
    }

    @Override
    public Role getRoleById(long id) {
        return roleDAO.getRoleById(id);
    }
}

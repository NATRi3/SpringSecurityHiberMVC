package com.illilois.spring.dao;


import com.illilois.spring.entity.Role;

import java.util.List;

public interface RoleDAO {

    Role getRoleByName(String name);

    void saveRole(Role role);

    List<Role> getRoles();

    public Role getRoleById(long id);
}

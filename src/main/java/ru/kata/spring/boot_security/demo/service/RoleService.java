package ru.kata.spring.boot_security.demo.service;

import ru.kata.spring.boot_security.demo.model.Role;

import java.util.List;

public interface RoleService{

    public Role findRoleById(Long id);

    public List<Role> getAllRoles();

    public void addRole(Role role);
}

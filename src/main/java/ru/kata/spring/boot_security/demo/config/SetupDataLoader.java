package ru.kata.spring.boot_security.demo.config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.service.UserServiceImpl;
import ru.kata.spring.boot_security.demo.service.RoleServiceImpl;

import java.util.HashSet;
import java.util.Set;

@Component
public class SetupDataLoader implements CommandLineRunner {
    private final UserServiceImpl userService;
    private final RoleServiceImpl roleService;

    public SetupDataLoader(UserServiceImpl userService, RoleServiceImpl roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }

    @Override
    public void run(String... args) throws Exception {
        Role roleAdmin = new Role("ROLE_ADMIN");
        Role roleUser = new Role("ROLE_USER");
        Set<Role> adminRoles = new HashSet<>();
        Set<Role> userRoles = new HashSet<>();
        roleService.addRole(roleAdmin);
        roleService.addRole(roleUser);
        adminRoles.add(roleAdmin);
        adminRoles.add(roleUser);
        userRoles.add(roleUser);

        User userAdmin = new User("admin","admin", "admin", 32);
        User userUser = new User("user","user", "user", 32);
        userAdmin.setRoles(adminRoles);
        userService.save(userAdmin);
        userUser.setRoles(userRoles);
        userService.save(userUser);
    }
}

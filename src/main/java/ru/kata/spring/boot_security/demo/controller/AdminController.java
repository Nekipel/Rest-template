package ru.kata.spring.boot_security.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.RoleService;
import ru.kata.spring.boot_security.demo.service.UserService;

import java.security.Principal;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private final UserService userService;
    private final RoleService roleService;

    public AdminController(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }

    @GetMapping()
    public String getAllUsers(Model model, Principal principal){
        model.addAttribute("users", userService.findAll());
        model.addAttribute("user", userService.findByName(principal.getName()));
        return "admin";
    }
    @GetMapping("/user")
    public String showUsers(Model model, Principal principal) {
        User princ = userService.findByName(principal.getName());
        model.addAttribute("princ", princ);
        return "user";
    }
}

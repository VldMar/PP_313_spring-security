package org.mrchv.springsecurity.controller;

import org.mrchv.springsecurity.model.User;
import org.mrchv.springsecurity.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private UserService userService;

    @GetMapping()
    public String showAdminPanel(ModelMap model) {
        model.put("users", userService.findAllUsers());
        return "admin/admin-panel";
    }

    @GetMapping("/add")
    public String showFormForCreateUser(ModelMap model) {
        model.addAttribute("user", new User());
        return "admin/add-user";
    }

    @PostMapping("/save")
    public String saveUser(@ModelAttribute("user") User user) {
        userService.saveUser(user);
        return "redirect:/admin";
    }
}

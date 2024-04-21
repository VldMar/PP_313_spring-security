package org.mrchv.springsecurity.controller;

import org.mrchv.springsecurity.model.User;
import org.mrchv.springsecurity.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping("/update/{id}")
    public String showFormForUpdateUser(@PathVariable("id") Long userId, ModelMap model) {
        User user = userService.findUserById(userId);
        model.addAttribute("user", user);
        return "admin/update-user";
    }

    @PostMapping("/add")
    public String saveUser(@ModelAttribute("user") User user) {
        userService.addUser(user);
        return "redirect:/admin";
    }

    @PutMapping("/update")
    public String updateUser(@ModelAttribute("user") User user) {
        userService.updateUser(user);
        return "redirect:/admin";
    }

    @DeleteMapping("/remove/{id}")
    public String removeUser(@PathVariable("id") Long userId) {
        userService.removeUserById(userId);
        return "redirect:/admin";
    }

}

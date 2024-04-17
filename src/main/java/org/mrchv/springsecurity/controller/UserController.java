package org.mrchv.springsecurity.controller;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/user")
public class UserController {

    @GetMapping()
    public String showUserPanel(@AuthenticationPrincipal UserDetails userDetails, ModelMap model) {
        model.put("user", userDetails);
        model.put("roles", AuthorityUtils.authorityListToSet(userDetails.getAuthorities()));
        return "user/user-info";
    }
}

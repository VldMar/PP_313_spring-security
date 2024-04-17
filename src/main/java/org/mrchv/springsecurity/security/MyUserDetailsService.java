package org.mrchv.springsecurity.security;

import jakarta.annotation.PostConstruct;
import org.mrchv.springsecurity.model.Role;
import org.mrchv.springsecurity.model.User;
import org.mrchv.springsecurity.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.time.LocalDate;
import java.util.*;

@Service
public class MyUserDetailsService implements UserDetailsService  {

    private final UserService userService;

    @Autowired
    public MyUserDetailsService(UserService userService) {
        this.userService = userService;
    }

    @PostConstruct
    public void init() {
        Role adminRole = new Role("ROLE_ADMIN");
        Role userRole = new Role("ROLE_USER");

        User admin = User.builder()
                .name("ivan")
                .lastName("ivanov")
                .birthday(LocalDate.of(1994, 04, 5))
                .username("admin")
                .password("admin")
                .roles(Set.of(adminRole, userRole))
                .build();

        userService.saveUser(admin);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userService.findByUserName(username);
        if (user == null) {
            throw new UsernameNotFoundException("User %s not found".formatted(username));
        }

        return user;
    }
}

package org.mrchv.springsecurity.security;

import jakarta.annotation.PostConstruct;
import org.mrchv.springsecurity.model.Role;
import org.mrchv.springsecurity.model.User;
import org.mrchv.springsecurity.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Set;

@Service
public class MyUserDetailsService implements UserDetailsService  {

    private final UserService userService;

    @Autowired
    public MyUserDetailsService(UserService userService) {
        this.userService = userService;
    }

    @PostConstruct
    public void init() {
        User admin = User.builder()
                .name("ivan")
                .lastName("ivanov")
                .birthday(LocalDate.of(1994, 04, 5))
                .username("admin")
                .password("admin")
                .roles(Set.of(new Role("ROLE_ADMIN"), new Role("ROLE_USER")))
                .build();

        userService.addUser(admin);


        User user = User.builder()
                .name("vladimir")
                .lastName("marychev")
                .birthday(LocalDate.of(1996, 9, 16))
                .username("user")
                .password("user")
                .roles(Set.of(new Role("ROLE_USER")))
                .build();

        userService.addUser(user);
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

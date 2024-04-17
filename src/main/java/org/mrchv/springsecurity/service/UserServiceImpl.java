package org.mrchv.springsecurity.service;

import org.mrchv.springsecurity.model.Role;
import org.mrchv.springsecurity.model.User;
import org.mrchv.springsecurity.repository.RoleRepository;
import org.mrchv.springsecurity.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Set;

@Service
public class UserServiceImpl implements UserService{

    private final UserRepository userRepo;
    private final RoleRepository roleRepo;
    private PasswordEncoder encoder;

    public UserServiceImpl(UserRepository userRepo, RoleRepository roleRepo, PasswordEncoder encoder) {
        this.userRepo = userRepo;
        this.roleRepo = roleRepo;
        this.encoder = encoder;
    }

    @Override
    public List<User> findAllUsers() {
        return userRepo.findAll();
    }

    @Override
    public User findUserById(Long id) {
        return userRepo.getById(id);
    }

    @Override
    public User findByUserName(String username) {
        return userRepo.findByUsername(username);
    }

    @Override
    public void saveUser(User user) {
        User userFromDb = findByUserName(user.getUsername());
        if (userFromDb != null) {
            return;
        }

        if (user.getRoles() == null) {
            user.setRoles(Set.of(new Role("ROLE_USER")));
        }

        user.setPassword(encoder.encode(user.getPassword()));
        userRepo.save(user);
    }


    @Override
    public void removeUserById(Long id) {
        userRepo.deleteById(id);
    }
}

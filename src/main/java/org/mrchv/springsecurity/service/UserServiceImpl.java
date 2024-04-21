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
    private PasswordEncoder encoder;

    public UserServiceImpl(UserRepository userRepo, PasswordEncoder encoder) {
        this.userRepo = userRepo;
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
    public void addUser(User user) {
        User userFromDB = findByUserName(user.getUsername());
        if (userFromDB != null) {
            throw new RuntimeException("Пользователь с username=%s уже существует!".formatted(user.getUsername()));
        }

        if(user.getRoles() == null) {
            user.setRoles(Set.of(new Role("ROLE_USER")));
        }

        user.setPassword(encoder.encode(user.getPassword()));
        userRepo.save(user);
    }

    @Override
    public void updateUser(User user) {
        User userFromDB = findUserById(user.getId());
        if (userFromDB == null) {
            throw new RuntimeException("Пользователь с username=%s не найден!".formatted(user.getUsername()));
        }

        if (user.getRoles() == null) {
            user.setRoles(userFromDB.getRoles());
        }

        String password = user.getPassword() == ""
                ?   userFromDB.getPassword()
                :   encoder.encode(user.getPassword());

        user.setPassword(password);
        userRepo.save(user);
    }

    @Override
    public void removeUserById(Long id) {
        userRepo.deleteById(id);
    }
}

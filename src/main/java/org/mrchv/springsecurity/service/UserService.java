package org.mrchv.springsecurity.service;

import org.mrchv.springsecurity.model.User;

import java.util.List;

public interface UserService {
    List<User> findAllUsers();
    User findUserById(Long id);
    User findByUserName(String username);
    void saveUser(User user);
    void removeUserById(Long id);
}

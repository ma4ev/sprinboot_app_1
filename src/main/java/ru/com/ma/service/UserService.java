package ru.com.ma.service;

import ru.com.ma.domain.User;

import java.util.List;

public interface UserService{

    User findUserByUserName(String username);
    User save(User user);
    List<User> findAll();
    void delete(User user);
    void updateProfile(User user, String password, String email);

    boolean activateUser(String code);
}

package ru.com.ma.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.com.ma.domain.Role;
import ru.com.ma.domain.User;
import ru.com.ma.repositories.UserRepository;

import java.util.Collections;
import java.util.List;

@Service
public class UserServiceImpl implements ru.com.ma.service.UserService{

    private UserRepository repo;

    private PasswordEncoder passwordEncoder;

    @Autowired
    public UserServiceImpl(UserRepository repo, PasswordEncoder passwordEncoder){

        this.repo = repo;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return repo.findByUsername(username);
    }

    @Override
    public User findUserByUserName(String username) {
        return repo.findByUsername(username);
    }

    @Override
    public User save(User user) {
        if(user.getId() == null){
            user.setActive(true);
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            user.setRoles(Collections.singleton(Role.USER));
        }
        return repo.save(user);
    }

    public List<User> findAll(){
        return repo.findAll();
    }

    @Transactional
    public void delete(User user){
        repo.delete(user);
    }


}
package ru.com.ma.service.impl;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.com.ma.domain.Role;
import ru.com.ma.domain.User;
import ru.com.ma.exceptions.ActivationCodeException;
import ru.com.ma.repositories.UserRepository;
import ru.com.ma.service.MailSender;

import java.util.Collections;
import java.util.List;
import java.util.UUID;
import java.util.logging.Logger;

@Service
public class UserServiceImpl implements ru.com.ma.service.UserService{

    private static final Logger logger = Logger.getLogger(UserServiceImpl.class.getName());

    private UserRepository repo;
    private PasswordEncoder passwordEncoder;
    private MailSender mailSender;

    @Autowired
    public UserServiceImpl(UserRepository repo, PasswordEncoder passwordEncoder, MailSender mailSender){
        this.repo = repo;
        this.passwordEncoder = passwordEncoder;
        this.mailSender = mailSender;
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
            user.setActivationCode(UUID.randomUUID().toString());
        }
        User user1 = repo.save(user);
        try {
            mailSender.sendActivationCode(user1);
        } catch (ActivationCodeException e){
            logger.warning(e.getMessage());
        }

        return user1;
    }

    public List<User> findAll(){
        return repo.findAll();
    }

    @Transactional
    public void delete(User user){
        repo.delete(user);
    }

    @Override
    public boolean activateUser(String code) {

        User user = repo.findByActivationCode(code);

        if (user == null){
            return false;
        }

        user.setActivationCode(null);
        repo.save(user);

        return true;
    }

    @Override
    public void updateProfile(User user, String password, String email) {
        String currentEmail = user.getEmail();

        if(StringUtils.isNotBlank(currentEmail) && StringUtils.isNotBlank(password) && StringUtils.isNotBlank(email)){
            Boolean isEmailChanged = !email.equals(currentEmail);

            if(isEmailChanged){
                user.setEmail(email);
                user.setActivationCode(UUID.randomUUID().toString());
                user.setPassword(password);
                repo.save(user);

                try {
                    mailSender.sendActivationCode(user);
                } catch (ActivationCodeException e){
                    logger.warning(e.getMessage());
                }
            }
        }


    }
}

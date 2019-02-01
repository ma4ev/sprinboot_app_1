package ru.com.ma.service;

import ru.com.ma.domain.User;
import ru.com.ma.exceptions.ActivationCodeException;

public interface MailSender {

    void sendActivationCode(User user) throws ActivationCodeException;
}

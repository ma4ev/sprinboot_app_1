package ru.com.ma.service.impl;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import ru.com.ma.domain.User;
import ru.com.ma.exceptions.ActivationCodeException;
import ru.com.ma.service.MailSender;

@Service
public class MailSenderImpl implements MailSender {

    private final String subject = "Activation code";
    private final String msg = "Hello, %s! \n" +
            "Welcome to my app.Please, visit this link: http:localhost:8091/activate/%s";

    private JavaMailSender sender;

    @Value("${spring.mail.username}")
    private String from;

    @Autowired
    public MailSenderImpl(JavaMailSender sender){
        this.sender = sender;
    }

    @Override
    public void sendActivationCode(User user) throws ActivationCodeException {
        if(StringUtils.isBlank(user.getEmail())){
            throw new ActivationCodeException("Email is empty!");
        }

        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(from);
        message.setTo(user.getEmail());
        message.setSubject(subject);
        message.setText(String.format(msg,
                user.getUsername(),
                user.getActivationCode()));

        sender.send(message);
    }
}

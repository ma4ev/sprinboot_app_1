package ru.com.ma.service.impl;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.com.ma.domain.Message;
import ru.com.ma.domain.MessageFilter;
import ru.com.ma.domain.User;
import ru.com.ma.repositories.MessageRepository;

import java.util.List;

@Service
public class MessageServiceImpl implements ru.com.ma.service.MessageService {

    private MessageRepository repo;

    @Autowired
    public MessageServiceImpl(MessageRepository repo){
        this.repo = repo;
    }

    public Iterable<Message> getList(){
        return repo.findAll();
    }

    public Message save(Message message, User user){
        message.setAuthor(user);
        return repo.save(message);
    }

    public List<Message> getByFilter(MessageFilter filter){
        return repo.getByTag(filter.getTerm());
    }
}

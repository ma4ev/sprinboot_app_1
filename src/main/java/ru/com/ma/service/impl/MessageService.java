package ru.com.ma.service.impl;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.com.ma.domain.Message;
import ru.com.ma.repositories.MessageRepository;

@Service
public class MessageService implements ru.com.ma.service.MessageService {

    private MessageRepository repo;

    @Autowired
    public MessageService(MessageRepository repo){
        this.repo = repo;
    }

    public Iterable<Message> getList(){
        return repo.findAll();
    }
}

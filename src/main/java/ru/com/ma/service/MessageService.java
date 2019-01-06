package ru.com.ma.service;

import ru.com.ma.domain.Message;
import ru.com.ma.domain.MessageFilter;
import ru.com.ma.domain.User;

import java.util.List;

public interface MessageService {

    Iterable<Message> getList();
    Message save(Message message, User user);
    List<Message> getByFilter(MessageFilter filter);
}

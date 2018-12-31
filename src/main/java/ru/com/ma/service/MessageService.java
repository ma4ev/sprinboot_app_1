package ru.com.ma.service;

import ru.com.ma.domain.Message;

public interface MessageService {

    Iterable<Message> getList();
}

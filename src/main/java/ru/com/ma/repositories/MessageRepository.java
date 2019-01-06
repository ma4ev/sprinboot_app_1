package ru.com.ma.repositories;

import org.springframework.data.repository.CrudRepository;
import ru.com.ma.domain.Message;
import ru.com.ma.domain.MessageFilter;

import java.util.List;

public interface MessageRepository extends CrudRepository<Message, Long> {

    List<Message> getByTag(String tag);
}

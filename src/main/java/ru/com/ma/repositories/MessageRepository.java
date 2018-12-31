package ru.com.ma.repositories;

import org.springframework.data.repository.CrudRepository;
import ru.com.ma.domain.Message;

public interface MessageRepository extends CrudRepository<Message, Long> {
}

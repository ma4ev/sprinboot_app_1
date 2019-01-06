package ru.com.ma.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.com.ma.domain.User;

public interface UserRepository extends JpaRepository<User, Long>{

    User findByUsername(String username);
}

package ru.kata.spring.boot_security.demo.services;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.transaction.annotation.Transactional;
import ru.kata.spring.boot_security.demo.models.Role;
import ru.kata.spring.boot_security.demo.models.User;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface UserService extends UserDetailsService {
    UserDetails loadUserByUsername(String email);
    List<User> findAll();
    Optional<User> findByUsername(String username);
    User findOne(int id);
    void save(User user);

    void update(int id, User updatedUser);
    void delete(int id);
}

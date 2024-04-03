package ru.kata.spring.boot_security.demo.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kata.spring.boot_security.demo.models.Role;
import ru.kata.spring.boot_security.demo.models.User;
import ru.kata.spring.boot_security.demo.repositories.UserRepository;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;


@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    private final RoleService roleService;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, RoleService roleService) {
        this.userRepository = userRepository;
        this.roleService = roleService;
    }


    @Override
    @Transactional
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Optional<User> user = findByUsername(email);

        if (user.isEmpty()) {
            throw new UsernameNotFoundException( "User not found" );
        }

        return user.get();
    }


    @Override
    public List<User> findAll() {
        return userRepository.findAll();
    }

    @Override
    public Optional<User> findByUsername(String email) {
        return userRepository.findByUsername(email);
    }

    @Transactional
    @Override
    public void save(User user, List<String> formRoles) {
        user.setPassword( new BCryptPasswordEncoder().encode(user.getPassword()));
        if (formRoles.isEmpty()) {
            user.setRoles( Collections.singleton(new Role("USER")));
        } else {
            user.setRoles(roleService.findFormRole(formRoles));
        }
        userRepository.save( user );
    }

    @Transactional
    @Override
    public void update(User updatedUser, List<String> formRoles) {
        User user = findByUsername(updatedUser.getEmail()).get();
        if (!user.getPassword().equals( updatedUser.getPassword() )) {
            updatedUser.setPassword(new BCryptPasswordEncoder().encode(updatedUser.getPassword()));
        }
        user.setRoles(roleService.findFormRole(formRoles));
        userRepository.save(user);
    }

    @Transactional
    @Override
    public void delete(int id) {
        userRepository.deleteById( id );
    }
}

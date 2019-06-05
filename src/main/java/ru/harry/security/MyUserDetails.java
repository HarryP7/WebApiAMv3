package ru.harry.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.harry.Entity.Role;
import ru.harry.Entity.User;
import ru.harry.Repository.UsersRepository;

import java.util.ArrayList;
import java.util.Arrays;

@Service
public class MyUserDetails implements UserDetailsService {
    @Autowired
    private UsersRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
        final User user = userRepository.findByLogin(login);

        if (user == null) {
            throw new UsernameNotFoundException("User '" + login + "' not found");
        }

        return org.springframework.security.core.userdetails.User//
                .withUsername(login)//
                .password(user.getPassword())//
                .authorities(user.getRole())//
                .accountExpired(false)//
                .accountLocked(false)//
                .credentialsExpired(false)//
                .disabled(false)//
                .build();
    }
}

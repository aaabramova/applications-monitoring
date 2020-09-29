package com.abramova.applications.monitoring.services;

import com.abramova.applications.monitoring.repositories.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserService implements UserDetailsService {
    @Autowired
    private UserRepo userRepo;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//        return userRepo.getByUsername(username);
        com.abramova.applications.monitoring.entities.User user = userRepo.getByUsername(username);

        return new User(user.getUsername(), user.getPassword(), user.getAuthorities());
    }
}

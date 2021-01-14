package com.abramova.applications.monitoring.services;

import com.abramova.applications.monitoring.enums.Role;
import com.abramova.applications.monitoring.entities.User;
import com.abramova.applications.monitoring.exceptions.UserNotFoundException;
import com.abramova.applications.monitoring.repositories.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class AdminService {
    @Autowired
    private UserRepo userRepo;

    public Page<User> findAllUsers(Pageable pageable) {
        return userRepo.findAll(pageable);
    }

    public User updateUserRole(long id, Role role) {
        User user = userRepo.findById(id)
                .orElseThrow(() -> new UserNotFoundException("User with id = " + id + " not found."));

        Set<Role> userRoles = user.getRoles();
        userRoles.add(role);
        user.setRoles(userRoles);

        return userRepo.save(user);
    }
}

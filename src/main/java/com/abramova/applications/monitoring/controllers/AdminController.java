package com.abramova.applications.monitoring.controllers;

import com.abramova.applications.monitoring.entities.User;
import com.abramova.applications.monitoring.repositories.UserRepo;
import com.abramova.applications.monitoring.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@PreAuthorize("hasAuthority('ADMIN')")
@RequestMapping("/admin/users")
public class AdminController {

    @Autowired
    private UserRepo userRepo;

    @GetMapping("/page/{pageNumber}")
    public List<User> getAllUsers(@PathVariable("pageNumber") int pageNumber) {
        return userRepo.getAll(pageNumber);
    }

    @PutMapping("/{userId}")
    public User updateUserRoleToOperator(@PathVariable("userId") long id) {
        return userRepo.updateUserRole(id, Role.OPERATOR);
    }
}

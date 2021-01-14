package com.abramova.applications.monitoring.controllers;

import com.abramova.applications.monitoring.enums.Role;
import com.abramova.applications.monitoring.entities.User;
import com.abramova.applications.monitoring.services.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin/users")
public class AdminController {
    @Autowired
    private AdminService adminService;

    @GetMapping("")
    public Page<User> getAllUsers(@PageableDefault(sort = {"id"}, direction = Sort.Direction.ASC) Pageable pageable) {
        return adminService.findAllUsers(pageable);
    }

    @PutMapping("/{userId}")
    public User updateUserRoleToOperator(@PathVariable("userId") long id) {
        return adminService.updateUserRole(id, Role.OPERATOR);
    }
}

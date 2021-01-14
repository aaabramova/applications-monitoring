package com.abramova.applications.monitoring.controllers;

import com.abramova.applications.monitoring.entities.Application;
import com.abramova.applications.monitoring.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/apps")
public class UserController {
    @Autowired
    private UserService userService;

    @GetMapping()
    public Page<Application> getAllApplicationsFromUser(Authentication authentication,
                                                        @PageableDefault(sort = {"id"}, direction = Sort.Direction.DESC)
                                                                Pageable pageable) {
        return userService.findAllByUser((UserDetails) authentication.getPrincipal(), pageable);
    }

    @GetMapping("/{appId}")
    public Application getApplicationById(Authentication authentication, @PathVariable("appId") long id) {
        return userService.findApplicationById((UserDetails) authentication.getPrincipal(), id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Application createApplication(Authentication authentication, @RequestBody Application application) {
        return userService.createApplication((UserDetails) authentication.getPrincipal(), application);
    }

    @PutMapping("/draft/{appId}")
    public Application updateDraftApplication(@PathVariable("appId") long id, @RequestBody String text) {
        return userService.updateDraftApplication(id, text);
    }

    @PutMapping("/{appId}")
    public Application updateDraftApplicationToSent(@PathVariable("appId") long id, @RequestBody String text) {
        return userService.updateDraftApplicationToSent(id, text);
    }
}

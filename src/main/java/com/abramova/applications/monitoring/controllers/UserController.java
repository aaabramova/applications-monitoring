package com.abramova.applications.monitoring.controllers;

import com.abramova.applications.monitoring.entities.Application;
import com.abramova.applications.monitoring.exceptions.AccessApplicationException;
import com.abramova.applications.monitoring.repositories.ApplicationRepo;
import com.abramova.applications.monitoring.repositories.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@PreAuthorize("hasAuthority('USER')")
@RequestMapping("/apps")
public class UserController {

    @Autowired
    private ApplicationRepo applicationRepo;

    @Autowired
    private UserRepo userRepo;

    @GetMapping("/page/{pageNumber}")
    public List<Application> getAllApplicationsFromUser(Authentication authentication, @PathVariable("pageNumber") int pageNumber) {
        return applicationRepo.getAllFromUser((UserDetails) authentication.getPrincipal(), pageNumber);
    }

    @GetMapping("/{appId}")
    public Application getApplicationById(Authentication authentication, @PathVariable("appId") long id) {
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        Application application = applicationRepo.getById(id);
        if(application.getAuthor().getUsername().equals(userDetails.getUsername())) {
            return application;
        } else {
            throw new AccessApplicationException("Application with id = " + id + " can not be accessed by this user.");
        }
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Application createApplication(Authentication authentication, @RequestBody Application application) {
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        application.setAuthor(userRepo.getByUsername(userDetails.getUsername()));
        return applicationRepo.create(application);
    }

    @PutMapping("/draft/{appId}")
    public Application updateDraftApplication(@PathVariable("appId") long id, @RequestBody String text) {
        return applicationRepo.update(id, text);
    }

    @PutMapping("/{appId}")
    public Application updateDraftApplicationToSent(@PathVariable("appId") long id, @RequestBody String text) {
        return applicationRepo.draftToSent(id, text);
    }
}

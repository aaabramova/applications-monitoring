package com.abramova.applications.monitoring.services;

import com.abramova.applications.monitoring.enums.Status;
import com.abramova.applications.monitoring.entities.Application;
import com.abramova.applications.monitoring.exceptions.AccessApplicationException;
import com.abramova.applications.monitoring.exceptions.ApplicationNotFoundException;
import com.abramova.applications.monitoring.repositories.ApplicationRepo;
import com.abramova.applications.monitoring.repositories.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class UserService implements UserDetailsService {
    @Autowired
    private UserRepo userRepo;
    @Autowired
    private ApplicationRepo applicationRepo;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        com.abramova.applications.monitoring.entities.User user = userRepo.findByUsername(username);

        return new User(user.getUsername(), user.getPassword(), user.getAuthorities());
    }

    public Page<Application> findAllByUser(UserDetails userDetails, Pageable pageable) {
        com.abramova.applications.monitoring.entities.User user = userRepo.findByUsername(userDetails.getUsername());

        return applicationRepo.findAllByAuthor(user, pageable);
    }

    public Application findApplicationById(UserDetails userDetails, long id) {
        Application application = applicationRepo.findById(id)
                .orElseThrow(() -> new ApplicationNotFoundException("Application with id = " + id + " not found."));
        if(application.getAuthor().getUsername().equals(userDetails.getUsername())) {
            return application;
        } else {
            throw new AccessApplicationException("Application with id = " + id + " can not be accessed by this user.");
        }
    }

    public Application createApplication(UserDetails userDetails, Application application) {
        application.setAuthor(userRepo.findByUsername(userDetails.getUsername()));
        application.setDateOfCreation(LocalDateTime.now());
        return applicationRepo.save(application);
    }

    public Application updateDraftApplication(long id, String text) {
        Application application = applicationRepo.findById(id)
                .orElseThrow(() -> new ApplicationNotFoundException("Application with id = " + id + " not found."));
        if (application.getStatus() != Status.DRAFT) {
            throw new AccessApplicationException("Application must have draft status.");
        }
        application.setDateOfCreation(LocalDateTime.now());
        application.setText(text);
        return applicationRepo.save(application);
    }

    public Application updateDraftApplicationToSent(long id, String text) {
        Application application = this.updateDraftApplication(id, text);
        application.setStatus(Status.SENT);
        return applicationRepo.save(application);
    }
}

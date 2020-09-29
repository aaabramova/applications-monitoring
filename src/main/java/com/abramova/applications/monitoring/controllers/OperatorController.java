package com.abramova.applications.monitoring.controllers;

import com.abramova.applications.monitoring.entities.Application;
import com.abramova.applications.monitoring.repositories.ApplicationRepo;
import com.abramova.applications.monitoring.Status;
import com.abramova.applications.monitoring.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@PreAuthorize("hasAuthority('OPERATOR')")
@RequestMapping("/operator")
public class OperatorController {

    @Autowired
    private ApplicationRepo applicationRepo;

    @GetMapping("/apps/page/{pageNumber}")
    public List<Application> getAllApplications(@PathVariable("pageNumber") int pageNumber) {
        List<Application> applications = applicationRepo.getAllWithStatus(Status.SENT, pageNumber);
        for (Application application : applications) {
            application.setText(new Utils().addSymbolBetweenLettersInString(application.getText(), "-"));
        }
        return applications;
    }

    @GetMapping("/apps/{appId}")
    public Application getApplicationById(@PathVariable("appId") long id) {
        Application application = applicationRepo.getById(id);
        application.setText(new Utils().addSymbolBetweenLettersInString(application.getText(), "-"));
        return application;
    }

    @PutMapping("/apps/{appId}")
    public Application updateApplicationStatus(@PathVariable("appId") long id, @RequestBody String status) {
        return applicationRepo.setStatus(id, Status.valueOf(status.toUpperCase()));
    }
}

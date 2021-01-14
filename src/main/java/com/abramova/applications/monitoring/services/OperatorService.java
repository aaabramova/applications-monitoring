package com.abramova.applications.monitoring.services;

import com.abramova.applications.monitoring.enums.Status;
import com.abramova.applications.monitoring.entities.Application;
import com.abramova.applications.monitoring.exceptions.ApplicationNotFoundException;
import com.abramova.applications.monitoring.repositories.ApplicationRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

@Service
public class OperatorService {
    @Autowired
    private ApplicationRepo applicationRepo;

    public Page<Application> findAllApplications(Pageable pageable) {
        Page<Application> applications = applicationRepo.findAllByStatus(Status.SENT, pageable);

        applications.forEach(application ->
                application.setText(addSubstringBetweenLettersInString(application.getText(), "-")));

        return applications;
    }

    public Application findApplicationById(long id) {
        Application application = applicationRepo.findById(id)
                .orElseThrow(() -> new ApplicationNotFoundException("Application with id = " + id + " not found."));

        application.setText(addSubstringBetweenLettersInString(application.getText(), "-"));

        return application;
    }

    public Application updateApplicationStatus(long id, String status) {
        Application application = applicationRepo.findById(id)
                .orElseThrow(() -> new ApplicationNotFoundException("Application with id = " + id + " not found."));

        application.setStatus(Status.valueOf(status.toUpperCase()));

        return applicationRepo.save(application);
    }

    private String addSubstringBetweenLettersInString(String string, String subString) {
        int stringLength = string.length();

        String newString = string.chars()
                .limit(stringLength - 1)
                .mapToObj(c -> (char) c + subString)
                .collect(Collectors.joining());

        return newString.concat(String.valueOf(string.charAt(stringLength - 1)));
    }
}

package com.abramova.applications.monitoring.controllers;

import com.abramova.applications.monitoring.entities.Application;
import com.abramova.applications.monitoring.services.OperatorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/operator")
public class OperatorController {
    @Autowired
    private OperatorService operatorService;

    @GetMapping("/apps")
    public Page<Application> getAllApplications(@PageableDefault(sort = {"id"}, direction = Sort.Direction.DESC) Pageable pageable) {
        return operatorService.findAllApplications(pageable);
    }

    @GetMapping("/apps/{appId}")
    public Application getApplicationById(@PathVariable("appId") long id) {
        return operatorService.findApplicationById(id);
    }

    @PutMapping("/apps/{appId}")
    public Application updateApplicationStatus(@PathVariable("appId") long id, @RequestBody String status) {
        return operatorService.updateApplicationStatus(id, status);
    }
}

package com.abramova.applications.monitoring.exceptions;

import javax.persistence.EntityNotFoundException;

public class ApplicationNotFoundException extends EntityNotFoundException {
    public ApplicationNotFoundException(String message) {
        super(message);
    }
}

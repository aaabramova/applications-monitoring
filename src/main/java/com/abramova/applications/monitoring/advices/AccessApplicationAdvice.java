package com.abramova.applications.monitoring.advices;

import com.abramova.applications.monitoring.exceptions.AccessApplicationException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class AccessApplicationAdvice {

    @ResponseBody
    @ExceptionHandler(AccessApplicationException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public String accessApplicationHandler(AccessApplicationException exception) {
        return exception.getMessage();
    }
}

package com.abramova.applications.monitoring.advices;

import com.abramova.applications.monitoring.exceptions.ApplicationNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class ApplicationNotFoundAdvice {

    @ResponseBody
    @ExceptionHandler(ApplicationNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String applicationNotFoundHandler(ApplicationNotFoundException exception) {
        return exception.getMessage();
    }
}

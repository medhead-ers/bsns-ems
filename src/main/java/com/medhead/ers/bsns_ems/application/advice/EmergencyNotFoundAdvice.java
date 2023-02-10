package com.medhead.ers.bsns_ems.application.advice;

import com.medhead.ers.bsns_ems.domain.exception.EmergencyNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class EmergencyNotFoundAdvice {
    @ResponseBody
    @ExceptionHandler(EmergencyNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    String emergencyNotFoundAdviceHandler(EmergencyNotFoundException exception) {
        return exception.getMessage();
    }
}

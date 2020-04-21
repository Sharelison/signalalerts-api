package com.github.sharelison.signalalertsapi.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.io.IOException;

@Slf4j
@Order(Ordered.HIGHEST_PRECEDENCE)
@RestControllerAdvice
public class SignalControllerAdvice {

    @ExceptionHandler(SignalException.class)
    public String onSignalException(SignalException exception) {
       log.error("Error happened trying to execute signal process, {}", exception.getMessage(), exception);
       return "Could not execute signal operation :" + exception.getMessage();
    }

    @ExceptionHandler({InterruptedException.class, IOException.class})
    public String onException(Exception exception){
        log.error("Error occured, {}", exception.getMessage(), exception);
        return "Error occured during operation";
    }
}

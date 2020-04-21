package com.github.sharelison.signalalertsapi.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class DefaultControllerAdvice {

    @ExceptionHandler({RuntimeException.class})
    public String onRuntimeException(RuntimeException runtimeException) {
        log.error("Unexpected error occured, {}", runtimeException.getMessage(), runtimeException);
        return "Unexpected error";
    }
}

package com.example.demo.advice;

import com.example.demo.exceptions.EmployeeNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;


@RestControllerAdvice
public class GlobalExceptionHandler {



    private final String message = "Error message: ";

    @ExceptionHandler(EmployeeNotFoundException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Map<String,String> handleEmployeeNotFoundException(EmployeeNotFoundException exception) {
        Map<String, String> errorMap = new HashMap<>();
        errorMap.put(message, exception.getMessage());
        return errorMap;
    }
}

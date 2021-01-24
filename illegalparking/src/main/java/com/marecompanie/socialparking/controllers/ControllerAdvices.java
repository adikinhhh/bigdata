package com.marecompanie.socialparking.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ControllerAdvices {
    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<?> entityNotFound() {
        return ResponseEntity.notFound().build();
    }
}

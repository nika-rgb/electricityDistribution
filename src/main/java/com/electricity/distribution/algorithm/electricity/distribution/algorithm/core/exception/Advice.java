package com.electricity.distribution.algorithm.electricity.distribution.algorithm.core.exception;

import com.electricity.distribution.algorithm.electricity.distribution.algorithm.core.exception.model.ExceptionModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.server.ResponseStatusException;

import javax.servlet.http.HttpServletRequest;

@ControllerAdvice
public class Advice {

    @ExceptionHandler(ResponseStatusException.class)
    public ResponseEntity<ExceptionModel> handleResponseStatusException(ResponseStatusException exception, HttpServletRequest request, WebRequest webRequest) {
        ExceptionModel model = new ExceptionModel(exception.getStatus(), exception.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(model);
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResponseEntity<ExceptionModel> handleHttpRequestMethodNotSupportedException(HttpRequestMethodNotSupportedException exception) {
        ExceptionModel model = new ExceptionModel(HttpStatus.METHOD_NOT_ALLOWED, "Please check method type");
        return ResponseEntity.status(HttpStatus.METHOD_NOT_ALLOWED).body(model);
    }

}

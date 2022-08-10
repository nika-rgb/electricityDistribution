package com.electricity.distribution.algorithm.electricity.distribution.algorithm.core.exception.model;

import org.springframework.http.HttpStatus;


public record ExceptionModel(HttpStatus status, String message) {
}

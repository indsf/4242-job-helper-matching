package com.test.common.exception;

import org.springframework.http.HttpStatus;

public interface ErrorStatus {

    HttpStatus HttpStatus();

    String getMessage();
    String getCode();
}

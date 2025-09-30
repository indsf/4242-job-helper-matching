package com.test.common.exception;

public class BussinessException extends RuntimeException {
    private final ErrorStatus errorCode;

    public BussinessException(ErrorStatus errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }

    public ErrorStatus getErrorCode() {
        return errorCode;
    }
}

package com.pfl.lib_common.base;

public class ErrorBean {

    public int errorType;
    public String message;

    public ErrorBean() {
    }

    public ErrorBean(@ErrorType.ErrrorType int errorType, String message) {
        this.errorType = errorType;
        this.message = message;
    }

    public int getErrorType() {
        return errorType;
    }

    public void setErrorType(@ErrorType.ErrrorType int errorType) {
        this.errorType = errorType;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}

package com.fabrizziochavez.apropo.model;

public class ErrorModel {
    private final String Message;
    private final String ExceptionMessage;

    public ErrorModel(String message, String exceptionMessage) {
        Message = message;
        ExceptionMessage = exceptionMessage;
    }

    public String getMessage() {
        return Message;
    }

    public String getExceptionMessage() {
        return ExceptionMessage;
    }

}


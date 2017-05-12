package com.fabrizziochavez.apropo.services;

public class RestErrorObject {
    private final String Message;
    private final String ExceptionMessage;

    public RestErrorObject(String message, String exceptionMessage) {
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


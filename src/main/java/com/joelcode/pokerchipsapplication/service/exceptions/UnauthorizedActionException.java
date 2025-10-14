package com.joelcode.pokerchipsapplication.service.exceptions;

public class UnauthorizedActionException extends RuntimeException {
    public UnauthorizedActionException() { super(); }
    public UnauthorizedActionException(String message) { super(message); }
    public UnauthorizedActionException(String message, Throwable cause) { super(message, cause); }
}

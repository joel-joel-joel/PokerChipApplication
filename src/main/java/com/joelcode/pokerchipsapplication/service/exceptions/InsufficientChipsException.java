package com.joelcode.pokerchipsapplication.service.exceptions;

public class InsufficientChipsException extends RuntimeException {
    public InsufficientChipsException() { super(); }
    public InsufficientChipsException(String message) { super(message); }
    public InsufficientChipsException(String message, Throwable cause) { super(message, cause); }
}

package com.joelcode.pokerchipsapplication.service.exceptions;

public class InvalidGameStateException extends RuntimeException {
    public InvalidGameStateException() { super(); }
    public InvalidGameStateException(String message) { super(message); }
    public InvalidGameStateException(String message, Throwable cause) { super(message, cause); }
}

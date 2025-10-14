package com.joelcode.pokerchipsapplication.service.exceptions;

public class InvalidTransferException extends RuntimeException {
    public InvalidTransferException() { super(); }
    public InvalidTransferException(String message) { super(message); }
    public InvalidTransferException(String message, Throwable cause) { super(message, cause); }
}

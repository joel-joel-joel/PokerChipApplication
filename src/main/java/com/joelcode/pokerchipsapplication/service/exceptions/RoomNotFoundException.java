package com.joelcode.pokerchipsapplication.service.exceptions;

public class RoomNotFoundException extends RuntimeException {
    public RoomNotFoundException() { super(); }
    public RoomNotFoundException(String message) { super(message); }
    public RoomNotFoundException(String message, Throwable cause) { super(message, cause); }
}

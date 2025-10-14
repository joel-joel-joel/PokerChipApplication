package com.joelcode.pokerchipsapplication.service.exceptions;

public class RoomFullException extends RuntimeException {
    public RoomFullException() { super(); }
    public RoomFullException(String message) { super(message); }
    public RoomFullException(String message, Throwable cause) { super(message, cause); }
}

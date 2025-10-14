package com.joelcode.pokerchipsapplication.service.exceptions;

public class PlayerNotInRoomException extends RuntimeException {
    public PlayerNotInRoomException() { super(); }
    public PlayerNotInRoomException(String message) { super(message); }
    public PlayerNotInRoomException(String message, Throwable cause) { super(message, cause); }
}

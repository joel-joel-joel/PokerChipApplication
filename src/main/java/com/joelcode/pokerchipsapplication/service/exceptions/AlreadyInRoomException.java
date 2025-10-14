package com.joelcode.pokerchipsapplication.service.exceptions;

public class AlreadyInRoomException extends RuntimeException {
    public AlreadyInRoomException() {
        super();
    }
    public AlreadyInRoomException(String message) {
        super(message);
    }
    public AlreadyInRoomException(String message, Throwable cause) {
        super(message, cause);
    }
}

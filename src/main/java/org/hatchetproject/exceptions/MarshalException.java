package org.hatchetproject.exceptions;

public class MarshalException extends Exception {
    public MarshalException(String message, Throwable cause) {
        super(message, cause);
    }

    public MarshalException(String message) {
        super(message);
    }
}

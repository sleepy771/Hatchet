package org.hatchetproject.exceptions;

public class HatchetSignatureException extends Exception {
    public HatchetSignatureException(String message, Throwable cause) {
        super(message, cause);
    }

    public HatchetSignatureException(String message) {
        super(message);
    }
}

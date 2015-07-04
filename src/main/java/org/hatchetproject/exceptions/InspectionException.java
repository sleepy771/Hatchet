package org.hatchetproject.exceptions;

public class InspectionException extends Exception {
    public InspectionException(String message, Throwable cause) {
        super(message, cause);
    }
    public InspectionException(String message) {
        super(message);
    }
}

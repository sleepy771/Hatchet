package org.hatchetproject.exceptions;

/**
 * Created by filip on 3.8.2015.
 */
public class InvocationException extends Exception {
    public InvocationException(String message, Throwable cause) {
        super(message, cause);
    }

    public InvocationException(String message) {
        super(message);
    }
}

package org.hatchetproject.exceptions;

/**
 * Created by filip on 6/27/15.
 */
public class ManagerException extends Exception {

    public ManagerException(String message) {
        super(message);
    }

    public ManagerException(String message, Throwable cause) {
        super(message, cause);
    }
}

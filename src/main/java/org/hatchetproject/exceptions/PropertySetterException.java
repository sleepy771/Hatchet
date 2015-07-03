package org.hatchetproject.exceptions;

/**
 * Created by filip on 7/2/15.
 */
public class PropertySetterException extends Exception {

    public PropertySetterException(String message) {
        super(message);
    }

    public PropertySetterException(String message, Throwable cause) {
        super(message, cause);
    }
}

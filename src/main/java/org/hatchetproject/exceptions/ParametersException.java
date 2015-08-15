package org.hatchetproject.exceptions;

public class ParametersException extends Exception {
    public ParametersException(String message) {
        super(message);
    }

    public ParametersException(String s, Throwable e) {
        super(s, e);
    }
}

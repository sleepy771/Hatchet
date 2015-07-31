package org.hatchetproject.exceptions;

public class ParserException extends Exception {

    public ParserException(String message, Throwable cause) {
        super(message, cause);
    }

    public ParserException(String message) {
        this(message, null);
    }
}

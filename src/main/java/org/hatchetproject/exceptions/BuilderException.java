package org.hatchetproject.exceptions;

public class BuilderException extends Exception {
    public BuilderException(String s) {
        super(s);
    }

    public BuilderException(String s, Throwable cause) {
        super(s, cause);
    }
}

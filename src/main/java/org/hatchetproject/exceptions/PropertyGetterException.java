package org.hatchetproject.exceptions;

public class PropertyGetterException extends Exception {

    public PropertyGetterException(String s, Throwable e) {
        super(s, e);
    }

    public PropertyGetterException(String s) {
        super(s);
    }
}

package org.hatchetproject.exceptions;

/**
 * Created by filip on 7/2/15.
 */
public class PropertyGetterException extends Throwable {

    public PropertyGetterException(String s, Throwable e) {
        super(s, e);
    }

    public PropertyGetterException(String s) {
        super(s);
    }
}

package org.hatchetproject.exceptions;

/**
 * Created by filip on 7/3/15.
 */
public class HatchetSignatureException extends Throwable {
    public HatchetSignatureException(String message, NoSuchMethodException cause) {
        super(message, cause);
    }

    public HatchetSignatureException(String message) {
        super(message);
    }
}

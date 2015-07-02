package org.hatchetproject.annotations.valuecast;

import org.hatchetproject.reflection.constants.AsSelf;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by filip on 7/2/15.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface FromClass {
    Class value() default AsSelf.class;
}

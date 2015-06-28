package org.hatchetproject.annotations;

import org.hatchetproject.di.InjectionType;
import org.hatchetproject.reflection.constants.AsSelf;
import org.hatchetproject.reflection.constants.PreviousValue;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by filip on 6/27/15.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.CONSTRUCTOR, ElementType.METHOD, ElementType.FIELD})
public @interface InjectDefault {
    String filePath() default "-";
    String uid() default "-";
    Class type() default AsSelf.class;
    int index() default -1;
    InjectionType injectionType() default InjectionType.EMPTY;
}
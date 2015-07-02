package org.hatchetproject.annotations;

import org.hatchetproject.reflection.constants.AsSelf;
import org.hatchetproject.value_management.DoNothing;
import org.hatchetproject.value_management.UndefinedValueCast;
import org.hatchetproject.value_management.ValueCast;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD, ElementType.FIELD, ElementType.CONSTRUCTOR})
public @interface Property {
    String name() default "-";

    Class type() default AsSelf.class;

    Class<? extends ValueCast> caster() default UndefinedValueCast.class;

    int index() default -1;
}

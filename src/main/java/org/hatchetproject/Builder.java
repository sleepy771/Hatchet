package org.hatchetproject;

import org.hatchetproject.exceptions.BuilderException;
import org.hatchetproject.exceptions.PropertyGetterException;

public interface Builder<Type> {
    Type build() throws BuilderException;
}

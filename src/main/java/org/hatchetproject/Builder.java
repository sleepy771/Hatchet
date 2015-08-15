package org.hatchetproject;

import org.hatchetproject.exceptions.BuilderException;

public interface Builder<Type> {
    Type build() throws BuilderException;
}

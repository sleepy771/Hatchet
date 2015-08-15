package org.hatchetproject.reflection.accessors.property.helpers;

import org.hatchetproject.exceptions.HelperException;

public interface PropertyHelper<INPUT, OUTPUT> {
    OUTPUT push(INPUT input);
}

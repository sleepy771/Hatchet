package org.hatchetproject.reflection.accessors.property.helpers;

public interface PropertyHelper<INPUT, OUTPUT> {
    OUTPUT push(INPUT input);
}

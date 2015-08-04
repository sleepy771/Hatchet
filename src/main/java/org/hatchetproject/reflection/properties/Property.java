package org.hatchetproject.reflection.properties;

import org.hatchetproject.reflection.meta.signatures.PropertyMeta;
import org.hatchetproject.reflection.meta.signatures.Signature;

public interface Property extends Signature {
    Object getValue();

    PropertyMeta getMeta();
}

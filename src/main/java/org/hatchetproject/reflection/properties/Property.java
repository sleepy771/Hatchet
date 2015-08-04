package org.hatchetproject.reflection.properties;

import org.hatchetproject.reflection.meta.signatures.PropertyMeta;
import org.hatchetproject.reflection.meta.signatures.Signature;

/**
 * Created by filip on 4.8.2015.
 */
public interface Property extends Signature {
    Object getValue();

    PropertyMeta getMeta();
}

package org.hatchetproject.reflection.meta.signatures;

/**
 * Created by filip on 2.8.2015.
 */
public interface AccessorMeta extends Signature {

    enum MetaType {
        FIELD("Field"), METHOD("Method"), CONSTRUCTOR("Constructor");

        private final String name;

        MetaType(String name) {
            this.name = name;
        }

        String getName() {
            return this.name;
        }
    }

    int getParameterCount();

    Class[] getParameterTypes();

    Class getParameterType(int k);

    MetaType getAccessorMetaType();
}

package org.hatchetproject.reflection.accessors;

import org.hatchetproject.reflection.meta.signatures.AccessorMeta;

import java.util.List;

public interface AccessorExecutor extends Invokable {
    AccessorMeta getSignature();

    void setParameter(int index, Object value);

    void setAllParameters(Object[] values);

    void setAllParameters(List<Object> values);

    void add(Object value);

    void addAllParameters(Object[] values);

    void addAllParameters(List<Object> values);
}

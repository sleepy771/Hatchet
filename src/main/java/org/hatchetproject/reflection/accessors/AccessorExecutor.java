package org.hatchetproject.reflection.accessors;

import org.hatchetproject.reflection.meta.signatures.AccessorMeta;
import org.hatchetproject.value_management.inject_default.ParametersBuilder;

import java.util.List;

public interface AccessorExecutor extends Invokable {

    ParametersBuilder createBuilder();

    ParametersBuilder getBuilder();

    void setBuilder(ParametersBuilder builder);

    void newParametersBuilder();

    AccessorMeta getSignature();

    void setParameter(int index, Object value);

    void setAllParameters(Object[] values);

    void setAllParameters(List<Object> values);

    void add(Object value);

    void addAllParameters(Object[] values);

    void addAllParameters(List<Object> values);
}

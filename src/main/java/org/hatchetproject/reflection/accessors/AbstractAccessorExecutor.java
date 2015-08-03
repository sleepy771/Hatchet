package org.hatchetproject.reflection.accessors;

import org.hatchetproject.exceptions.InvocationException;
import org.hatchetproject.exceptions.ParametersException;
import org.hatchetproject.value_management.inject_default.AssignedParameters;
import org.hatchetproject.value_management.inject_default.AssignedParameters.Type;
import org.hatchetproject.value_management.inject_default.ParametersBuilder;

import java.util.List;

public abstract class AbstractAccessorExecutor implements AccessorExecutor {

    private ParametersBuilder builder;

    @Override
    public void setParameter(int index, Object value) {
        builder.set(index, value);
    }

    @Override
    public void setAllParameters(Object[] values) {
        builder.setAll(values);
    }

    @Override
    public void setAllParameters(List<Object> values) {
        builder.setAll(values);
    }

    @Override
    public void add(Object value) {
        builder.add(value);
    }

    @Override
    public void addAllParameters(Object[] values) {
        builder.addAll(values);
    }

    @Override
    public void addAllParameters(List<Object> values) {
        builder.addAll(values);
    }

    @Override
    public boolean canInvoke() {
        return builder.isFilled();
    }

    @Override
    public Object invoke(Object destination) throws InvocationException {
        try {
            AssignedParameters parameters = getParameters();
            return this.invoke(destination, parameters.asArray());
        } catch (ParametersException e) {
            throw new InvocationException("Parameters are not assigned");
        }
    }

    protected AssignedParameters getParameters() throws ParametersException {
        return builder.build();
    }

    protected abstract boolean isValidBuilder(ParametersBuilder builder);

    @Override
    public void setBuilder(ParametersBuilder builder) {
        if (!isValidBuilder(builder)) {
            throw new RuntimeException("Parameters builder can not be assigned");
        }
        this.builder = builder;
    }

    @Override
    public ParametersBuilder getBuilder() {
        return this.builder;
    }

    @Override
    public void newParametersBuilder() {
        this.builder = createBuilder();
    }
}

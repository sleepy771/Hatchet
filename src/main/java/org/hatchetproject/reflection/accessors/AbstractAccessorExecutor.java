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
    public final void setParameter(int index, Object value) {
        builder.set(index, value);
        checkAssigned();
    }

    @Override
    public final void setAllParameters(Object[] values) {
        builder.setAll(values);
        checkAssigned();
    }

    @Override
    public final void setAllParameters(List<Object> values) {
        builder.setAll(values);
        checkAssigned();
    }

    @Override
    public final void add(Object value) {
        builder.add(value);
        checkAssigned();
    }

    @Override
    public final void addAllParameters(Object[] values) {
        builder.addAll(values);
        checkAssigned();
    }

    @Override
    public final void addAllParameters(List<Object> values) {
        builder.addAll(values);
        checkAssigned();
    }

    @Override
    public final boolean isFilled() {
        return builder.isFilled();
    }

    @Override
    public final Object invoke(Object destination) throws InvocationException {
        try {
            AssignedParameters parameters = getParameters();
            return this.invoke(destination, parameters.asArray());
        } catch (ParametersException e) {
            throw new InvocationException("Parameters are not assigned");
        }
    }

    protected final AssignedParameters getParameters() throws ParametersException {
        return builder.build();
    }

    protected abstract boolean isValidBuilder(ParametersBuilder builder);

    protected final void setBuilder(ParametersBuilder builder) {
        if (!isValidBuilder(builder)) {
            throw new RuntimeException("Parameters builder can not be assigned");
        }
        this.builder = builder;
    }

    protected final ParametersBuilder getBuilder() {
        return this.builder;
    }

    protected final void newParametersBuilder() {
        this.builder = createBuilder();
    }

    protected abstract ParametersBuilder createBuilder();

    protected abstract void update();

    protected abstract boolean isReady();

    protected final void checkAssigned() {
        if (isReady()) {
            update();
        }
    }
}

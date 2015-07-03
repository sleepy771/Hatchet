package org.hatchetproject.reflection;

import org.hatchetproject.exceptions.PropertyAccessorException;
import org.hatchetproject.value_management.ValueCast;


public abstract class PropertyAccessorBase implements PropertyAccessor {
    private ValueCast caster;
    private AccessorType type;
    private Boolean needsCaster;

    protected PropertyAccessorBase(ValueCast caster, AccessorType type) throws PropertyAccessorException {
        setCaster(caster);
        this.type = type;
    }

    @Override
    public final ValueCast getCaster() {
        return caster;
    }

    @Override
    public final boolean hasCaster() {
        return caster != null;
    }

    @Override
    @SuppressWarnings("unchecked")
    public final boolean needsCaster() {
        if (needsCaster == null) {
            switch (this.type) {
                case GETTER:
                    needsCaster = getProperty().getPropertyType().isAssignableFrom(getValueClass());
                    break;
                case SETTER:
                    needsCaster = getValueClass().isAssignableFrom(getProperty().getPropertyType());
                    break;
            }
        }
        return needsCaster;
    }

    @SuppressWarnings("unchecked")
    protected final Object cast(Object value) throws PropertyAccessorException {
        validateCaster();
        validateObjectType(value);
        if (hasCaster()) {
            value = getCaster().cast(value);
        }
        return value;
    }

    protected final void validateObjectType(Object value) throws PropertyAccessorException {
        if (hasCaster() && !getCaster().getInputType().isInstance(value)) {
            throw new PropertyAccessorException();
        }
    }

    protected final void validateCaster() throws PropertyAccessorException {
        if (needsCaster() && !hasCaster())
            throw new PropertyAccessorException();
    }

    @SuppressWarnings("unchecked")
    protected final void setCaster(ValueCast caster) throws PropertyAccessorException {
        if (caster == null) {
            if (!needsCaster()) {
                this.caster = null;
                return;
            }
            throw new PropertyAccessorException();
        }
        switch (this.type) {
            case GETTER:
                if (caster.getInputType().isAssignableFrom(getValueClass())
                        && getProperty().getPropertyType().isAssignableFrom(caster.getOutputType())) {
                    this.caster = caster;
                    return;
                }
            case SETTER:
                if (caster.getInputType().isAssignableFrom(getProperty().getPropertyType())
                        && getValueClass().isAssignableFrom(caster.getOutputType())) {
                    this.caster = caster;
                    return;
                }
            default:
                throw new PropertyAccessorException();
        }
    }

    enum AccessorType {
        SETTER, GETTER;
    }
}

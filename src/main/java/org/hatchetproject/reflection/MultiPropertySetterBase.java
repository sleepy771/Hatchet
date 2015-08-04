package org.hatchetproject.reflection;

import org.hatchetproject.exceptions.PropertySetterException;
import org.hatchetproject.reflection.meta.signatures.Signature;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by filip on 7/3/15.
 */
public abstract class MultiPropertySetterBase {

    private MultiPropertySetter[] setters;
    private int size;

    protected MultiPropertySetterBase(int size) {
        this.size = size;
        this.setters = new MultiPropertySetter[size];
    }

    @SuppressWarnings("unchecked")
    protected final void addSetter(int idx, MultiPropertySetter setter) throws PropertySetterException {
        if (!getParameterType(idx).isAssignableFrom(setter.getValueClass())) {
            throw new PropertySetterException("Can not assign setter, incompatible types");
        }
        this.setters[idx] = setter;
    }

    protected final MultiPropertySetter removeSetter(int idx) {
        MultiPropertySetter tmp = this.setters[idx];
        this.setters[idx] = null;
        return tmp;
    }

    protected abstract Class getParameterType(int idx);

    protected final Object[] createListOfArgs(Map<Signature, Object> values) throws PropertySetterException {
        List<Object> args = new ArrayList<>(size);
        for (MultiPropertySetter setter : setters) {
            setter.set(args, values.get(setter.getProperty().getSignature()));
        }
        return args.toArray();
    }

    public abstract Object setAll(Object destination, Map<Signature, Object> values) throws PropertySetterException;
}

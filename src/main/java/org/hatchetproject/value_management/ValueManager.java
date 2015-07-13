package org.hatchetproject.value_management;

import org.hatchetproject.exceptions.ManagerException;
import org.hatchetproject.manager.AbstractManager;
import org.hatchetproject.value_management.RegistrableValue.ValueSignature;

/**
 * Created by filip on 7/13/15.
 */
public class ValueManager extends AbstractManager<RegistrableValue.ValueSignature, RegistrableValue> {

    private static ValueManager INSTANCE;

    private ValueManager() {
    }

    @Override
    protected ValueSignature getKeyForElement(RegistrableValue registrableValue) {
        return registrableValue.getSignature();
    }

    @Override
    protected boolean postRegister(ValueSignature valueSignature, RegistrableValue registrableValue) {
        return true;
    }

    @Override
    protected void postUnregister(ValueSignature valueSignature, RegistrableValue registrableValue) {
    }

    @Override
    protected String verboseKey(ValueSignature valueSignature) {
        return "[" + valueSignature.getName() + " : " + valueSignature.getType().getName() + "]";
    }

    @Override
    protected String verboseElement(RegistrableValue registrableValue) {
        return verboseKey(registrableValue.getSignature()) + " := " + registrableValue.getValue().toString();
    }

    @Override
    public boolean isRegistered(RegistrableValue registrableValue) {
        return contains(registrableValue.getSignature(), registrableValue);
    }

    @Override
    public boolean isRegistered(ValueSignature valueSignature, RegistrableValue registrableValue) {
        return contains(valueSignature, registrableValue);
    }

    @Override
    public boolean isKeyRegistered(ValueSignature valueSignature) {
        return containsKey(valueSignature);
    }

    @Override
    public RegistrableValue get(ValueSignature valueSignature) throws ManagerException {
        if (!isKeyRegistered(valueSignature))
            throw new ManagerException("Value is not registered for key");
        return getDirectly(valueSignature);
    }

    public Object getValueUndefined(ValueSignature valueSignature) throws ManagerException {
        return get(valueSignature).getValue();
    }

    public <T> T getValueAs(Class<T> type, ValueSignature signature) throws ManagerException {
        return type.cast(get(signature).getValue());
    }

    @SuppressWarnings("unchecked")
    public <T> T getValueUnchecked(ValueSignature signature) throws ManagerException {
        return (T) get(signature).getValue();
    }

    public static ValueManager getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new ValueManager();
        }
        return INSTANCE;
    }
}

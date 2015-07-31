package org.hatchetproject.settings;

import org.hatchetproject.exceptions.ManagerException;
import org.hatchetproject.value_management.ParsersManager;

import javax.xml.bind.annotation.XmlType;

@XmlType(name = "value")
public class MarshallableObject {

    private Object value;

    public Object get() {
        return value;
    }

    public void set(Object value) {
        this.value = value;
    }

    // TODO throws MarshalableObjectException
    public void setValue(String value) throws ManagerException {
        String[] classAndValue = value.trim().split(":");
        this.value = ParsersManager.getInstance().get(classAndValue[0]).parse(classAndValue[1]);
    }

    @SuppressWarnings("unchecked")
    public String getValue() throws ManagerException {
        return value.getClass().getName() + ":" + ParsersManager.getInstance().get(value.getClass().getName()).toString(value);
    }
}

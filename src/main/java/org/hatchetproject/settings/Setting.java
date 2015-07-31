package org.hatchetproject.settings;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import java.io.Serializable;

@XmlType (name = "setting", propOrder = {"name", "type", "value"})
public class Setting implements SettingGetter {

    private String name;

    private Class type;

    private Serializable value;

    public Setting() {
        this(null, null, null);
    }

    public Setting(String name, Class type, Serializable value) {
        this.name = name;
        this.type = type;
        this.value = value;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public Serializable getValue() {
        return value;
    }

    @Override
    public Class getType() {
        return type;
    }

    public void setType(Class type) {
        this.type = type;
    }

    public void setValue(Serializable value) {
        this.value = value;
    }

    public void setName(String name) {
        this.name = name;

    }

    @Override
    public String toString() {
        return "Setting{" +
                "name='" + name + '\'' +
                ", type=" + type +
                ", value=" + value +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (null == o || getClass() != o.getClass()) {
            return false;
        }

        Setting setting = (Setting) o;

        return name.equals(setting.name) && type.equals(setting.type) && value.equals(setting.value);
    }

    @Override
    public int hashCode() {
        int result = name.hashCode();
        result = 31 * result + type.hashCode();
        result = 31 * result + value.hashCode();
        return result;
    }
}

package org.hatchetproject.settings;

/**
 * Created by filip on 7/31/15.
 */
public class SettingProxy implements SettingGetter {

    private SettingGetter getter;

    SettingProxy(SettingGetter settingGetter) {
        this.getter = settingGetter;
    }

    @Override
    public String getName() {
        return getter.getName();
    }

    @Override
    public Class getType() {
        return getter.getType();
    }

    @Override
    public Object getValue() {
        return getter.getValue();
    }
}

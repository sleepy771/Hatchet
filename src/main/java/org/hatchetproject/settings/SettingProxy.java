package org.hatchetproject.settings;

/**
 * Created by filip on 7/31/15.
 */
public class SettingProxy implements SettingGetter {

    private SettingGetter getter;

    SettingProxy(SettingGetter settingGetter) {
        if (settingGetter instanceof SettingProxy) {
            Setting setting = new Setting();
            setting.setType(settingGetter.getType());
            setting.setName(settingGetter.getName());
            setting.setValue(settingGetter.getValue());
            this.getter = setting;
        }
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

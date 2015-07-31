package org.hatchetproject.settings;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class SettingsImpl implements Settings {

    private final Map<String, SettingGetter> getterMap;

    public SettingsImpl(final Iterable<SettingGetter> list) {
        this.getterMap = new HashMap<>();
        for (SettingGetter getter : list) {
            this.getterMap.put(getter.getName(), getter);
        }
    }

    @Override
    public Object getValue(final String settingName) {
        containsName(settingName);
        return getterMap.get(settingName).getValue();
    }

    @Override
    public <T> T getValue(final String settingName, final Class<T> type) {
        return type.cast(getValue(settingName));
    }

    @Override
    public Class getType(final String settingName) {
        containsName(settingName);
        return getterMap.get(settingName).getType();
    }

    @Override
    public SettingGetter getSetting(String setting) {
        containsName(setting);
        return getterMap.get(setting);
    }

    @Override
    public Map<String, Object> getValues(Iterable<String> settingNames) {
        Map<String, Object> values = new HashMap<>();
        for (String settingName : settingNames) {
            values.put(settingName, getValue(settingName));
        }
        return values;
    }

    @Override
    public Map<String, Class> getTypes(Iterable<String> settingNames) {
        Map<String, Class> types = new HashMap<>();
        for (String settingName : settingNames) {
            types.put(settingName, getType(settingName));
        }
        return types;
    }

    @Override
    public Map<String, SettingGetter> getSettings(Iterable<String> settingNames) {
        Map<String, SettingGetter> getterMap = new HashMap<>();
        for (String settingName : settingNames) {
            getterMap.put(settingName, getSetting(settingName));
        }
        return getterMap;
    }

    @Override
    public boolean addSetting(SettingGetter settingGetter) {
        if (getterMap.containsKey(settingGetter.getName())) {
            return false;
        }
        getterMap.put(settingGetter.getName(), new SettingProxy(settingGetter));
        return true;
    }

    @Override
    public boolean addSetting(String name, Class type, Serializable value) {
        return addSetting(new Setting(name, type, value));
    }

    @Override
    public SettingGetter setSetting(SettingGetter setting) {
        return getterMap.put(setting.getName(), new SettingProxy(setting));
    }

    @Override
    public SettingGetter setSetting(String name, Class type, Serializable value) {
        return setSetting(new Setting(name, type, value));
    }

    @Override
    public boolean addAllSettings(Iterable<SettingGetter> settings) {
        boolean wasAppend = true;
        for (SettingGetter setting : settings) {
            wasAppend &= addSetting(setting);
        }
        return wasAppend;
    }

    @Override
    public Map<String, SettingGetter> setAllSettings(Iterable<SettingGetter> settings) {
        Map<String, SettingGetter> oldSettings = new HashMap<>();
        for (SettingGetter setting : settings) {
            SettingGetter oldSetting = setSetting(setting);
            if (null != oldSetting) {
                oldSettings.put(oldSetting.getName(), oldSetting);
            }
        }
        return oldSettings;
    }

    @Override
    public void clear() {
        getterMap.clear();
    }

    @Override
    public SettingGetter removeSettingByName(String name) {
        return getterMap.remove(name);
    }

    @Override
    public Map<String, SettingGetter> removeAllSettingsByName(Iterable<String> settingNames) {
        Map<String, SettingGetter> getterMap = new HashMap<>();
        for (String settingName : settingNames) {
            SettingGetter oldSetting = removeSettingByName(settingName);
            if (null != oldSetting) {
                getterMap.put(oldSetting.getName(), oldSetting);
            }
        }
        return getterMap;
    }

    @Override
    public boolean removeSetting(SettingGetter setting) {
        return getterMap.remove(setting.getName(), setting);
    }

    @Override
    public boolean removeAllSettings(Iterable<SettingGetter> settings) {
        boolean wasRemovedAll = true;
        for (SettingGetter settingGetter : settings) {
            wasRemovedAll &= removeSetting(settingGetter);
        }
        return wasRemovedAll;
    }

    @Override
    public boolean containsSetting(String name, Class type) {
        SettingGetter getter = getterMap.get(name);
        return null != getter && getter.getType() == type;
    }

    @Override
    public boolean containsSetting(String name) {
        return getterMap.containsKey(name);
    }

    @Override
    public int size() {
        return getterMap.size();
    }

    @Override
    public boolean isEmpty() {
        return getterMap.isEmpty();
    }

    @Override
    public Map<String, SettingGetter> asMap() {
        return new HashMap<>(getterMap);
    }

    private void containsName(String name) {
        if (!getterMap.containsKey(name)) {
            throw new IllegalArgumentException("Setting is undefined");
        }
    }

    @Override
    public Iterator<SettingGetter> iterator() {
        return new Iterator<SettingGetter>() {

            private final Iterator<SettingGetter> iterator = getterMap.values().iterator();

            @Override
            public boolean hasNext() {
                return iterator.hasNext();
            }

            @Override
            public SettingGetter next() {
                return iterator.next();
            }
        };
    }
}

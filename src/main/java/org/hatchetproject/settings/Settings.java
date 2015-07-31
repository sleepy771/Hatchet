package org.hatchetproject.settings;

import org.hatchetproject.TypeValueEntry;

import java.util.Map;

public interface Settings {
    Object getValue(String settingName);

    <T> T getValue(String settingName, Class<T> type);

    Class getType(String settingName);

    SettingGetter getSetting(String setting);

    Map<String, Object> getValues(Iterable<String> settingNames);

    Map<String, Class> getTypes(Iterable<String> settingNames);

    Map<String, SettingGetter> getSettings(Iterable<String> settingNames);

    boolean addSetting(SettingGetter settingGetter);

    boolean addSetting(String name, Class type, Object value);

    SettingGetter setSetting(SettingGetter setting);

    SettingGetter setSetting(String name, Class type, Object value);

    boolean addAllSettings(Iterable<SettingGetter> settings);

    Map<String, SettingGetter> setAllSettings(Iterable<SettingGetter> settings);

    void clear();

    SettingGetter removeSettingByName(String name);

    Map<String, SettingGetter> removeAllSettingsByName(Iterable<String> settingNames);

    boolean removeSetting(SettingGetter settings);

    boolean removeAllSettings(Iterable<SettingGetter> settings);

    boolean containsSetting(String name, Class type);

    boolean containsSetting(String name);

    int size();

    boolean isEmpty();

    Map<String, SettingGetter> asMap();
}

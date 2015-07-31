package org.hatchetproject.settings;

import org.hatchetproject.TypeValueEntry;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;


public class XMLSettings implements Persistent, Settings {
    @Override
    public void load(InputStream inputStream) throws Exception {

    }

    @Override
    public OutputStream save() throws Exception {
        return null;
    }

    @Override
    public Object getValue(String settingName) {
        return null;
    }

    @Override
    public <T> T getValue(String settingName, Class<T> type) {
        return null;
    }

    @Override
    public Class getType(String settingName) {
        return null;
    }

    @Override
    public SettingGetter getSetting(String setting) {
        return null;
    }

    @Override
    public Map<String, Object> getValues(Iterable<String> settingNames) {
        return null;
    }

    @Override
    public Map<String, Class> getTypes(Iterable<String> settingNames) {
        return null;
    }

    @Override
    public Map<String, SettingGetter> getSettings(Iterable<String> settingNames) {
        return null;
    }

    @Override
    public boolean addSetting(SettingGetter settingGetter) {
        return false;
    }

    @Override
    public boolean addSetting(String name, Class type, Object value) {
        return false;
    }

    @Override
    public SettingGetter setSetting(SettingGetter setting) {
        return null;
    }

    @Override
    public SettingGetter setSetting(String name, Class type, Object value) {
        return null;
    }

    @Override
    public boolean addAllSettings(Iterable<SettingGetter> settings) {
        return false;
    }

    @Override
    public Map<String, SettingGetter> setAllSettings(Iterable<SettingGetter> settings) {
        return null;
    }

    @Override
    public void clear() {

    }

    @Override
    public SettingGetter removeSettingByName(String name) {
        return null;
    }

    @Override
    public Map<String, SettingGetter> removeAllSettingsByName(Iterable<String> settingNames) {
        return null;
    }

    @Override
    public boolean removeSetting(SettingGetter settings) {
        return false;
    }

    @Override
    public boolean removeAllSettings(Iterable<SettingGetter> settings) {
        return false;
    }

    @Override
    public boolean containsSetting(String name, Class type) {
        return false;
    }

    @Override
    public boolean containsSetting(String name) {
        return false;
    }

    @Override
    public int size() {
        return 0;
    }

    @Override
    public boolean isEmpty() {
        return false;
    }

    @Override
    public Map<String, SettingGetter> asMap() {
        return null;
    }
}

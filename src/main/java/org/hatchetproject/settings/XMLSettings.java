package org.hatchetproject.settings;

import org.hatchetproject.TypeValueEntry;

import javax.xml.bind.JAXBContext;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.util.Iterator;
import java.util.Map;

public class XMLSettings implements Persistent, Settings {

    enum LoadBehavior {
        OVERRIDE, MERGE, CLEAR;
    }

    private Settings settings;

    private LoadBehavior loadBehavior;

    private InputStream inputStream;

    private OutputStream outputStream;

    public LoadBehavior getLoadBehavior() {
        return loadBehavior;
    }

    public void setLoadBehavior(LoadBehavior loadBehavior) {
        this.loadBehavior = loadBehavior;
    }

    @Override
    public void load() throws Exception {
        JAXBContext context = JAXBContext.newInstance()
    }

    @Override
    public void setInputStream(InputStream stream) {

    }

    @Override
    public void setOutputStream(OutputStream stream) {

    }

    @Override
    public OutputStream getOutputStream() {
        return null;
    }

    @Override
    public InputStream getInputStream() {
        return null;
    }

    @Override
    public void save() throws Exception {

    }

    @Override
    public Object getValue(String settingName) {
        return settings.getValue(settingName);
    }

    @Override
    public <T> T getValue(String settingName, Class<T> type) {
        return settings.getValue(settingName, type);
    }

    @Override
    public Class getType(String settingName) {
        return settings.getType(settingName);
    }

    @Override
    public SettingGetter getSetting(String setting) {
        return settings.getSetting(setting);
    }

    @Override
    public Map<String, Object> getValues(Iterable<String> settingNames) {
        return settings.getValues(settingNames);
    }

    @Override
    public Map<String, Class> getTypes(Iterable<String> settingNames) {
        return settings.getTypes(settingNames);
    }

    @Override
    public Map<String, SettingGetter> getSettings(Iterable<String> settingNames) {
        return settings.getSettings(settingNames);
    }

    @Override
    public boolean addSetting(SettingGetter settingGetter) {
        return settings.addSetting(settingGetter);
    }

    @Override
    public boolean addSetting(String name, Class type, Serializable value) {
        return settings.addSetting(name, type, value);
    }

    @Override
    public SettingGetter setSetting(SettingGetter setting) {
        return settings.setSetting(setting);
    }

    @Override
    public SettingGetter setSetting(String name, Class type, Serializable value) {
        return settings.setSetting(name, type, value);
    }

    @Override
    public boolean addAllSettings(Iterable<SettingGetter> settings) {
        return this.settings.addAllSettings(settings);
    }

    @Override
    public Map<String, SettingGetter> setAllSettings(Iterable<SettingGetter> settings) {
        return this.settings.setAllSettings(settings);
    }

    @Override
    public void clear() {
        settings.clear();
    }

    @Override
    public SettingGetter removeSettingByName(String name) {
        return settings.removeSettingByName(name);
    }

    @Override
    public Map<String, SettingGetter> removeAllSettingsByName(Iterable<String> settingNames) {
        return settings.removeAllSettingsByName(settingNames);
    }

    @Override
    public boolean removeSetting(SettingGetter settings) {
        return this.settings.removeSetting(settings);
    }

    @Override
    public boolean removeAllSettings(Iterable<SettingGetter> settings) {
        return this.settings.removeAllSettings(settings);
    }

    @Override
    public boolean containsSetting(String name, Class type) {
        return settings.containsSetting(name, type);
    }

    @Override
    public boolean containsSetting(String name) {
        return settings.containsSetting(name);
    }

    @Override
    public int size() {
        return settings.size();
    }

    @Override
    public boolean isEmpty() {
        return settings.isEmpty();
    }

    @Override
    public Map<String, SettingGetter> asMap() {
        return settings.asMap();
    }

    @Override
    public Iterator<SettingGetter> iterator() {
        return settings.iterator();
    }
}

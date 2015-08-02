package org.hatchetproject.settings;

import org.hatchetproject.exceptions.SettingsException;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class XMLSettings implements Persistent, Settings {

    private Settings settings;

    private MergeBehavior mergeBehavior;

    private InputStream inputStream;

    private OutputStream outputStream;

    public MergeBehavior getMergeBehavior() {
        return mergeBehavior;
    }

    public void setMergeBehavior(MergeBehavior mergeBehavior) {
        this.mergeBehavior = mergeBehavior;
    }

    @Override
    public void load() throws Exception {
        if (null == inputStream) {
            throw new SettingsException();
        }
        JAXBContext settingsContext = JAXBContext.newInstance(SettingsList.class);
        Unmarshaller settingsUnmarshaller = settingsContext.createUnmarshaller();
        SettingsList settingsList = (SettingsList) settingsUnmarshaller.unmarshal(inputStream);
        getMergeBehavior().merge(this, settingsList);
        inputStream.close();
        inputStream = null;
    }

    @Override
    public void setInputStream(InputStream stream) {
        this.inputStream = stream;
    }

    @Override
    public void setOutputStream(OutputStream stream) {
        this.outputStream = stream;
    }

    @Override
    public OutputStream getOutputStream() {
        return outputStream;
    }

    @Override
    public InputStream getInputStream() {
        return inputStream;
    }

    @Override
    public void save() throws Exception {
        if (null != outputStream) {
            SettingsList list = new SettingsList();
            List<Setting> settingList = new ArrayList<>();
            for (SettingGetter getter : this) {
                settingList.add(new Setting(getter));
            }
            list.setSettings(settingList);
            JAXBContext settingsContext = JAXBContext.newInstance(SettingsList.class);
            Marshaller settingsMarshaller = settingsContext.createMarshaller();
            settingsMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
            settingsMarshaller.marshal(list, outputStream);
            outputStream.close();
            outputStream = null;
        }
        throw new SettingsException();
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

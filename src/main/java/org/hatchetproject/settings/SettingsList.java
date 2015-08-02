package org.hatchetproject.settings;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.util.Iterator;
import java.util.List;

@XmlRootElement(name = "settings")
class SettingsList implements Serializable, Iterable<SettingGetter> {

    private List<Setting> settings;

    @XmlElement(name = "setting")
    public void setSettings(List<Setting> settings) {
        this.settings = settings;
    }

    public List<Setting> getSettings() {
        return settings;
    }

    @Override
    public Iterator<SettingGetter> iterator() {
        return new Iterator<SettingGetter>() {
            private final Iterator<Setting> settingIterator = SettingsList.this.settings.iterator();
            @Override
            public boolean hasNext() {
                return settingIterator.hasNext();
            }

            @Override
            public SettingGetter next() {
                return new SettingProxy(settingIterator.next());
            }
        };
    }
}

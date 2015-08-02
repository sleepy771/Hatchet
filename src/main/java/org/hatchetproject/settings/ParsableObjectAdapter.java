package org.hatchetproject.settings;

import org.hatchetproject.value_management.ParsersManager;

import javax.xml.bind.annotation.adapters.XmlAdapter;

/**
 * Created by filip on 2.8.2015.
 */
public class ParsableObjectAdapter extends XmlAdapter<String, Object> {
    @Override
    public Object unmarshal(String v) throws Exception {
        String[] classAndValue = v.trim().split(":", 1);
        return ParsersManager.parse(classAndValue[0], classAndValue[1]);
    }

    @Override
    public String marshal(Object v) throws Exception {
        return v.getClass().getName() + ":" + ParsersManager.toString(v);
    }
}

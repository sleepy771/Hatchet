package org.hatchetproject.value_management;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;

@XmlType(name = "parsers")
public class HatchetParsersList implements Serializable {

    private List<String> parserList;

    private List<HatchetParser> parsers;

    @XmlElement(name = "parser-class")
    public List<String> getParserList() {
        return parserList;
    }

    @XmlElement(name = "parser-class")
    public void setParserList(List<String> parserList) {
        this.parserList = parserList;
    }

    public List<HatchetParser> getParsers() {
        return null;
    }

    public void setParsers(List<HatchetParser> parsers) {
        this.parsers = parsers;
    }

    @SuppressWarnings("unchecked")
    private void performClassLoad() throws ClassNotFoundException, IllegalAccessException, InstantiationException {
        if ((null != parsers && parsers.size() == parserList.size())) {
            return;
        }
        Collection<Class<HatchetParser>> parsersClasses = new HashSet<>(parserList.size());
        for (String classRep : parserList) {
            parsersClasses.add((Class<HatchetParser>) Class.forName(classRep));
        }
        if (null == parsers) {
            parsers = new ArrayList<>(parsersClasses.size());
        }
        for (Class<HatchetParser> parserClass : parsersClasses) {
            parsers.add(parserClass.newInstance());
        }
    }

    private void convertClassToString() {

    }
}

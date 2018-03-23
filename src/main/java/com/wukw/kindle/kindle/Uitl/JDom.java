package com.wukw.kindle.kindle.Uitl;

import org.dom4j.Element;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class JDom {

    public static Map<String, Object> recGetXmlElementValue(Element ele, Map<String, Object> map){
        List<Element> eleList = ele.elements();
        if (eleList.size() == 0) {
            map.put(ele.getName(), ele.getTextTrim());
            return map;
        } else {
            for (Iterator<Element> iter = eleList.iterator(); iter.hasNext();) {
                Element innerEle = iter.next();
                recGetXmlElementValue(innerEle, map);
            }
            return map;
        }
    }
}

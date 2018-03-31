package com.wukw.kindle.Util;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import java.util.HashMap;
import java.util.Map;

public class XmlUtil {

    public static Map<String,Object> xmlStringToMap(String xml) throws DocumentException {
        try {
            Map<String,Object> result =new HashMap<>();
            Document doc = doc = DocumentHelper.parseText(xml);
            Element el = doc.getRootElement();
            result = JDom.recGetXmlElementValue(el,result);
            return result;
        }catch(Exception e){
            e.printStackTrace();
        }
        return null;


    }
}

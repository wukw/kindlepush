package com.wukw.kindle.Model;

import lombok.Data;
import org.htmlcleaner.TagNode;
import org.w3c.dom.Document;

import javax.xml.xpath.XPath;

@Data
public class XPathResult {

    XPath xPath;
    TagNode tagNode;
    Document document;


}

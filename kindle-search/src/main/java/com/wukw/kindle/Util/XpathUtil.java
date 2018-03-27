package com.wukw.kindle.Util;

import com.wukw.kindle.Model.XPathResult;
import org.htmlcleaner.CleanerProperties;
import org.htmlcleaner.DomSerializer;
import org.htmlcleaner.HtmlCleaner;
import org.htmlcleaner.TagNode;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.w3c.dom.Document;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathFactory;
import java.io.IOException;

public class XpathUtil {

    public  static XPathResult getXPath(String url){
        XPathResult xPathResult = new XPathResult();
        try {
            Connection connect = Jsoup.connect(url);
            String html = connect.get().body().html();
            HtmlCleaner hc = new HtmlCleaner();
            TagNode tn = hc.clean(html);
            Document dom = new DomSerializer(new CleanerProperties()).createDOM(tn);
            XPath xPath = XPathFactory.newInstance().newXPath();
            xPathResult.setDocument(dom);
            xPathResult.setTagNode(tn);
            xPathResult.setXPath(xPath);
            return xPathResult;

        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;


    }
}

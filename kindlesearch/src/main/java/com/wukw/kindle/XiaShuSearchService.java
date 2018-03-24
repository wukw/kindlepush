package com.wukw.kindle;

import com.wukw.kindle.Model.XPathResult;
import com.wukw.kindle.Util.XpathUtil;
import org.htmlcleaner.CleanerProperties;
import org.htmlcleaner.DomSerializer;
import org.htmlcleaner.HtmlCleaner;
import org.htmlcleaner.TagNode;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class XiaShuSearchService {

    public void XiashuIndexSearch() throws IOException, ParserConfigurationException, XPathExpressionException {
        String url = "https://www.xiashu.la/";
        String indexExp = "//*[@class='subMenuCon']/*[@class='subMenu']/ul//li";
         List<String> indexUrlList = new ArrayList<>();
        XPathResult xPathResult = XpathUtil.getXPath(url);
        Object result;
        result = xPathResult.getXPath().evaluate(indexExp, xPathResult.getDocument(), XPathConstants.NODESET);
        if (result instanceof NodeList) {
            NodeList nodeList = (NodeList) result;
            System.out.println(nodeList.getLength());
            for (int i = 0; i < nodeList.getLength(); i++) {
                Node node = nodeList.item(i);
                if(node.getNodeValue() == null && (node.getFirstChild().getAttributes().getNamedItem("title")+"").indexOf("全本书籍") >1){
                    String indexurl = node.getFirstChild().getAttributes().getNamedItem("href")+"";
                    indexurl=indexurl.substring(6,indexurl.length()-1);
                    System.out.println(indexurl);
                    indexUrlList.add(indexurl);

                }
                System.out.println(
                            node.getNodeValue() == null ? node.getFirstChild().getAttributes().getNamedItem("title") : node.getNodeValue());
                    //System.out.println(indexUrlList);
            }
        }
        for(String indexUrl:indexUrlList){
            String secondeExp ="/*[@id='main']/*[@id='waterfall'] /*";
            XPathResult  xPathResult1Seconde=XpathUtil.getXPath(url+indexUrl);
            Object  resultseconde = xPathResult1Seconde.getXPath().evaluate(secondeExp, xPathResult1Seconde.getDocument(), XPathConstants.NODESET);
            if (resultseconde instanceof NodeList) {
                NodeList nodeList = (NodeList) resultseconde;
                System.out.println(nodeList.getLength());
                for (int i = 0; i < nodeList.getLength(); i++) {
                    Node node = nodeList.item(i);

                    System.out.println(
                            node.getNodeValue() == null ? node.getFirstChild().getAttributes().getNamedItem("href") : node.getNodeValue());
                    //System.out.println(indexUrlList);
                }
            }
        }





    }





    public static void main(String[] args) throws IOException, ParserConfigurationException, XPathExpressionException {
        new XiaShuSearchService().XiashuIndexSearch();
    }
}

package com.wukw.kindle;

import com.wukw.kindle.Model.Book;
import com.wukw.kindle.Model.XPathResult;
import com.wukw.kindle.Util.XpathUtil;
import org.springframework.beans.BeanUtils;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class XiaShuSearchService {
    String  xiashuurl = "https://www.xiashu.la/";

    /**
     * 首页处理
     * @throws IOException
     * @throws ParserConfigurationException
     * @throws XPathExpressionException
     */
    public List<String> XiaShuIndexSearch() throws IOException, ParserConfigurationException, XPathExpressionException {
        //首页选择类目
        String indexExp = "//*[@class='subMenuCon']/*[@class='subMenu']/ul//li";
        List<String> indexUrlList = new ArrayList<>();
        XPathResult xPathResult = XpathUtil.getXPath(xiashuurl);
        Object result = xPathResult.getXPath().evaluate(indexExp, xPathResult.getDocument(), XPathConstants.NODESET);
        if (result instanceof NodeList) {
            NodeList nodeList = (NodeList) result;
            System.out.println(nodeList.getLength());
            for (int i = 0; i < nodeList.getLength(); i++) {
                Node node = nodeList.item(i);
                if (node.getNodeValue() == null && (node.getFirstChild().getAttributes().getNamedItem("title") + "").indexOf("全本书籍") > 1) {
                    String indexurl = node.getFirstChild().getAttributes().getNamedItem("href") + "";
                    indexurl = indexurl.substring(6, indexurl.length() - 1);
                    System.out.println(indexurl);
                    indexUrlList.add(indexurl);

                }
                System.out.println(
                        node.getNodeValue() == null ? node.getFirstChild().getAttributes().getNamedItem("title") : node.getNodeValue());
                //System.out.println(indexUrlList);
            }
        }
        return indexUrlList;
    }

    /**
     * 下书网book 递归翻页
     * @param rooturl
     * @param pageurl
     * @param num
     * @return
     * @throws XPathExpressionException
     */
    public List<Book> XiaShuBookPageSeach(String rooturl,String pageurl,int num) throws XPathExpressionException {

        List<Book> firstDownUrlList = new ArrayList<>();
            //获取书本资源
            String secondeExp = "//*[@class='pic']/a/img";
            XPathResult xPathResult1Seconde = XpathUtil.getXPath(rooturl+pageurl);
            Object result = xPathResult1Seconde.getXPath().evaluate(secondeExp, xPathResult1Seconde.getDocument(), XPathConstants.NODESET);
            if (result instanceof NodeList) {
                for (int i = 0; i < ((NodeList) result).getLength(); i++) {
                    Node node2 = ((NodeList) result).item(i);
                    String bookid = node2.getParentNode().getAttributes().getNamedItem("href") + "";
                    String author = node2.getParentNode().getParentNode().getTextContent().replace("\n", "").replace(" ", "");
                    String titel = node2.getAttributes().getNamedItem("alt") + "";
                    String downurl = rooturl + bookid.substring(7, bookid.length() - 1) + "down";
                    String pic = node2.getAttributes().getNamedItem("data-original").toString();
                    Book book = new Book();
                    book.setName(titel);
                    book.setFirstUrl(downurl);
                    book.setPic(pic);
                    book.setAuthor(author);
                    firstDownUrlList.add(book);
                }
            }

        XiaShuSearchService s = new XiaShuSearchService();
        firstDownUrlList = s.XiaShuDownSelectPage(firstDownUrlList);
        firstDownUrlList = s.XiaShuDownPage(firstDownUrlList);
        String pageInfoExp ="//*[@id=\"main\"]/div[3]/span";
        Object resultPageInfo = xPathResult1Seconde.getXPath().evaluate(pageInfoExp, xPathResult1Seconde.getDocument(), XPathConstants.NODE);
        if(resultPageInfo instanceof  Node){
            Node node = (Node)resultPageInfo;
            String pageinfo = node.getTextContent();
            System.out.println("分页信息"+pageinfo+"爬到第几"+num+"页");
            Integer pagenum = new Integer(pageinfo.substring(1,5));
            if(num < pagenum) {
                num++;
                String newPageUrl = pageurl.substring(0, pageurl.length() - 6)+num+".html";
                s.XiaShuBookPageSeach(rooturl,newPageUrl,num);
            }

        }
        return firstDownUrlList;
    }

    /**
     * 下书网下载来源选择
     * @param firstDownUrlList
     * @return
     * @throws XPathExpressionException
     */
    public List<Book> XiaShuDownSelectPage(List<Book> firstDownUrlList) throws XPathExpressionException {

        List<Book> downUrlList = new ArrayList<>();
        for (Book temp : firstDownUrlList) {
            XPathResult xPathResultDownPage = XpathUtil.getXPath(temp.getFirstUrl());
            String downPageExp = "//*[@id=\"downlist\"]/ul/li[1]/span[5]/a";
            Object result = xPathResultDownPage.getXPath().evaluate(downPageExp, xPathResultDownPage.getDocument(), XPathConstants.NODESET);
            if (result instanceof NodeList) {
                for (int i = 0; i < ((NodeList) result).getLength(); i++) {
                    Node node3 = ((NodeList) result).item(i);
                    //System.out.println("第一步下载路径"+node3.getAttributes().getNamedItem("href"));
                    String downurl = node3.getAttributes().getNamedItem("href") + "";
                    Book book = new Book();
                    BeanUtils.copyProperties(temp, book);
                    book.setSecondUrl(downurl.substring(6, downurl.length() - 1).replaceAll("amp;", ""));
                    downUrlList.add(book);
                }

            }
        }




        return downUrlList;
    }

    /**
     * 下书网下载跳转
     * @param downUrlList
     * @return
     * @throws XPathExpressionException
     */
    public List<Book> XiaShuDownPage(List<Book> downUrlList) throws XPathExpressionException {
        for (Book temp : downUrlList) {
            System.out.println("第一步下载路径" + temp.getSecondUrl());
            XPathResult xPathResultRealDownPage = XpathUtil.getXPath(temp.getSecondUrl());
            String realDownpage = "/html/body/div/div[1]/div/p[2]/a";
            Object realDownresult = xPathResultRealDownPage.getXPath().evaluate(realDownpage, xPathResultRealDownPage.getDocument(), XPathConstants.NODE);
            if (realDownresult instanceof Node) {
                Node realUrlNode = (Node) realDownresult;
                String realUrl = realUrlNode.getAttributes().getNamedItem("href").toString();
                temp.setRealUrl(realUrl.substring(6,realUrl.length()-1));

            }

        }
        return downUrlList;
    }














    public static void main(String[] args) throws IOException, ParserConfigurationException, XPathExpressionException {
        XiaShuSearchService s = new XiaShuSearchService();
        List<String> indexUrl=s.XiaShuIndexSearch();
        List<Book> bookList =s.XiaShuBookPageSeach("https://www.xiashu.la/",indexUrl.get(0),1);
        //bookList = s.XiaShuDownSelectPage(bookList);
        //bookList = s.XiaShuDownPage(bookList);
    }
}

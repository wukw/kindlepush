package com.wukw.kindle.Service;

import com.wukw.kindle.Dao.BookDao;
import com.wukw.kindle.Model.ResourceBook;
import com.wukw.kindle.Model.XPathResult;
import com.wukw.kindle.Util.XpathUtil;
import com.wukw.kindle.repo.model.Book;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
@Service
@Slf4j
public class XiaShuSearchService {

    @Autowired
    BookDao bookDao;
    @Autowired
    XiaShuSearchService xiaShuSearchService;


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
    public List<ResourceBook> XiaShuBookPageSeach(String rooturl, String pageurl, int num) throws XPathExpressionException {
        String pagenumurl = pageurl+num+".html";
        List<ResourceBook> firstDownUrlList = new ArrayList<>();
            //获取书本资源
            String secondeExp = "//*[@class='pic']/a/img";
            XPathResult xPathResult1Seconde = XpathUtil.getXPath(rooturl+pagenumurl);
            System.out.println("-----------------页面路径:"+rooturl+pageurl);
            Object result = xPathResult1Seconde.getXPath().evaluate(secondeExp, xPathResult1Seconde.getDocument(), XPathConstants.NODESET);
            if (result instanceof NodeList) {
                for (int i = 0; i < ((NodeList) result).getLength(); i++) {
                    Node node2 = ((NodeList) result).item(i);
                    String bookid = node2.getParentNode().getAttributes().getNamedItem("href") + "";
                    String author = node2.getParentNode().getParentNode().getTextContent().replace("\n", "").replace(" ", "");
                    String titel = node2.getAttributes().getNamedItem("alt") + "";
                    String downurl = rooturl + bookid.substring(7, bookid.length() - 1) + "down";
                    String pic = node2.getAttributes().getNamedItem("data-original").toString();
                    ResourceBook book = new ResourceBook();
                    book.setName(titel.substring(5,titel.length()-1));
                    book.setFirstUrl(downurl);
                    book.setPic(pic);
                    book.setAuthor(author);
                    firstDownUrlList.add(book);
                }
            }
        try {
            firstDownUrlList = xiaShuSearchService.XiaShuDownSelectPage(firstDownUrlList);
            firstDownUrlList = xiaShuSearchService.XiaShuDownPage(firstDownUrlList);
        }catch (Exception e){
                e.printStackTrace();
        }
        String pageInfoExp ="//*[@id=\"main\"]/div[3]/span";
        Object resultPageInfo = xPathResult1Seconde.getXPath().evaluate(pageInfoExp, xPathResult1Seconde.getDocument(), XPathConstants.NODE);
        if(resultPageInfo instanceof  Node){
            Node node = (Node)resultPageInfo;
            String pageinfo = node.getTextContent();
            System.out.println("分页信息"+pageinfo+"爬到第几"+num+"页");
            log.info("分页信息"+pageinfo+"爬到第"+num+"页");
            Integer pagenum = new Integer(pageinfo.substring(1,5));
            if(num < pagenum) {
                num++;
                //String newPageUrl = pageurl+num+".html";
                xiaShuSearchService.XiaShuBookPageSeach(rooturl,pageurl,num);
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
    public List<ResourceBook> XiaShuDownSelectPage(List<ResourceBook> firstDownUrlList) throws XPathExpressionException {

        List<ResourceBook> downUrlList = new ArrayList<>();
        for (ResourceBook temp : firstDownUrlList) {
            XPathResult xPathResultDownPage = XpathUtil.getXPath(temp.getFirstUrl());
            String downPageExp = "//*[@id=\"downlist\"]/ul/li[1]/span[5]/a";
            Object result = xPathResultDownPage.getXPath().evaluate(downPageExp, xPathResultDownPage.getDocument(), XPathConstants.NODESET);
            if (result instanceof NodeList) {
                for (int i = 0; i < ((NodeList) result).getLength(); i++) {
                    Node node3 = ((NodeList) result).item(i);
                    //System.out.println("第一步下载路径"+node3.getAttributes().getNamedItem("href"));
                    String downurl = node3.getAttributes().getNamedItem("href") + "";
                    ResourceBook book = new ResourceBook();
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
    public List<ResourceBook> XiaShuDownPage(List<ResourceBook> downUrlList) throws XPathExpressionException {
        for (ResourceBook temp : downUrlList) {
            System.out.println("第一步下载路径" + temp.getSecondUrl());

            XPathResult xPathResultRealDownPage = null;
            try {
                xPathResultRealDownPage = XpathUtil.getXPath(temp.getSecondUrl());
            }catch (Exception e){
                continue;
            }
            String realDownpage = "/html/body/div/div[1]/div/p[2]/a";
            Object realDownresult = xPathResultRealDownPage.getXPath().evaluate(realDownpage, xPathResultRealDownPage.getDocument(), XPathConstants.NODE);
            if (realDownresult instanceof Node) {
                Node realUrlNode = (Node) realDownresult;
                String realUrl = realUrlNode.getAttributes().getNamedItem("href").toString();
                System.out.println("真实下载路径"+realUrl);
                temp.setRealUrl(realUrl.substring(6,realUrl.length()-1));
                Book book = new Book();
                book.setCreateTime(new Date());
                book.setOriginUrl(temp.getRealUrl());
                book.setName(temp.getName());
                book.setPic(temp.getPic());
                book.setDes(temp.getDes());
                book.setAuthor(temp.getAuthor());
                bookDao.save(book);
            }

        }
        return downUrlList;
    }














    public static void main(String[] args) throws IOException, ParserConfigurationException, XPathExpressionException {
        XiaShuSearchService s = new XiaShuSearchService();
        List<String> indexUrl=s.XiaShuIndexSearch();
        List<ResourceBook> bookList =s.XiaShuBookPageSeach("https://www.xiashu.la/",indexUrl.get(0),1);
        //bookList = s.XiaShuDownSelectPage(bookList);
        //bookList = s.XiaShuDownPage(bookList);
    }
}

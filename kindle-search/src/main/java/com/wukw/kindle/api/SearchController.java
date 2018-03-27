package com.wukw.kindle.api;

import com.wukw.kindle.Model.ResourceBook;
import com.wukw.kindle.Service.XiaShuSearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPathExpressionException;
import java.io.IOException;
import java.util.List;

@RestController
public class SearchController {

    @Autowired
    XiaShuSearchService XiaShuSearchService;
    @GetMapping("startSearch")
    public void startSearch(Integer num) throws ParserConfigurationException, XPathExpressionException, IOException {
        List<String> indexUrl=XiaShuSearchService.XiaShuIndexSearch();
        String url = indexUrl.get(0).substring(0,indexUrl.get(0).length()-6);
        List<ResourceBook> bookList =XiaShuSearchService.XiaShuBookPageSeach("https://www.xiashu.la/",url,num);

    }
}

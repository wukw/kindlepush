package com.wukw.kindle.Controller;

import com.wukw.kindle.Service.BookService;
import com.wukw.kindle.Service.JiuMoResourceService;
import com.wukw.kindle.Util.HttpUtil;
import com.wukw.kindle.Util.WXUtil;
import com.wukw.kindle.Util.XmlUtil;
import com.wukw.kindle.repo.model.Book;
import org.dom4j.DocumentException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("wx")
public class WXTestController {

    @Autowired
    JiuMoResourceService jiuMoResourceService;
    @Autowired
    BookService bookService ;

    @GetMapping("test")
    public String test(String echostr){
        return echostr;
    }

    @PostMapping("test")
    public String test2(HttpServletRequest request){
        Map<String,Object> map = new HashMap<String,Object>();
        String  resultxml = null;
        try {
            resultxml = HttpUtil.getRequestInputStream(request);
            map = XmlUtil.xmlStringToMap(resultxml);
            //获取参数
            String key = (String)map.get("Content");
            String FromUserName = (String)map.get("FromUserName");
            String ToUserName = (String)map.get("ToUserName");
            //查询资源
            //List<DataInfo> dataInfos =  jiuMoResourceService.getOriginResource(key);
            //组装返回信息
            //List<WXImageMessage> list =jiuMoResourceService.bookResourcesTOWxMessage(dataInfos);

            List<Book> bookPage = bookService.quertBookList(key,1,8);


            resultxml = WXUtil.createBookUrlMesg(ToUserName,FromUserName,new Date().getTime()/1000,bookPage);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (DocumentException e) {
            e.printStackTrace();
        }
        System.out.println(resultxml.toString());
        return resultxml.toString();
    }
}

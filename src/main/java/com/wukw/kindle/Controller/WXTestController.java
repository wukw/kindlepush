package com.wukw.kindle.Controller;

import com.wukw.kindle.Model.DataInfo;
import com.wukw.kindle.Model.WXImageMessage;
import com.wukw.kindle.Service.ResourceService;
import com.wukw.kindle.Uitl.JDom;
import com.wukw.kindle.Uitl.WXUtil;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

@RestController
@RequestMapping("wx")
public class WXTestController {

    @Autowired
    ResourceService resourceService;

    @GetMapping("test")
    public String test(String echostr){
        return echostr;
    }

    @PostMapping("test")
    public String test2(HttpServletRequest request){
        Map<String,Object> map = new HashMap<String,Object>();
        String buffer = null;
        StringBuffer xml = null;
        String  resultxml = null;
        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(request.getInputStream(), "UTF-8"));
            xml = new StringBuffer();
            while ((buffer = br.readLine()) != null) {
                xml.append(buffer);
            }
            Document doc = doc = DocumentHelper.parseText(xml.toString());
            Element el = doc.getRootElement();
            map = JDom.recGetXmlElementValue(el,map);
            System.out.println(br.toString());
            String key = (String)map.get("Content");
            String FromUserName = (String)map.get("FromUserName");
            String ToUserName = (String)map.get("ToUserName");
            System.out.println(key);
            //return resourceService.getOriginResource(key);
            List<WXImageMessage> list = new ArrayList<>();
            WXImageMessage wxImageMessage = new WXImageMessage();
            wxImageMessage.setDescription("简介");
            wxImageMessage.setTitle("标题");
            wxImageMessage.setUrl("http://www.baidu.com");
            wxImageMessage.setPicUrl("http://wx.qlogo.cn/mmopen/vi_32/Q0j4TwGTfTJ92vuknlfSNjNgc0oENsxKJeHSibLcxvn47n9GVVQEh6OxZIYy67uiaHGV8DJmGdy6yp4E8wh7XcHw/132");
            list.add(wxImageMessage);
            resultxml = WXUtil.createUrlMesg(ToUserName,FromUserName,new Date().getTime()/1000,list);


        } catch (IOException e) {
            e.printStackTrace();
        } catch (DocumentException e) {
            e.printStackTrace();
        }

        System.out.println(resultxml.toString());
        return resultxml.toString();
    }
}

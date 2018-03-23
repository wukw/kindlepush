package com.wukw.kindle.kindle.Uitl;


import com.wukw.kindle.kindle.Model.WXImageMessage;

import java.util.List;

public class WXUtil {
    /**
     * 创建微信普通消息
     * @param ToUserName
     * @param FromUserName
     * @param CreateTime
     * @param Content
     * @param MsgId
     * @return
     */
     public static String  createSimpleMesg(String ToUserName,String FromUserName,Long CreateTime,String Content,String MsgId){
         StringBuffer resultxml = new StringBuffer();
         resultxml.append("<?xml version=1.0?>");
         resultxml.append("<xml>");
         resultxml.append("<ToUserName><![CDATA["+FromUserName+"]]></ToUserName>");
         resultxml.append("<FromUserName><![CDATA["+ToUserName+"]]></FromUserName>");
         resultxml.append("<CreateTime>"+CreateTime+"</CreateTime>");
         resultxml.append("<MsgType><![CDATA[text]]></MsgType>");
         resultxml.append("<Content><![CDATA["+Content+"]]></Content>");
         resultxml.append("<MsgId>"+MsgId+"</MsgId>");
         resultxml.append("</xml>");
         return resultxml.toString();
     }

    /**
     * 创建image消息
     * @param ToUserName
     * @param FromUserName
     * @param CreateTime
     * @return
     */
    public static String  createUrlMesg(String ToUserName,
                                        String FromUserName,
                                        Long CreateTime,
                                        List<WXImageMessage> message){
        StringBuffer resultxml = new StringBuffer();
        resultxml.append("<?xml version=1.0?>");
        resultxml.append("<xml>");
        resultxml.append("<ToUserName><![CDATA["+FromUserName+"]]></ToUserName>");
        resultxml.append("<FromUserName><![CDATA["+ToUserName+"]]></FromUserName>");
        resultxml.append("<CreateTime>"+CreateTime+"</CreateTime>");
        resultxml.append("<MsgType><![CDATA[news]]></MsgType>");
        resultxml.append("<ArticleCount>"+message.size()+"</ArticleCount>");
        resultxml.append("<Articles>");
        for(WXImageMessage temp:message){
            resultxml.append("<item>");
            resultxml.append("<Title><![CDATA["+temp.getTitle()+"]]></Title>");
            resultxml.append("<Description><![CDATA["+temp.getDescription()+"]]></Description>");
            resultxml.append("<PicUrl><![CDATA["+temp.getPicUrl()+"]]></PicUrl>");
            resultxml.append("<Url><![CDATA["+temp.getUrl()+"]]></Url>");
            resultxml.append("</item>");
        }
        resultxml.append("</Articles>");
        resultxml.append("</xml>");
        return resultxml.toString();
    }










}

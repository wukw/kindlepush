package com.wukw.kindle.kindle.Service;

import com.alibaba.fastjson.JSON;
import com.wukw.kindle.kindle.Uitl.HttpUtil;
import com.wukw.kindle.kindle.Model.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Service
public class JiuMoResourceService {

    @Value("${originurl}")
    String originurl;

    public List<DataInfo> getOriginResource(String  key ) throws InterruptedException {
        String firstUrl = "https://www2.jiumodiary.com/init_hubs.php";
        Map<String,Object> map = new HashMap<>();
        map.put("q",key);
        String firstJson = HttpUtil.HttpPost(firstUrl,map);
        ResourceCheckCode code = JSON.parseObject(firstJson, ResourceCheckCode.class);
        System.out.println(code.getId());

        TimeUnit.SECONDS.sleep(1);
        String secondUrl = originurl;
        Map<String,Object> secondMap = new HashMap<>();
        secondMap.put("id",code.getId());
        secondMap.put("set",0);
        String secondJson = HttpUtil.HttpPost(secondUrl,secondMap);
        System.out.println("资源"+secondJson);
        Resource resource = JSON.parseObject(secondJson, Resource.class);
        System.out.println(resource.getStatus());
        List<DataInfo> allDateInfoList = new ArrayList<>();
        List<Sources> sourcesList = resource.getSources();
        for(Sources sources : sourcesList){
            List<Detail> detailList = sources.getDetails();
            for(Detail detail : detailList){
                List<DataInfo> dataInfoList = detail.getData();
                for(DataInfo dataInfo : dataInfoList){
                    if(dataInfo.getLink() != null && dataInfo.getLink().indexOf("doudou") >0)
                        allDateInfoList.add(dataInfo);
                }
            }
        }
        //线程池下载
        DownServiceRunable.Execute(allDateInfoList,3);
        System.out.println("查询结束");
        return allDateInfoList;
    }

    public List<WXImageMessage> bookResourcesTOWxMessage(List<DataInfo> dataInfoList){
        List<WXImageMessage>  imageMessageList = new ArrayList<>();
        int maxsize = dataInfoList.size()>7?7:dataInfoList.size();
        for(int i=0;i<maxsize;i++){
            DataInfo dataInfo = dataInfoList.get(i);
            WXImageMessage wxImageMessage = new WXImageMessage();
            wxImageMessage.setUrl(dataInfo.getLink());
            wxImageMessage.setTitle(dataInfo.getTitle());
            wxImageMessage.setDescription(dataInfo.getDes());
            wxImageMessage.setPicUrl("http://cartmall.oss-cn-hangzhou.aliyuncs.com/img/1521798746284.jpg");
            imageMessageList.add(wxImageMessage);
        }
        return imageMessageList;
    }




    public static void main(String[] args) throws InterruptedException {
        new JiuMoResourceService().getOriginResource("斗罗大陆");
    }
}

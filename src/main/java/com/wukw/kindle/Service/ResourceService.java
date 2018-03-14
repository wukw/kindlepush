package com.wukw.kindle.Service;

import com.alibaba.fastjson.JSON;
import com.wukw.kindle.Model.*;
import com.wukw.kindle.Uitl.HttpUtil;
import com.wukw.kindle.Uitl.UrlDown;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

@Service
public class ResourceService {


    public List<DataInfo> getOriginResource(String  key ) throws InterruptedException {
        String firstUrl = "https://www2.jiumodiary.com/init_hubs.php";
        Map<String,Object> map = new HashMap<>();
        map.put("q",key);
        String firstJson = HttpUtil.HttpPost(firstUrl,map);
        ResourceCheckCode code = JSON.parseObject(firstJson, ResourceCheckCode.class);
        System.out.println(code.getId());

        TimeUnit.SECONDS.sleep(1);
        String secondUrl = "https://www.jiumodiary.com/ajax_fetch_hubs.php";
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
                    allDateInfoList.add(dataInfo);
                }
            }
        }
        //线程池下载
        DownServiceRunable.Execute(allDateInfoList,3);
        System.out.println("查询结束");
        return allDateInfoList;



    }


    public static void main(String[] args) throws InterruptedException {
        new ResourceService().getOriginResource("斗罗大陆");
    }
}

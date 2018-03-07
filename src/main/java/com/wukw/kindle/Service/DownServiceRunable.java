package com.wukw.kindle.Service;

import com.sun.scenario.effect.impl.sw.sse.SSEBlend_SRC_OUTPeer;
import com.wukw.kindle.Model.DataInfo;
import com.wukw.kindle.Uitl.UrlDown;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

public class DownServiceRunable implements  Runnable {

    List<DataInfo> dataInfoList = new ArrayList<>();
    DownServiceRunable(List<DataInfo> list){
        this.dataInfoList =  list;
    }


    AtomicInteger i = new AtomicInteger(-1);

    @Override
    public void run() {
        while (i.intValue() < dataInfoList.size()) {
            i.addAndGet(1);
            System.out.println("执行下载"+i.intValue()+"线程id"+Thread.currentThread().getId());

            DataInfo dataInfo = dataInfoList.get(i.intValue());
            if (dataInfo.getLink() != null &&
                    (dataInfo.getTitle().indexOf("mobi") > 0 || dataInfo.getTitle().indexOf("epub") > 0)) {

                String bookname = null;
                if (dataInfo.getTitle().indexOf("mobi") > 0) {
                    bookname = dataInfo.getTitle()+i.intValue() + ".mobi";
                }
                if (dataInfo.getTitle().indexOf("epub") > 0) {
                    bookname = dataInfo.getTitle()+i.intValue() + ".epub";
                }

                //System.out.println("开始下载" + i.intValue() + "-------" + dataInfoList.size() + dataInfo.getLink());
                UrlDown.Down(dataInfo.getLink(), bookname, "/Users/wukaiwei/book");


            }

        }
    }





    public static void Execute(List<DataInfo> dataInfoList){
        DownServiceRunable downServiceRunable = new DownServiceRunable(dataInfoList);
        Executors.newFixedThreadPool(10).execute(downServiceRunable);

    }
}

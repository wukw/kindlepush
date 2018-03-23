package com.wukw.kindle.kindle.Service;

import com.wukw.kindle.kindle.KIndleAPP;
import com.wukw.kindle.kindle.Model.DataInfo;
import com.wukw.kindle.kindle.Uitl.UrlDown;
import com.wukw.kindle.kindle.config.SetProperties;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

public class DownServiceRunable implements  Runnable {

   SetProperties setProperties =  (SetProperties) KIndleAPP.springContext.getBean("SetProperties");
   public  final String savapath =setProperties.getSavepath();

    List<DataInfo> dataInfoList = new ArrayList<>();
    DownServiceRunable(List<DataInfo> list){
        this.dataInfoList =  list;
    }


    AtomicInteger i = new AtomicInteger(-1);

    @Override
    public void run() {
        while (i.intValue() < dataInfoList.size()) {

            i.addAndGet(1);
            System.out.println("执行下载任务"+i.intValue()+"线程id"+Thread.currentThread().getId());

            DataInfo dataInfo = dataInfoList.get(i.intValue());
            if (dataInfo.getLink() != null &&  dataInfo.getLink().indexOf("doudou") > 0 &&
                    (dataInfo.getTitle().indexOf("mobi") > 0 || dataInfo.getTitle().indexOf("epub") > 0)) {

                String bookname = null;
                try {
                    if (dataInfo.getTitle().indexOf("mobi") > 0) {
                        bookname = new String((dataInfo.getTitle() + ".mobi").getBytes(), "utf-8");
                    }
                    if (dataInfo.getTitle().indexOf("epub") > 0) {
                        bookname = new String((dataInfo.getTitle() + ".epub").getBytes(), "utf-8");
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }
                //System.out.println("开始下载" + i.intValue() + "-------" + dataInfoList.size() + dataInfo.getLink());
                UrlDown.Down(dataInfo.getLink(), bookname, savapath);


            }

        }
    }





    public static void Execute(List<DataInfo> dataInfoList,int threadNum){
        DownServiceRunable downServiceRunable = new DownServiceRunable(dataInfoList);
        ExecutorService executorService = Executors.newCachedThreadPool();
        while(threadNum >= 0){
            threadNum--;
            executorService.execute(downServiceRunable);
        }
    }
}

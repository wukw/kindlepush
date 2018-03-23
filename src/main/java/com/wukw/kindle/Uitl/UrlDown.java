package com.wukw.kindle.Uitl;

import sun.nio.ch.DirectBuffer;

import java.awt.image.DirectColorModel;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

public class UrlDown {

    public static void Down(String urlStr,String fileName,String savePath)  {
       try {
           System.out.println("开始下载");

           File file = new File(urlStr);
           System.out.println("文件名字"+file.getName());
           URL url = new URL(urlStr);
           HttpURLConnection conn = (HttpURLConnection) url.openConnection();
           conn.setConnectTimeout(3 * 1000);
           //防止屏蔽程序抓取而返回403错误
           conn.setRequestProperty("User-Agent", "Mozilla/5.0 (Linux; Android 7.0; STF-AL10 Build/HUAWEISTF-AL10; wv) AppleWebKit/537.36 (KHTML, like Gecko) Version/4.0 Chrome/53.0.2785.49 Mobile MQQBrowser/6.2 TBS/043508 Safari/537.36 V1_AND_SQ_7.2.0_730_YYB_D QQ/7.2.0.3270 NetType/4G WebP/0.3.0 Pixel/1080");
           //得到输入流
           InputStream inputStream = conn.getInputStream();

           //获取自己数组
           byte[] getData = readInputStream(inputStream);
           FileChannel fileChannel ;
           fileChannel = new FileOutputStream(savePath + fileName).getChannel();
           ByteBuffer bf = ByteBuffer.wrap(getData);
           fileChannel.write(bf);

           fileChannel.close();

           if (inputStream != null) {
               inputStream.close();
           }
           System.out.println("info:" + url + " download success");
           System.out.println("下载成功");
       }catch (Exception e){
           e.printStackTrace();
       }


    }

    public static  byte[] readInputStream(InputStream inputStream) throws IOException {
        byte[] buffer = new byte[1024];
        int len = 0;
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        while((len = inputStream.read(buffer)) != -1) {
            bos.write(buffer, 0, len);
        }
        bos.close();
        return bos.toByteArray();
    }


}

package com.wukw.kindle.Uitl;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class UrlDown {

    public static void Down(String urlStr,String fileName,String savePath)  {
       try {


           URL url = new URL(urlStr);
           HttpURLConnection conn = (HttpURLConnection) url.openConnection();
           conn.setConnectTimeout(3 * 1000);
           //防止屏蔽程序抓取而返回403错误
           conn.setRequestProperty("User-Agent", "Mozilla/4.0 (compatible; MSIE 5.0; Windows NT; DigExt)");
           //得到输入流
           InputStream inputStream = conn.getInputStream();
           //获取自己数组
           byte[] getData = readInputStream(inputStream);

           //文件保存位置
           File saveDir = new File(savePath);
           if (!saveDir.exists()) {
               saveDir.mkdir();
           }
           File file = new File(saveDir + File.separator + fileName);
           FileOutputStream fos = new FileOutputStream(file);
           fos.write(getData);
           if (fos != null) {
               fos.close();
           }
           if (inputStream != null) {
               inputStream.close();
           }


           System.out.println("info:" + url + " download success");
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

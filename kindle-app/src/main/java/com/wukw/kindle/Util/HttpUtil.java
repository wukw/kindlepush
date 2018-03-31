package com.wukw.kindle.Util;


import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class HttpUtil {


    public static  String HttpPost(String url,Map<String,Object> map ) {

        try{
                List<NameValuePair> list = new LinkedList<NameValuePair>();
                if(map != null) {
                    for (Map.Entry<String, Object> entry : map.entrySet()) {
                        list.add(new BasicNameValuePair(entry.getKey(), entry.getValue().toString()));
                    }
                }
                HttpPost httpPost = new HttpPost(url);

//                httpPost.setHeader("access-control-allow-headers", "Origin, X-Requested-With, Content-Type, Accept");
//
//                httpPost.setHeader("connection", "keep-alive");
//
//                httpPost.setHeader("content-encoding", "gzip");
//
//                httpPost.setHeader("date", new Date().toString());
//
//                httpPost.setHeader("server", "nginx/1.10.3 (Ubuntu)");
//
//                httpPost.setHeader("transfer-encoding", "chunked");




                UrlEncodedFormEntity formEntity = new UrlEncodedFormEntity(list,"utf-8");
                httpPost.setEntity(formEntity);
                HttpClient httpCient = HttpClients.createDefault();
                HttpResponse httpresponse = null;

                httpresponse = httpCient.execute(httpPost);
                HttpEntity httpEntity = httpresponse.getEntity();
                String response = EntityUtils.toString(httpEntity, "utf-8");
                System.out.println(response);
                return response;
            }catch(ClientProtocolException e){
                System.out.println("http请求失败，uri{},exception{}");
            }catch(IOException e){
                System.out.println("http请求失败，uri{},exception{}");
            }
            return null;
    }


    public  static  String getRequestInputStream(HttpServletRequest request) throws IOException {

        BufferedReader br = new BufferedReader(new InputStreamReader(request.getInputStream(), "UTF-8"));
        StringBuffer content = new StringBuffer();
        String buffer = null;
        while ((buffer = br.readLine()) != null) {
            content.append(buffer);
        }
        return content.toString();
    }





}

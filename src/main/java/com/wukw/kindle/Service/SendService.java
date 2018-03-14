package com.wukw.kindle.Service;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.*;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.Properties;

public class SendService {


    /**
     * 创建邮件 基本
     * @param session
     * @param fromAccount
     * @param toAccount
     * @return
     */
    public MimeMessage createMessage(Session session, String fromAccount, String toAccount,Boolean isSave){
        MimeMessage message = new MimeMessage(session);
        try {
            message.setFrom(fromAccount);
            message.setRecipient(MimeMessage.RecipientType.TO, new InternetAddress(toAccount, "USER_DD", "UTF-8"));
            message.setSentDate(new Date());
            message.setSubject("TEST邮件主题", "UTF-8");
            message.setContent("TEST这是邮件正文。。。", "text/html;charset=UTF-8");
            if(isSave ) {
                message.saveChanges();
                OutputStream out = new FileOutputStream("D:\\mail\\test.eml");
                message.writeTo(out);
                out.flush();
                out.close();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return message;
    }

    /**
     * 创建邮件 带附件
     * @param session
     * @param fromAccount
     * @param toAccount
     * @param fileString
     * @return
     * @throws MessagingException
     * @throws UnsupportedEncodingException
     */
    public MimeMessage createMessage(Session session, String fromAccount, String toAccount,String fileString) throws MessagingException, UnsupportedEncodingException {
       MimeMessage message =  createMessage(session,fromAccount,toAccount,false);
        MimeBodyPart attachment = new MimeBodyPart();
        FileDataSource fileDataSource = new FileDataSource(fileString);
        DataHandler dh2 = new DataHandler(fileDataSource);  // 读取本地文件
        attachment.setDataHandler(dh2);                                             // 将附件数据添加到“节点”
        attachment.setFileName(MimeUtility.encodeText(dh2.getName()));
        MimeMultipart mm = new MimeMultipart();
        mm.addBodyPart(attachment);
        mm.setSubType("mixed");
        message.setContent(mm);
        message.saveChanges();
        return message;
    }














    public void sendMessage() throws MessagingException, UnsupportedEncodingException {
        String myEmailAccount = "18667912987@163.com";
        String myEmailPassword = "wkw581x0";
        String myEmailSMTPHost = "smtp.163.com";
        String receiveMailAccount = "522310157@qq.com";

        Properties props = new Properties();                    // 参数配置
        props.setProperty("mail.transport.protocol", "smtp");   // 使用的协议（JavaMail规范要求）
        props.setProperty("mail.smtp.host", myEmailSMTPHost);   // 发件人的邮箱的 SMTP 服务器地址
        props.setProperty("mail.smtp.auth", "true");

        final String smtpPort = "465";
        props.setProperty("mail.smtp.port", smtpPort);
        props.setProperty("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        props.setProperty("mail.smtp.socketFactory.fallback", "false");
        props.setProperty("mail.smtp.socketFactory.port", smtpPort);



        Session session = Session.getInstance(props);
        session.setDebug(true);
        String url = "D:\\book\\斗罗大陆 - 唐家三少 [mobi].mobi";
        MimeMessage message = createMessage(session, myEmailAccount, receiveMailAccount,url);
        Transport transport = session.getTransport();
        transport.connect(myEmailAccount,myEmailPassword);
        transport.sendMessage(message, message.getAllRecipients());

        // 7. 关闭连接
        transport.close();
    }




    public static void main(String[] args) throws MessagingException, UnsupportedEncodingException {
        new SendService().sendMessage();
    }


}

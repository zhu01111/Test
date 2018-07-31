package com.huiyou.mzzn.utils;

import java.security.GeneralSecurityException;
import java.util.Properties;
import java.util.Random;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import com.sun.mail.util.MailSSLSocketFactory;

public class  MailMessage {
	public static String getCode(String email){
		JavaMailSenderImpl senderImpl = new JavaMailSenderImpl();  
        // 设定mail server  
        senderImpl.setHost("smtp.mxhichina.com");    
        // 建立邮件消息  
        SimpleMailMessage mailMessage = new SimpleMailMessage();  
        // 设置收件人，寄件人 用数组发送多个邮件  
        // String[] array = new String[] {"sun111@163.com","sun222@sohu.com"};  
        // mailMessage.setTo(array);  
        mailMessage.setTo(email);  
        mailMessage.setFrom("info@magent.net");  
        mailMessage.setSubject("验证码");
        int code=nextInt();
        mailMessage.setText("您好，您的验证码为"+code+"，请勿泄露给他人");    
        senderImpl.setUsername("info@magent.net"); // 根据自己的情况,设置username  
        senderImpl.setPassword("Qt20150904zju"); // 根据自己的情况, 设置password  
        MailSSLSocketFactory sf=null;
		try {
			sf = new MailSSLSocketFactory();
		} catch (GeneralSecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        sf.setTrustAllHosts(true);        
        Properties prop = new Properties(); 
        prop.put("mail.smtp.ssl.enable", "true");
        prop.put("mail.smtp.ssl.socketFactory", sf);
        prop.put(" mail.smtp.auth ", " true "); // 将这个参数设为true，让服务器进行认证,认证用户名和密码是否正确  
        prop.put(" mail.smtp.timeout ", " 25000 ");  
        senderImpl.setJavaMailProperties(prop);  
        // 发送邮件  
        senderImpl.send(mailMessage);
        return String.valueOf(code);
	}
	public static int nextInt(){
		Random random = new Random();
        int x = random.nextInt(8999);
		x = x+1000;
		return x;
	}
}

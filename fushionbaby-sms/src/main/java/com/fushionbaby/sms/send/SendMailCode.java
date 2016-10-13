package com.fushionbaby.sms.send;

import javax.mail.internet.MimeMessage;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
/***
 * 发送邮件
 * @author King 索亮
 *
 */
public class SendMailCode {
	
	static ApplicationContext ac = new ClassPathXmlApplicationContext(
			"classpath:conf/spring-mail.xml"
	);
	
	// 获取JavaMailSender bean
	static JavaMailSender sender = (JavaMailSender) ac.getBean("javaMailSender");
	
	/***
	 * 发送html的邮件验证码
	 */
	public static void sendHtmlEmail(String sender_email,String receiver_email,String email_subject,String email_content){
		try {
			JavaMailSenderImpl senderImpl = new JavaMailSenderImpl();
			MimeMessage mailMessage = senderImpl.createMimeMessage();
			// 设置utf-8或GBK编码，否则邮件会有乱码
			MimeMessageHelper messageHelper = new MimeMessageHelper(mailMessage, true, "utf-8");
		
			messageHelper.setFrom(sender_email);// 发送者
			messageHelper.setTo(receiver_email);// 接收者
			messageHelper.setSubject(email_subject);// 主题
			// 邮件内容，注意加参数true，表示启用html格式
			messageHelper.setText(email_content,true);// 邮件内容
			sender.send(mailMessage);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
}

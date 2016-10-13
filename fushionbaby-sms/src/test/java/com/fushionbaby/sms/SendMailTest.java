package com.fushionbaby.sms;

import java.io.File;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;

public class SendMailTest {
	public ApplicationContext ctx = null;

	public SendMailTest() {
		// 获取上下文
		ctx = new ClassPathXmlApplicationContext(
				"classpath:conf/spring-mail.xml");
	}

	@Test
	public void sendSimplMail() {
		// 获取JavaMailSender bean

		JavaMailSender sender = (JavaMailSender) ctx.getBean("javaMailSender");
		SimpleMailMessage mail = new SimpleMailMessage(); // <span
															// style="color: #ff0000;">注意SimpleMailMessage只能用来发送text格式的邮件</span>

		try {
			mail.setTo("xupeijun@alamt.com");// 接受者
			mail.setFrom("yili@aldmt.com");// 发送者,这里还可以另起Email别名，不用和xml里的username一致
			mail.setSubject("忘记密码提示(aladingshop.com)");// 主题
			mail.setText("尊敬的用户，您好:您在Fushionbaby(www.fushionbaby.com)点击了“忘记密码”按钮，故系统自动为您发送了这封邮件。您本次操作的验证码为：****。此验证码有效期为***，请在有效期内使用验证码进行修改。如果您不需要修改密码，或者您从未点击过“忘记密码”按钮，请忽略本邮件。");// 邮件内容
			sender.send(mail);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	public void sendHtmlMail() throws MessagingException {
		// 获取JavaMailSender bean
		JavaMailSender sender = (JavaMailSender) ctx.getBean("javaMailSender");
		JavaMailSenderImpl senderImpl = new JavaMailSenderImpl();
		MimeMessage mailMessage = senderImpl.createMimeMessage();
		// 设置utf-8或GBK编码，否则邮件会有乱码
		MimeMessageHelper messageHelper = new MimeMessageHelper(mailMessage,
				true, "utf-8");
		try {
			messageHelper.setTo("xupeijun@alamt.com");// 接受者
			messageHelper.setFrom("yili@aldmt.com");// 发送者
			messageHelper.setSubject("测试邮件");// 主题
			// 邮件内容，注意加参数true，表示启用html格式
			messageHelper
					.setText(
							"<strong>尊敬的用户，您好:</strong><br />"
	+"&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;您在Fushionbaby(www.fushionbaby.com)点击了\"忘记密码\"按钮，故系统自动为您发送了这封邮件。"
	+"您本次操作的验证码为：****。此验证码有效期为***，请在有效期内使用验证码进行修改。如果您不需要修改密码，或者您从未点击过\"忘记密码\"按钮，请忽略本邮件。",
							true);
			sender.send(mailMessage);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	public void sendAttachMail() throws MessagingException {
		// 获取JavaMailSender bean
		JavaMailSender sender = (JavaMailSender) ctx.getBean("javaMailSender");
		JavaMailSenderImpl senderImpl = new JavaMailSenderImpl();
		MimeMessage mailMessage = senderImpl.createMimeMessage();
		// 设置utf-8或GBK编码，否则邮件会有乱码
		MimeMessageHelper messageHelper = new MimeMessageHelper(mailMessage,
				true, "utf-8");
		try {
			messageHelper.setTo("xupeijun@alamt.com");// 接受者
			messageHelper.setFrom("yili@aldmt.com");// 发送者
			messageHelper.setSubject("测试邮件");// 主题
			// 邮件内容，注意加参数true
			messageHelper.setText(
							"<a href='http://www.fushionbaby.com/'>hello fushionbaby</a>",
							true);
			// 附件内容
			messageHelper.addInline("a", new File("D:/QMDownload/zhuceye.jpg"));
//			messageHelper.addInline("b", new File("E:/2.png"));
//			File file = new File("E:/测试中文文件.rar");
// 			这里的方法调用和插入图片是不同的，使用MimeUtility.encodeWord()来解决附件名称的中文问题
//			messageHelper.addAttachment(MimeUtility.encodeWord(file.getName()),file);
			sender.send(mailMessage);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}

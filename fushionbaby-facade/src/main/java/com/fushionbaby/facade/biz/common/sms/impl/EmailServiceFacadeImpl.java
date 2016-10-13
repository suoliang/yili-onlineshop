package com.fushionbaby.facade.biz.common.sms.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fushionbaby.facade.biz.common.sms.EmailServiceFacade;
import com.fushionbaby.sms.model.Email;
import com.fushionbaby.sms.service.EmailService;

/***
 * 
 * @description 类描述...
 * @author 徐培峻
 * @date 2015年8月14日下午1:11:16
 */
@Service
public class EmailServiceFacadeImpl implements EmailServiceFacade {

	@Autowired
	private EmailService<Email> emailService;
	public void add(Email email) {
		emailService.add(email);
	}

}

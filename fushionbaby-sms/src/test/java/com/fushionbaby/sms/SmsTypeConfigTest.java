package com.fushionbaby.sms;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.fushionbaby.common.constants.MyTestConstant;
import com.fushionbaby.sms.model.SmsTypeConfig;
import com.fushionbaby.sms.service.SmsTypeConfigService;
/**
 * 
 * @author King
 *
 */
public class SmsTypeConfigTest {

	private SmsTypeConfigService smsTypeService;
	
	@Before
	public void before(){
		@SuppressWarnings("resource")
		ApplicationContext ac = new ClassPathXmlApplicationContext(new String[]{
				MyTestConstant.TEST_SPRING_CONFIG,"classpath:conf/spring-sms-mybatis.xml"
		});
		smsTypeService = (SmsTypeConfigService) ac.getBean("smsTypeConfigServiceImpl");
	}
	
	public void add(){
		SmsTypeConfig smsType = new SmsTypeConfig();
		smsType.setId(MyTestConstant.ID);
		smsType.setSmsName("促销信息");
		smsType.setSmsTemplate("您好，欢迎您购买我们的产品，我们会为你竭诚负责到底");
		smsTypeService.add(smsType);
	}
	
	@Test
	public void testAdd() {
		this.add();
	}

	@After
	public void testDeleteById() {
		smsTypeService.deleteById(MyTestConstant.ID);
	}

	@Test
	public void testUpdate() {
		this.add();
		SmsTypeConfig smsType = (SmsTypeConfig) smsTypeService.findById(MyTestConstant.ID);
		smsType.setSmsTemplate("wobuzhdoa a好的我知道了行的");
		smsTypeService.update(smsType);
	}

	@Test
	public void testFindById() {
		this.add();
		Assert.assertNotNull(smsTypeService.findById(MyTestConstant.ID));
	}

	@Test
	public void testFindAll() {
		this.add();
		Assert.assertNotNull(smsTypeService.findAll());
	}

}

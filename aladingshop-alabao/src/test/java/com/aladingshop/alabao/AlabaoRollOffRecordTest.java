package com.aladingshop.alabao;

import java.math.BigDecimal;

import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.aladingshop.alabao.model.AlabaoRollOffRecord;
import com.aladingshop.alabao.service.AlabaoRollOffRecordService;
import com.fushionbaby.common.constants.MyTestConstant;

public class AlabaoRollOffRecordTest {
	
	private AlabaoRollOffRecordService<AlabaoRollOffRecord> alabaoRollOffRecordService;
	
	@SuppressWarnings({"resource", "unchecked"})
	@Before
	public void before() {
		ApplicationContext ac = new ClassPathXmlApplicationContext(
				new String[] { MyTestConstant.TEST_SPRING_CONFIG,
						"classpath:conf/spring-alabao-mybatis.xml" });
		alabaoRollOffRecordService = (AlabaoRollOffRecordService<AlabaoRollOffRecord>) ac.getBean("alabaoRollOffRecordServiceImpl");
	}
	
	public void add(){
		AlabaoRollOffRecord alabaoRollOffRecord = new AlabaoRollOffRecord();
		alabaoRollOffRecord.setAccount("1233");
		alabaoRollOffRecord.setIsSuccess("y");
		alabaoRollOffRecord.setMemberId(2L);
		alabaoRollOffRecord.setRollOffAccountType("1");
		alabaoRollOffRecord.setTransferMoney(new BigDecimal("234"));
		alabaoRollOffRecordService.add(alabaoRollOffRecord);
	}
	
	@Test
	public void testAdd() {
		add();
	}

}

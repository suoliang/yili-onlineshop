package com.fushionbaby.act.activity;

/**
 * 
 * @author cyla
 * 
 */
import java.util.Date;

import org.apache.commons.lang3.ObjectUtils;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.fushionbaby.act.activity.model.ActCardType;
import com.fushionbaby.act.activity.service.ActCardTypeService;
import com.fushionbaby.common.constants.CardTypeConstant;
import com.fushionbaby.common.constants.MyTestConstant;

public class ActCardTypeTest {

	private ActCardTypeService<ActCardType> sacts;

	@Before
	public void before() {
		@SuppressWarnings("resource")
		ApplicationContext context = new ClassPathXmlApplicationContext(
				new String[] { MyTestConstant.TEST_SPRING_CONFIG,
						"classpath:conf/spring-activity-mybatis.xml" });
		sacts = (ActCardTypeService<ActCardType>) context
				.getBean("actCardTypeServiceImpl");
	}

	public void add() {
		ActCardType sac = new ActCardType();
		sac.setId(MyTestConstant.ID);
		sac.setBeginTime(new Date());
		sac.setEndTime(new Date());
		sac.setName("name");
		sac.setDiscountMoney("200");
		sac.setIdList("1,2,6,88");
		sac.setCardType("通用");
		sac.setDisable("y");
		sac.setConditionSkuPriceAbove("500");
		sacts.add(sac);
	}

	@Test
	public void testAdd() {
		ActCardType cardType = sacts.findByCode(CardTypeConstant.REGISTER_SEND_ACTIVITY);
		Date now=new Date();
		System.out.println(ObjectUtils.notEqual(cardType, null)&&cardType.getBeginTime().before(now)&&cardType.getEndTime().after(now));
		this.add();
	}

	@After
	public void testDeleteById() {
		sacts.deleteById(MyTestConstant.ID);
	}
	
	@Test
	public void findById(){
		this.add();
		Assert.assertNotNull(sacts.findById(MyTestConstant.ID));
	}
	
//	@Test
//	public void findAll() {
//		this.add();
//		sacts.findAll();
//	}

	@Test
	public void testUpdate() {
		ActCardType sac = new ActCardType();
		sac.setId(MyTestConstant.ID);
		sac.setBeginTime(new Date());
		sac.setEndTime(new Date());
		sac.setDiscountMoney("200");
		sac.setIdList("1,2,6,88");
		sac.setCardType("通用");
		sac.setDisable("n");
		sacts.update(sac);
	}
	
	
	

}

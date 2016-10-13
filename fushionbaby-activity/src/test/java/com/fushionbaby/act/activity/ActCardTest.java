package com.fushionbaby.act.activity;

/**
 * 
 * @author cyla
 * 
 */
import java.util.Calendar;
import java.util.Date;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.fushionbaby.act.activity.model.ActCard;
import com.fushionbaby.act.activity.service.ActCardService;
import com.fushionbaby.common.constants.CardTypeConstant;
import com.fushionbaby.common.constants.MyTestConstant;
import com.fushionbaby.common.dto.CouponCardDto;
import com.fushionbaby.common.util.BasePagination;
import com.fushionbaby.common.util.RandomNumUtil;

public class ActCardTest {

	private ActCardService<ActCard> sacts;
	@SuppressWarnings("unchecked")
	@Before
	public void before() {
		@SuppressWarnings("resource")
		ApplicationContext context = new ClassPathXmlApplicationContext(
				new String[] { MyTestConstant.TEST_SPRING_CONFIG,
						"classpath:conf/spring-activity-mybatis.xml" });
		sacts = (ActCardService<ActCard>) context.getBean("actCardServiceImpl");
	}

	@After
	public void testDeleteById() {
		this.sacts.deleteById(MyTestConstant.ID);
	}
	public void add() {
		ActCard card=new ActCard();
		card.setCardNo(CardTypeConstant.REGISTER_SEND_CARD_PRE+RandomNumUtil.getNumber(8));
		card.setCardTypeId(100l);
		card.setMemberId(100l);
		card.setPassword(RandomNumUtil.getNumber(8));
		card.setStartTime(new Date());
		Calendar ca=Calendar.getInstance();
		ca.add(Calendar.DAY_OF_YEAR, CardTypeConstant.REGISTER_SEND_ACTIVITY_TIME);
		card.setStopTime(ca.getTime());
		card.setUseType("1");
		card.setUseCount(0);
		card.setUseStatus("1");
		sacts.add(card);
		
		
//		ActCard sac = new ActCard();
//		sac.setId(MyTestConstant.ID);
//		sac.setCardNo("54587485");
//		sac.setCardTypeId(11l);
//		sacts.add(sac);
	}

	@Test
	public void testAdd() {
		this.add();
		ActCard card=this.sacts.findByCardNoAndPassword("QX8039768567",null);
		System.out.println(card.getCardNo());
	}

	@Test
	public void testfindById() {
		this.add();
		Assert.assertNotNull(this.sacts.findById(MyTestConstant.ID));
	}

//	@Test
//	public void testfindAll() {
//		this.add();
//		Assert.assertNotNull(this.sacts.findAll());
//	}



	@Test
	public void testUpdate() {
		this.add();
		ActCard sac = this.sacts.findById(MyTestConstant.ID);
		sac.setId(MyTestConstant.ID);
		sac.setCardNo("415485");
		sac.setCardTypeId(112l);
		sac.setUsedTime(new Date());
		sacts.update(sac);

	}
	
	@Test
	public void testGetAmountByCardNo() {
		String cardNo = "fushionbaby6p0Fgi23ue";
		CouponCardDto amount = sacts.getAmountByCardNo(cardNo);
		System.out.println(amount);
	}

	@Test
	public void testgetlistpage() {
		BasePagination page = new BasePagination();
		System.out.println(this.sacts.getListPage(page).getTotal());
	}

	@Test
	public void testdeleteByTypeId() {
		this.sacts.deleteByTypeId(41l);
	}
	public void testupdateByCardNo() {

		this.sacts.updateByCardNo("fushionbaby6p0Fgi23ue");
	}

}

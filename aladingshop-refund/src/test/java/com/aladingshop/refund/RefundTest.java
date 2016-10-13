package com.aladingshop.refund;

import java.util.Date;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.aladingshop.refund.model.Refund;
import com.aladingshop.refund.service.RefundService;
import com.fushionbaby.common.constants.MyTestConstant;

public class RefundTest {

	private RefundService refundService;

	@SuppressWarnings("resource")
	@Before
	public void before(){
		ApplicationContext ac = new ClassPathXmlApplicationContext(new String[]{
				MyTestConstant.TEST_SPRING_CONFIG,"classpath:conf/spring-refund-mybatis.xml"
		});
		refundService = (RefundService) ac.getBean("refundServiceImpl");
	}
	
	@Ignore
	public void add(){
		Refund refund = new Refund();
//		refund.setId(MyTestConstant.ID);
		refund.setBatchNo(MyTestConstant.ID+"");
		refund.setCreateId(MyTestConstant.ID);
		refund.setMemberId(MyTestConstant.ID);
		refund.setOrderCode(MyTestConstant.ID+"");
		refund.setPaymentType("ZFB");
		refund.setSettleAmount("1");
		refund.setSourceCode("dd");
		refundService.add(refund);
	}
	@Test
	public void testAdd() {
		add();
	}
	
	@After
	public void testDeleteById() {
		refundService.deleteById(MyTestConstant.ID);
	}

	@Test
	public void testUpdate() {
		add();
		Refund refund = new Refund();
		refund.setBatchNo(MyTestConstant.ID+"");
		refund.setCreateId(MyTestConstant.ID);
		refund.setMemberId(MyTestConstant.ID);
		refund.setOrderCode(MyTestConstant.ID+"");
		refund.setPaymentType("ZFB");
		refund.setSettleAmount("1");
		refund.setSourceCode("dd");
		refundService.updateByBatchNo(refund);
	}


//	@Test
//	public void updateByOrderNumberAndMemberId() {
//		add();
//		Refund refund = new Refund();
//		refund.setBatchNo(MyTestConstant.ID+"");
//		refund.setCreateId(MyTestConstant.ID);
//		refund.setMemberId(MyTestConstant.ID);
//		refund.setOrderCode(MyTestConstant.ID+"");
//		refund.setPaymentType("ZFB");
//		refund.setSettleAmount("1");
//		refund.setSourceCode("dd");
//		refundService.updateByOrderNumberAndMemberId(refund);;
//	}

	@Test
	public void queryBySoCode() {
		add();
		refundService.findByBatchNo(MyTestConstant.ID+"");
	}

	@Test
	public void queryByOrderNumber() {
		add();
		refundService.findByMemberIdAndOrderCode(MyTestConstant.ID, MyTestConstant.ID+"");
	}

}

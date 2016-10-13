package com.fushionbaby.sysmgr;

import java.util.Date;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.fushionbaby.common.constants.MyTestConstant;
import com.fushionbaby.common.util.BasePagination;
import com.fushionbaby.sysmgr.model.SysmgrProductCompare;
import com.fushionbaby.sysmgr.service.SysmgrProductCompareService;
import com.fushionbaby.sysmgr.service.SysmgrRefundReasonService;

public class SysmgrProductCompareTest {
	private SysmgrProductCompareService sysmgrProductCompareService;
	@Before
	public void before() {
		@SuppressWarnings("resource")
		ApplicationContext ac = new ClassPathXmlApplicationContext(
				new String[] { "classpath:conf/spring-sysmgr-develop.xml",
						"classpath:conf/spring-sysmgr-mybatis.xml" });
		sysmgrProductCompareService = (SysmgrProductCompareService) ac
				.getBean(SysmgrProductCompareService.class);
	}


	@Test
	public void testUpdate() {
		this.add();
		
		SysmgrProductCompare sysmgrProduct = new SysmgrProductCompare();
		sysmgrProduct.setId(MyTestConstant.ID);
		sysmgrProduct.setSkuId(1112);
		sysmgrProduct.setName("11");
		sysmgrProduct.setClickNum(1000000);
		
		sysmgrProductCompareService.update(sysmgrProduct);
	}

	@After
	public void testDel() {
		sysmgrProductCompareService.deleteSysmgrProductCompareById(MyTestConstant.ID);
	}
	

	private void add() {
		SysmgrProductCompare sysmgrProduct = new SysmgrProductCompare();
		sysmgrProduct.setId(MyTestConstant.ID);
		sysmgrProduct.setName("11");
		sysmgrProduct.setSkuId(1112);
		sysmgrProduct.setClickNum(1000000);
	}

}

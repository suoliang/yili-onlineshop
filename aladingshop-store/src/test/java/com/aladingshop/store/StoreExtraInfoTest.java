package com.aladingshop.store;

import java.util.Date;

import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.aladingshop.store.model.StoreExtraInfo;
import com.aladingshop.store.service.StoreExtraInfoService;
import com.fushionbaby.common.constants.MyTestConstant;
import com.fushionbaby.common.util.BasePagination;

public class StoreExtraInfoTest {

	private StoreExtraInfoService storeExtraInfoService;

	@SuppressWarnings("resource")
	@Before
	public void before() {
		ApplicationContext ac = new ClassPathXmlApplicationContext(
				new String[] { MyTestConstant.TEST_SPRING_CONFIG,
						"classpath:conf/spring-store-mybatis.xml" });
		storeExtraInfoService = (StoreExtraInfoService) ac.getBean("storeExtraInfoServiceImpl");
	}

	
	public void add() {
		StoreExtraInfo storeExtraInfo = new StoreExtraInfo();
		storeExtraInfo.setId(MyTestConstant.ID);
		storeExtraInfo.setAddress(MyTestConstant.ID+"");
		storeExtraInfo.setDescription(MyTestConstant.ID+"");
		storeExtraInfo.setLinkMan(MyTestConstant.ID+"");
		storeExtraInfo.setMobile(MyTestConstant.ID+"");
		storeExtraInfo.setPicture(MyTestConstant.ID+"");
		storeExtraInfo.setStoreCode(MyTestConstant.ID+"");
		storeExtraInfo.setTelephone(MyTestConstant.ID+"");
		storeExtraInfo.setTrafficRoutes(MyTestConstant.ID+"");
		storeExtraInfo.setZip(MyTestConstant.ID+"");
		storeExtraInfo.setUpdateId(MyTestConstant.ID);
		storeExtraInfoService.add(storeExtraInfo);
	}

	@Test
	public void testAdd() {
		add();
	}

	@After
	public void testDeleteById() {
		storeExtraInfoService.deleteById(MyTestConstant.ID);
	}
	
	@Test
	public void testDeleteByStoreCode() {
		storeExtraInfoService.deleteByStoreCode(MyTestConstant.ID+"");;
	}

	@Test
	public void testUpdate() {
		add();
		StoreExtraInfo storeExtraInfo = new StoreExtraInfo();
		storeExtraInfo.setId(MyTestConstant.ID);
		storeExtraInfo.setAddress(MyTestConstant.ID+"");
		storeExtraInfo.setDescription(MyTestConstant.ID+"");
		storeExtraInfo.setLinkMan(MyTestConstant.ID+"");
		storeExtraInfo.setMobile(MyTestConstant.ID+"");
		storeExtraInfo.setPicture(MyTestConstant.ID+"");
		storeExtraInfo.setStoreCode(MyTestConstant.ID+"");
		storeExtraInfo.setTelephone(MyTestConstant.ID+"");
		storeExtraInfo.setTrafficRoutes(MyTestConstant.ID+"");
		storeExtraInfo.setZip(MyTestConstant.ID+"");
		storeExtraInfo.setUpdateId(MyTestConstant.ID);
		storeExtraInfoService.updateByStoreCode(storeExtraInfo);
	}


	
	@Test
	public void testFindById() {
		add();
		storeExtraInfoService.findById(MyTestConstant.ID);
	}

}

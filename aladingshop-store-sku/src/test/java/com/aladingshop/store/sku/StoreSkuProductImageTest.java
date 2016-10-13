package com.aladingshop.store.sku;


import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.aladingshop.store.sku.model.StoreSkuProductImage;
import com.aladingshop.store.sku.service.StoreSkuProductImageService;
import com.fushionbaby.common.constants.MyTestConstant;

public class StoreSkuProductImageTest {

	private StoreSkuProductImageService storeSkuProductImageService;

	@SuppressWarnings("resource")
	@Before
	public void before() {
		ApplicationContext ac = new ClassPathXmlApplicationContext(
				new String[] { MyTestConstant.TEST_SPRING_CONFIG,
						"classpath:conf/spring-store-sku-mybatis.xml" });
		storeSkuProductImageService = (StoreSkuProductImageService) ac.getBean("storeSkuProductImageServiceImpl");
	}

	
	public void add() {
		StoreSkuProductImage storeSkuProductImage = new StoreSkuProductImage();
		storeSkuProductImage.setId(MyTestConstant.ID);
		storeSkuProductImage.setProductCode(MyTestConstant.ID+"");
		storeSkuProductImage.setStoreCode(MyTestConstant.ID+"");
		storeSkuProductImage.setUpdateId(MyTestConstant.ID);
		storeSkuProductImageService.add(storeSkuProductImage);
	}

	@Test
	public void testAdd() {
		add();
	}

	@After
	public void testDeleteById() {
		storeSkuProductImageService.deleteById(MyTestConstant.ID);
	}
	
	@Test
	public void testDeleteByStoreCodeAndSkuCode() {
		storeSkuProductImageService.deleteByStoreCodeAndProductCode(MyTestConstant.ID+"", MyTestConstant.ID+"");
	}

	@Test
	public void testUpdate() {
		add();
		StoreSkuProductImage storeSkuProductImage = new StoreSkuProductImage();
		storeSkuProductImage.setId(MyTestConstant.ID);
		storeSkuProductImage.setProductCode(MyTestConstant.ID+"");
		storeSkuProductImage.setStoreCode(MyTestConstant.ID+"");
		storeSkuProductImage.setUpdateId(MyTestConstant.ID);
		storeSkuProductImageService.updateById(storeSkuProductImage);
	}


	
	@Test
	public void testFindById() {
		add();
		storeSkuProductImageService.findById(MyTestConstant.ID);
	}

}

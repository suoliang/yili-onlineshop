package com.fushionbaby.sku;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.fushionbaby.common.constants.MyTestConstant;
import com.fushionbaby.common.util.BasePagination;
import com.fushionbaby.sku.model.SkuImageTypeConfig;
import com.fushionbaby.sku.service.SkuImageTypeConfigService;

/**
 * 
 * @author xupeijun
 * 
 */
public class SkuImageTypeConfigTest {
	@Autowired
	private SkuImageTypeConfigService<SkuImageTypeConfig> skuImageTypeService;

	@SuppressWarnings("unchecked")
	@Before
	public void before() {
		@SuppressWarnings("resource")
		ApplicationContext ac = new ClassPathXmlApplicationContext(
				new String[] { MyTestConstant.TEST_SPRING_CONFIG,
						"classpath:conf/spring-sku-mybatis.xml" });
		skuImageTypeService = (SkuImageTypeConfigService) ac
				.getBean("skuImageTypeConfigServiceImpl");
	}

	@After
	public void testdeleteById() {
		this.skuImageTypeService.deleteById(MyTestConstant.ID);
	}

	@Test
	public void testadd() {
		this.add();
	}

	@Test
	public void testupdate() {
		this.add();
		SkuImageTypeConfig skuImageType = this.skuImageTypeService
				.findById(MyTestConstant.ID);
		skuImageType.setCode("654321789");
		skuImageType.setName("testupdate");
		skuImageType.setSuffix("jpeg");
	}

	@Test
	public void testfindAll() {
		this.add();
		Assert.assertNotNull(skuImageTypeService.findAll());
	}

	@Test
	public void testListPage() {
		this.add();
		BasePagination page = new BasePagination();
		Assert.assertNotNull(this.skuImageTypeService.getListPage(page));
	}

	@Test
	public void testfindBySuffix() {
		this.add();
		Assert.assertNotNull(this.skuImageTypeService.findBySuffix("_a_h_n_l_"));
	}

	public void add() {
		SkuImageTypeConfig skuImageType = new SkuImageTypeConfig();
		skuImageType.setCode("9876543321");
		skuImageType.setId(MyTestConstant.ID);
		skuImageType.setName("test1");
		skuImageType.setSuffix("png");

		this.skuImageTypeService.add(skuImageType);
	}

}

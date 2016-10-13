package com.aladingshop.sku.cms;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.aladingshop.sku.cms.model.SkuCardRelation;
import com.aladingshop.sku.cms.service.SkuCardRelationService;
import com.fushionbaby.common.constants.MyTestConstant;

/***
 * 商品储蓄卡关联
 * @description 类描述...
 * @author 徐培峻
 * @date 2015年8月6日下午1:14:12
 */
public class SkuCardRelationTest {

	
    private SkuCardRelationService<SkuCardRelation> skuCardRelationService;

	@SuppressWarnings("unchecked")
	@Before
	public void before() {
		@SuppressWarnings("resource")
		ApplicationContext ac = new ClassPathXmlApplicationContext(
				new String[] { MyTestConstant.TEST_SPRING_CONFIG,
						"classpath:conf/spring-sku-cms-mybatis.xml" });
		skuCardRelationService = (SkuCardRelationService<SkuCardRelation>) ac.getBean("skuCardRelationServiceImpl");
	}
	@After
	public void testdeleteById() {

		this.skuCardRelationService.deleteById(MyTestConstant.ID);
	}

	@Test
	public void testadd() {
		this.add();
	}
	
	@Test
	public void testupdate() {
		this.add();
		SkuCardRelation skuImage = this.skuCardRelationService.findById(MyTestConstant.ID);
		skuImage.setCardCode("123456");
		this.skuCardRelationService.update(skuImage);

	}


	@Test
	public void testfindById() {
		this.add();
		Assert.assertNotNull(this.skuCardRelationService.findById(MyTestConstant.ID));
	}




	public void add() {
		SkuCardRelation	image=new SkuCardRelation();
		image.setCardCode("66666666666");
		image.setSkuCode("11111111");
		image.setId(MyTestConstant.ID);
		this.skuCardRelationService.add(image);
	}


}

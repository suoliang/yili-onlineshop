package com.aladingshop.sku.cms;

import java.math.BigDecimal;
import java.util.Date;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.aladingshop.sku.cms.model.SkuPromotionsSku;
import com.aladingshop.sku.cms.service.SkuPromotionsSkuService;
import com.fushionbaby.common.constants.MyTestConstant;
import com.fushionbaby.common.util.BasePagination;


public class SkuPromotionsSkuTest {

	
    private SkuPromotionsSkuService<SkuPromotionsSku> skuPromotionsSkuService;

	@SuppressWarnings("unchecked")
	@Before
	public void before() {
		@SuppressWarnings("resource")
		ApplicationContext ac = new ClassPathXmlApplicationContext(
				new String[] { MyTestConstant.TEST_SPRING_CONFIG,
						"classpath:conf/spring-sku-cms-mybatis.xml" });
		skuPromotionsSkuService = (SkuPromotionsSkuService<SkuPromotionsSku>) ac.getBean("skuPromotionsSkuServiceImpl");
	}
	@After
	public void testdeleteById() {

		this.skuPromotionsSkuService.deleteById(MyTestConstant.ID);
	}

	@Test
	public void testdelete() {
		this.add();
		this.skuPromotionsSkuService.deleteByPmCodeAndSkuCode(MyTestConstant.ID+"", MyTestConstant.ID+"");
		this.add();
	}
	
	@Test
	public void testadd() {
		this.add();
	}
	
	@Test
	public void testupdate() {
		this.add();
		SkuPromotionsSku skuPromotionsSku = this.skuPromotionsSkuService.findById(MyTestConstant.ID);
		skuPromotionsSku.setCreateId(MyTestConstant.ID);
		skuPromotionsSku.setCreateTime(new Date());
		skuPromotionsSku.setId(MyTestConstant.ID);
		skuPromotionsSku.setPmCode(MyTestConstant.ID+"");
		skuPromotionsSku.setSkuCode(MyTestConstant.ID+"");
		skuPromotionsSku.setSkuPromotionsStatus("y");
		skuPromotionsSku.setSort(3L);
		skuPromotionsSku.setSpecialPrice(BigDecimal.valueOf(12.5));
		skuPromotionsSku.setLimitCount(12);
		this.skuPromotionsSkuService.updateById(skuPromotionsSku);
		this.skuPromotionsSkuService.updateByPmCodeAndSkuCode(skuPromotionsSku);

	}


	@Test
	public void testfind() {
		this.add();
		Assert.assertNotNull(this.skuPromotionsSkuService.findById(MyTestConstant.ID));
		Assert.assertNotNull(this.skuPromotionsSkuService.findByPmCodeAndSkuCode(MyTestConstant.ID+"",MyTestConstant.ID+""));
		Assert.assertNotNull(this.skuPromotionsSkuService.getListPage(new BasePagination()));
	}




	public void add() {
		SkuPromotionsSku	skuPromotionsSku=new SkuPromotionsSku();
		skuPromotionsSku.setCreateId(MyTestConstant.ID);
		skuPromotionsSku.setCreateTime(new Date());
		skuPromotionsSku.setId(MyTestConstant.ID);
		skuPromotionsSku.setPmCode(MyTestConstant.ID+"");
		skuPromotionsSku.setSkuCode(MyTestConstant.ID+"");
		skuPromotionsSku.setSkuPromotionsStatus("y");
		skuPromotionsSku.setSort(3L);
		skuPromotionsSku.setSpecialPrice(BigDecimal.valueOf(12.5));
		skuPromotionsSku.setLimitCount(12);
		this.skuPromotionsSkuService.add(skuPromotionsSku);
	}


}

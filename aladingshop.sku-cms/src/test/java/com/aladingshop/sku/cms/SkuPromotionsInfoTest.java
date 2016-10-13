package com.aladingshop.sku.cms;

import java.math.BigDecimal;
import java.util.Date;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.aladingshop.sku.cms.model.SkuPromotionsInfo;
import com.aladingshop.sku.cms.service.SkuPromotionsInfoService;
import com.fushionbaby.common.constants.MyTestConstant;
import com.fushionbaby.common.util.BasePagination;


public class SkuPromotionsInfoTest {

	
    private SkuPromotionsInfoService<SkuPromotionsInfo> skuPromotionsInfoService;

	@SuppressWarnings("unchecked")
	@Before
	public void before() {
		@SuppressWarnings("resource")
		ApplicationContext ac = new ClassPathXmlApplicationContext(
				new String[] { MyTestConstant.TEST_SPRING_CONFIG,
						"classpath:conf/spring-sku-cms-mybatis.xml" });
		skuPromotionsInfoService = (SkuPromotionsInfoService<SkuPromotionsInfo>) ac.getBean("skuPromotionsInfoServiceImpl");
	}
	@After
	public void testdeleteById() {

		this.skuPromotionsInfoService.deleteByPmid(MyTestConstant.ID);
	}

	@Test
	public void testadd() {
		this.add();
	}
	
	@Test
	public void testupdate() {
		this.add();
		SkuPromotionsInfo skuPromotionsInfo = this.skuPromotionsInfoService.findByPmid(MyTestConstant.ID);
		skuPromotionsInfo.setCreateId(MyTestConstant.ID);
		skuPromotionsInfo.setCreateTime(new Date());
		skuPromotionsInfo.setEndDate(new Date());
		skuPromotionsInfo.setIsRepeatBuy("y");
		skuPromotionsInfo.setPromotionsCode(MyTestConstant.ID+"");
		skuPromotionsInfo.setPmid(MyTestConstant.ID);
		skuPromotionsInfo.setPromotionsName(MyTestConstant.ID+"");
		skuPromotionsInfo.setSalesPrice(BigDecimal.valueOf(12.5));
		skuPromotionsInfo.setStartDate(new Date());
		skuPromotionsInfo.setTimeRange("08-12");
		skuPromotionsInfo.setUpdateId(MyTestConstant.ID);
		this.skuPromotionsInfoService.updateByPmid(skuPromotionsInfo);
		this.skuPromotionsInfoService.updateByPromotionsCode(skuPromotionsInfo);

	}


	@Test
	public void testfind() {
		this.add();
		Assert.assertNotNull(this.skuPromotionsInfoService.findByPmid(MyTestConstant.ID));
		Assert.assertNotNull(this.skuPromotionsInfoService.findByPromotionsCode(MyTestConstant.ID+""));
		Assert.assertNotNull(this.skuPromotionsInfoService.getListPage(new BasePagination()));
	}




	public void add() {
		SkuPromotionsInfo	skuPromotionsInfo=new SkuPromotionsInfo();
		skuPromotionsInfo.setCreateId(MyTestConstant.ID);
		skuPromotionsInfo.setCreateTime(new Date());
		skuPromotionsInfo.setEndDate(new Date());
		skuPromotionsInfo.setIsRepeatBuy("y");
		skuPromotionsInfo.setPromotionsCode(MyTestConstant.ID+"");
		skuPromotionsInfo.setPmid(MyTestConstant.ID);
		skuPromotionsInfo.setPromotionsName(MyTestConstant.ID+"");
		skuPromotionsInfo.setSalesPrice(BigDecimal.valueOf(12.5));
		skuPromotionsInfo.setStartDate(new Date());
		skuPromotionsInfo.setTimeRange("08-12");
		skuPromotionsInfo.setUpdateId(MyTestConstant.ID);
		this.skuPromotionsInfoService.add(skuPromotionsInfo);
	}


}

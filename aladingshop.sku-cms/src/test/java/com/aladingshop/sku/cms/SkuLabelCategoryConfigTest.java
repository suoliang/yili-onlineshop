/**
 * 
 */
package com.aladingshop.sku.cms;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.aladingshop.sku.cms.model.SkuLabelCategoryRelation;
import com.aladingshop.sku.cms.service.SkuLabelCategoryRelationService;
import com.fushionbaby.common.constants.MyTestConstant;

/**
 * @description 类描述...
 * @author 索亮
 * @date 2015年7月27日下午7:48:35
 */
public class SkuLabelCategoryConfigTest {
	
	private SkuLabelCategoryRelationService<SkuLabelCategoryRelation> lcc;

	@SuppressWarnings("unchecked")
	@Before
	public void before() {
		@SuppressWarnings("resource")
		ApplicationContext context = new ClassPathXmlApplicationContext(
				new String[] { MyTestConstant.TEST_SPRING_CONFIG,
						"classpath:conf/spring-sku-cms-mybatis.xml" });
		lcc = (SkuLabelCategoryRelationService<SkuLabelCategoryRelation>) context
				.getBean("skuLabelCategoryRelationServiceImpl");
	}

	public void add() {
		SkuLabelCategoryRelation lc = new SkuLabelCategoryRelation();
		lc.setId(MyTestConstant.ID);
		lc.setLabelCode("dd");
		lc.setCategoryCode("aa,bb,dd");
		lc.setDisable("y");
		lcc.add(lc);
	}
	
	@Test
	public void testAdd() {
		this.add();
	}
	
	@Test
	public void testFindObjectById() {
		Assert.assertNotNull(lcc.findObjectById(MyTestConstant.ID));
	}
	
	@Test
	public void testFindObjectByLabelCode() {
		Assert.assertNotNull(lcc.findListByLabelCode("dd"));
	}
	
	@Test
	public void testUpdate() {
		SkuLabelCategoryRelation lc = new SkuLabelCategoryRelation();
		lc.setId(MyTestConstant.ID);
		lc.setLabelCode("ddd");
		lc.setCategoryCode("aa,bb,dd,cc,ee,rr");
		lc.setDisable("n");
		lcc.update(lc);
	}

}

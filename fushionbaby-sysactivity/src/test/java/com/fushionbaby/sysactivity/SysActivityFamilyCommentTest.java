package com.fushionbaby.sysactivity;

import java.util.Date;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.fushionbaby.common.constants.MyTestConstant;
import com.fushionbaby.sysactivity.condition.SysActivityFamilyCommentQueryCondition;
import com.fushionbaby.sysactivity.model.SysActivityFamilyComment;
import com.fushionbaby.sysactivity.service.SysActivityFamilyCommentService;

/**
 * 
 * @author xupeijun
 * 
 */
public class SysActivityFamilyCommentTest {

	private SysActivityFamilyCommentService<SysActivityFamilyComment> sacs;

	private SysActivityFamilyComment sac;

	@After
	public void testDeleteById() {

		sacs.deleteById(MyTestConstant.ID);

	}

	@Before
	public void before() {
		@SuppressWarnings("resource")
		ApplicationContext ac = new ClassPathXmlApplicationContext(
				new String[] { MyTestConstant.TEST_SPRING_CONFIG,
						"classpath:conf/spring-sysactivity-mybatis.xml" });
		sacs = (SysActivityFamilyCommentService<SysActivityFamilyComment>) ac
				.getBean("sysActivityFamilyCommentServiceImpl");
	}

	@Test
	public void testAdd() {
		this.add();
	}

	@Test
	public void testUpdate() {
		this.add();
		sac = new SysActivityFamilyComment();
		sac.setArticleId(MyTestConstant.ID);
		sac.setCommentContent("我知道你很难过");
		sac.setCommentNickname("昵称");
		sac.setCommentTime(new Date());
		sacs.update(sac);
	}

	@Test
	public void testFindById() {
		this.add();
		Assert.assertNotNull(sacs.findById(MyTestConstant.ID));
	}

	@Test
	public void testFindAll() {
		this.add();
		Assert.assertNotNull(sacs.findAll().size());
	}

	@Test
	public void testpagelist() {
		this.add();
		SysActivityFamilyCommentQueryCondition queryCondition = new SysActivityFamilyCommentQueryCondition();
		Assert.assertNotNull(sacs.getListPage(queryCondition).getTotal());
	}

	@Test
	public void testGetListPageType() {
		this.add();
		Assert.assertNotNull(sacs.getListPageType(MyTestConstant.ID, 1, 5));
	}

	@Test
	public void deleteByArticleId() {
		this.sacs.deleteByArticleId(5l);
	}

	private void add() {
		sac = new SysActivityFamilyComment();
		sac.setId(MyTestConstant.ID);
		sac.setArticleId(11l);
		sac.setMemberId(MyTestConstant.ID);
		sac.setCommentContent("我知道你很难过");
		sac.setCommentNickname("昵称");
		sac.setCommentTime(new Date());
		sacs.add(sac);
	}
}

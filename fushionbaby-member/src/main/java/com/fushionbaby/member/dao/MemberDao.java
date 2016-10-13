package com.fushionbaby.member.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.fushionbaby.member.model.Member;
/**
 * 
 * @author King
 *
 */
public interface MemberDao {
	
	/**
	 * 通过用户名和密码查询出对象--登录
	 * @param loginName
	 * @param password
	 * @return
	 */
	Member loginByLoginNamePassword(
			@Param("loginName") String loginName, @Param("password")String password);
	

	void add(Member member);

	void deleteById(Long id);

	void update(Member member);
	
	/***
	 * 修改会员状态--禁用，解禁
	 * @param member
	 */
	void updateMemberDisable(Member member);
	
	/***
	 * 修改密码 -- 手机
	 */
	void updatePassword(@Param("loginName")String loginName, @Param("password")String password);
	
	/***
	 * 通过用户登录名获取会员用户对象
	 * @param loginName
	 * @return
	 */
	Member findByLoginName(String loginName);
	
	Member findById(Long id);
	
	List<Member> findAll();
	
	/**
	 * 分页查询
	 * @param map
	 * @return
	 */
	List<Member> getListPage(Map<String, Object> map);
	
	/**
	 *  分页查询的总记录数
	 * @param map
	 * @return
	 */
	Integer getTotal(Map<String, Object> map);

	/***
	 * 判断该用户是否登陆过web网站
	 * 
	 * @param openid
	 * @return
	 */
	Member findByOpenId(String openid);
	
	Map<String, Object> count(Map<String, Object> map);
	
	void updateDefaultAddressIdByMemberId(@Param("id") Long id, @Param("defaultAddressId") Long defaultAddressId);


	List<Member> findBySignId(String signId);

}
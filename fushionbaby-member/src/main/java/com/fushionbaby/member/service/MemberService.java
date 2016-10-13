package com.fushionbaby.member.service;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.dao.DataAccessException;

import com.fushionbaby.common.service.BaseService;
import com.fushionbaby.common.util.BasePagination;
import com.fushionbaby.member.model.Member;

/**
 * 
 * @author King
 * 
 */
public interface MemberService<T extends Member> extends BaseService<T> {

	/**
	 * 通过用户名和密码查询出对象--登录
	 * @param loginName
	 * @param password
	 * @return
	 */
	Member loginByLoginNamePassword(String loginName,String password);
	
	/***
	 * 修改会员状态
	 * @param id
	 * @param disable
	 */
	void updateMemberDisable(Long id,String disable);
	
	/***
	 * 忘记密码-设置新密码
	 * @param loginName
	 * @param password
	 */
	void updatePassword(String loginName, String password);
	
	/***
	 * 通过用户登录名获取会员用户对象
	 * @param loginName
	 * @return
	 */
	Member findByLoginName(String loginName);
	
	/**
	 * 
	 * @param page
	 *            分页
	 * @return 结果
	 * @throws DataAccessException
	 */
	BasePagination getListPage(BasePagination page) throws DataAccessException;
	
	/***
	 * app和web公用的用户注册时的信息添加
	 * @param member
	 * @param telephoneOrEmail
	 * @param password
	 * @param source
	 * @param request
	 * @return
	 */
	Member addMember(String telephoneOrEmail,String password,String source,HttpServletRequest request);

	/***
	 * 验证该第三方的用户是否已经登过web网站
	 * 
	 * @param openid
	 * @return
	 */
	Member findByOpenId(String openid);

	/***
	 * 通过传入的openid和来源code存入数据库，获得该member的id
	 * 
	 * @param openId
	 * @param ip
	 * @param
	 * @return
	 */
	Long addLoginMember(String openId, String source, String channelCode,
			String ip);
	
	Long addLoginMember(String openId, String source, String channelCode);
	
	Integer[] queryMemberWallet(Long memberId);

	List<Member> getListPage(Map<String, Object> map);

	public Map<String, Object> findByCreateTime(Date createTime) throws DataAccessException;
	/***
	 * 修改会员默认收货地址
	 * @param memberId
	 * @param defaultAddressId
	 */
	void updateDefaultAddressIdByMemberId(Long memberId, Long defaultAddressId);

	/***
	 * 通过设备标志查询注册记录
	 * 
	 * @param signId
	 * @return
	 */
	List<Member> findBySignId(String signId);
}

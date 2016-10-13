package com.fushionbaby.facade.biz.common.member;

import com.fushionbaby.common.dto.UserDto;
import com.fushionbaby.member.model.Member;

/***
 * 
 * @description 类描述...
 * @author 徐培峻
 * @date 2015年8月4日下午5:31:48
 */
public interface MemberFacade {

	/***
	 * 用户通过用户名和密码登陆
	 * @param userName
	 * @param password
	 * @return
	 */
	Member loginByLoginNamePassword(String userName, String password);

	/***
	 * 通过登陆的用户名查找用户
	 * @param userName
	 * @return
	 */
	Member findByUserName(String userName);

	/***
	 * 添加
	 */
	void add(Member member);

	/***
	 * 设置登陆用户的信息
	 * @param sid
	 * @param member
	 * @return
	 */
	UserDto setLoginUserInfo(String sid, Member member);
	/***
	 * 第三方用户查找
	 * @param openId
	 * @return
	 */
	Member findByOpenId(String openId);

	/***
	 * 保存第三方登陆用户信息
	 * @param openId
	 * @param source
	 * @param chanel
	 * @param ipAddr
	 * @return
	 */
	Long addLoginMember(String openId, String source, String chanel,String ipAddr);

	/***
	 * 通过id获得
	 * @param memberId
	 * @return
	 */
	Member findById(Long memberId);

	/***
	 * 更换密码
	 * @param userName
	 * @param password
	 */
	void updatePassword(String userName, String password);
	/***
	 * 通过注册设备号查询
	 * @param signId
	 * @return
	 */
//	String findBySignId(String signId);
	
}

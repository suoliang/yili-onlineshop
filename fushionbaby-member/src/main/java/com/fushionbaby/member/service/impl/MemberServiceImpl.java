package com.fushionbaby.member.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.fushionbaby.common.constants.AppConstant;
import com.fushionbaby.common.constants.ChannelConstant;
import com.fushionbaby.common.constants.CommonConstant;
import com.fushionbaby.common.constants.SourceConstant;
import com.fushionbaby.common.security.MD5Util;
import com.fushionbaby.common.util.BasePagination;
import com.fushionbaby.common.util.GetIpAddress;
import com.fushionbaby.member.dao.MemberAddressDao;
import com.fushionbaby.member.dao.MemberAreaConfigDao;
import com.fushionbaby.member.dao.MemberDao;
import com.fushionbaby.member.model.Member;
import com.fushionbaby.member.service.MemberService;

@Service
public class MemberServiceImpl implements MemberService<Member> {

	@Autowired
	private MemberDao memberDao;
	@Autowired
	private MemberAddressDao memberAddressDao;
	@Autowired
	private MemberAreaConfigDao sysAreaDao;
	
	/**
	 * 通过用户名和密码查询出用户对象--登录
	 */
	public Member loginByLoginNamePassword(String loginName, String password) {
		return memberDao.loginByLoginNamePassword(loginName, password);
	}

	//
	public void add(Member member) throws DataAccessException {
		memberDao.add(member);
	}

	public void deleteById(Long id) throws DataAccessException {
		memberDao.deleteById(id);
	}

	public void update(Member member) throws DataAccessException {
		memberDao.update(member);
	}

	/***
	 * 修改会员用户状态
	 */
	public void updateMemberDisable(Long id, String disable) {
		Member member = new Member();
		member.setId(id);
		member.setDisable(disable);
		memberDao.updateMemberDisable(member);
	}

	/** 修改会员密码 */
	public void updatePassword(String loginName, String password) {
		memberDao.updatePassword(loginName,password);
	}

	/**
	 * 通过登录名获取用户对象
	 */
	public Member findByLoginName(String loginName) {
		return memberDao.findByLoginName(loginName);
	}

	public Member findById(Long id) throws DataAccessException {
		return memberDao.findById(id);
	}

	// 分页
	public BasePagination getListPage(BasePagination page)
			throws DataAccessException {
		Integer total = memberDao.getTotal(page.getSearchParamsMap());
		page.setCurrentTotal(total);
		if (total > 0) {
			List<Member> list = memberDao
					.getListPage(page.getSearchParamsMap());
			page.setResult(list);
		} else {
			page.setResult(new ArrayList<Member>());
		}
		return page;
	}

	public Member findByOpenId(String openid) {
		return this.memberDao.findByOpenId(openid);
	}

	public Long addLoginMember(String openId, String source,
			String channelCode, String ip) {
		Member member = new Member();
		member.setOpenId(openId);
		member.setSourceCode(source);
		member.setChannelCode(channelCode);
		member.setIpAddress(ip);
		member.setAvailableMoney(new BigDecimal(0));
		member.setWalletMoney(new BigDecimal(0));

		String name = setOtherLoginUserNameByChanel(channelCode);
		member.setLoginName(name);
		member.setPassword(MD5Util.MD5("abc123"));

		memberDao.add(member);
		return member.getId();
	}

	private String setOtherLoginUserNameByChanel(String channelCode) {
		String name = "unknown";
		if (ChannelConstant.QQ_CHANNEL.equals(channelCode)) {
			name = "qq";
		}
		if (ChannelConstant.SINA_CHANNEL.equals(channelCode)) {
			name = "sina";
		}
		if (ChannelConstant.WX_CHANNEL.equals(channelCode)) {
			name = "weixin";
		}
		if (ChannelConstant.ZFB_CHANNEL.equals(channelCode)) {
			name = "zfb";
		}
		return name;
	}

	public Long addLoginMember(String openId, String source, String channelCode) {
		Member member = new Member();
		member.setOpenId(openId);
		member.setSourceCode(source);
		member.setChannelCode(channelCode);
		member.setAvailableMoney(new BigDecimal(0));
		member.setWalletMoney(new BigDecimal(0));

		String name = setOtherLoginUserNameByChanel(channelCode);
		member.setLoginName(name);
		member.setPassword(MD5Util.MD5("abc123"));

		memberDao.add(member);
		return member.getId();
	}

	/***
	 * app和web公用的用户注册时的信息添加
	 * 
	 * @param member
	 * @param telephoneOrEmail
	 * @param password
	 * @param source
	 * @param request
	 * @return
	 */
	public Member addMember(String telephoneOrEmail, String password,
			String source, HttpServletRequest request) {
		Member member = new Member();
		member.setLoginName(telephoneOrEmail);
		Pattern p = Pattern
				.compile("([a-zA-Z0-9]+[_|\\_|\\.]?)*[a-zA-Z0-9]+@([a-zA-Z0-9]+[_|\\_|\\.]?)*[a-zA-Z0-9]+\\.[a-zA-Z]{2,}");
		Matcher m = p.matcher(telephoneOrEmail);
		if (m.find()) {
			member.setEmail(telephoneOrEmail);
		} else {
			member.setTelephone(telephoneOrEmail);
		}
		member.setPassword(MD5Util.MD5(password));
		member.setSourceCode(source);// 注册来源
		member.setChannelCode(ChannelConstant.DEFAULT_CHANNEL);// 默认渠道
		if (StringUtils.equalsIgnoreCase(source, SourceConstant.WEB_CODE)
				|| StringUtils
						.equalsIgnoreCase(source, SourceConstant.CMS_CODE)) {
			member.setIpAddress(GetIpAddress.getIpAddr(request));// 用户注册的ip地址
		} else {
			member.setIpAddress(AppConstant.MOBILEPHONE_IP_ADDRESS);
		}
		member.setAvailableMoney(new BigDecimal(0));
		member.setWalletMoney(new BigDecimal(0));
		member.setEpoints(new BigDecimal(0));
		member.setIsBind(CommonConstant.YES);
		memberDao.add(member);
		return member;
	}

	public Integer[] queryMemberWallet(Long memberId) {
		Integer[] money = new Integer[3];
		Member member = memberDao.findById(memberId);
		money[0] = member.getWalletMoney().intValue();
		money[1] = member.getAvailableMoney().intValue();
		money[2] = member.getEpoints().intValue();
		return money;
	}

	public List<Member> getListPage(Map<String, Object> map) {
		return memberDao.getListPage(map);
	}

	public Map<String, Object> findByCreateTime(Date createTime)
			throws DataAccessException {

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("createTime", createTime);
		return memberDao.count(map);
	}

	public void updateDefaultAddressIdByMemberId(Long memberId, Long defaultAddressId) {
		memberDao.updateDefaultAddressIdByMemberId(memberId, defaultAddressId);
	}

	public List<Member> findBySignId(String signId) {
		List<Member> memberList=this.memberDao.findBySignId(signId);
		return memberList;
	}

}

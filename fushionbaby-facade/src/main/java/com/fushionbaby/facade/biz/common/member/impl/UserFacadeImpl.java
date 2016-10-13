package com.fushionbaby.facade.biz.common.member.impl;

import java.math.BigDecimal;
import java.util.List;

import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fushionbaby.cache.SessionCache;
import com.fushionbaby.common.dto.UserDto;
import com.fushionbaby.common.util.CheckObjectIsNull;
import com.fushionbaby.core.model.OrderBaseUser;
import com.fushionbaby.core.service.OrderBaseUserService;
import com.fushionbaby.facade.biz.common.member.UserFacade;
import com.fushionbaby.member.model.Member;
import com.fushionbaby.member.service.MemberService;

/**
 * 
 * @author King 索亮
 *
 */
@Service
public class UserFacadeImpl implements UserFacade {
	@Autowired
	private OrderBaseUserService<OrderBaseUser> orderBaseUserService;
	@Autowired
	private MemberService<Member> memberService;

	public boolean checkUserLogin(String sid) {
		if (StringUtils.isBlank(sid)) {
			return false;
		}
		UserDto user = getLatestUserBySid(sid);
		if (user == null) {
			return false;
		}
		return true;
	}

	public UserDto getLatestUserBySid(String sid) {
		UserDto user = (UserDto) SessionCache.get(sid);
		if (ObjectUtils.equals(null, user)) {
			return null;
		}
		Long memberId = user.getMemberId();
		Member member = memberService.findById(memberId);
		if (ObjectUtils.equals(null, member)) {
			return null;
		}
		BigDecimal epoints = member.getEpoints()==null ? BigDecimal.ZERO : member.getEpoints();
		user.setEpoints(epoints);
		return user;
	}

	public BigDecimal getCanEpoints(UserDto user) {
		if (CheckObjectIsNull.isNull(user)) {
			return BigDecimal.valueOf(0);
		}
		BigDecimal hasUsedEpints = BigDecimal.valueOf(0);
		List<OrderBaseUser> orderBaseList = orderBaseUserService.getWaitingListByMemberId(user.getMemberId());
		for (OrderBaseUser order : orderBaseList) {
			if (ObjectUtils.equals(null, order.getTotalPointUsed())) {
				continue;
			}
			hasUsedEpints =hasUsedEpints.add(order.getTotalPointUsed());
		}
		BigDecimal canEpoints = user.getEpoints().subtract(hasUsedEpints);
		if (canEpoints.compareTo(BigDecimal.valueOf(0)) ==-1) {
			canEpoints = BigDecimal.valueOf(0);
		}
		return canEpoints;
	}

}

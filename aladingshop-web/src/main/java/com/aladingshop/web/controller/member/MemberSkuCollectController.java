/**
 * 
 */
package com.aladingshop.web.controller.member;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fushionbaby.cache.SessionCache;
import com.fushionbaby.common.constants.CommonConstant;
import com.fushionbaby.common.constants.CookieConstant;
import com.fushionbaby.common.dto.UserDto;
import com.fushionbaby.common.util.CookieUtil;
import com.fushionbaby.common.util.jsonp.Jsonp_data;
import com.fushionbaby.facade.biz.common.sku.MemberSkuCollectFacade;

/**
 * @description 会员商品收藏control
 * @author 孙涛
 * @date 2015年8月17日下午9:11:30
 */
@Controller
@RequestMapping("/memberSkuCollect")
public class MemberSkuCollectController {
	@Autowired
	private MemberSkuCollectFacade memberSkuCollectFacade;

	/**
	 * 是否已加入收藏
	 * 
	 * @param request
	 * @param skuId
	 *            商品编号
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "isCollect", method = RequestMethod.POST)
	public Object getIsCollect(@RequestParam("skuCode") String skuCode, HttpServletRequest request) {
		String sid = CookieUtil.getCookieValue(request, CookieConstant.COOKIE_SID);
		if (StringUtils.isEmpty(sid)) {
			return Jsonp_data.success(CommonConstant.NO);
		}
		UserDto user = (UserDto) SessionCache.get(sid);
		if (user == null) {
			return Jsonp_data.success(CommonConstant.NO);
		}
		return Jsonp_data.success(memberSkuCollectFacade.getIsCollect(user, skuCode));
	}
}

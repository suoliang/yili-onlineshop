/**
 * 
 */
package com.fushionbaby.wap.controller.sku;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fushionbaby.cache.SessionCache;
import com.fushionbaby.common.condition.sku.SkuCollectQueryCondition;
import com.fushionbaby.common.constants.CommonConstant;
import com.fushionbaby.common.constants.CookieConstant;
import com.fushionbaby.common.constants.ImageTypeConstant;
import com.fushionbaby.common.constants.WebConstant;
import com.fushionbaby.common.dto.UserDto;
import com.fushionbaby.common.dto.sku.web.PageSetDto;
import com.fushionbaby.common.enums.SkuStatusEnum;
import com.fushionbaby.common.util.CookieUtil;
import com.fushionbaby.facade.biz.common.sku.MemberSkuCollectFacade;
import com.fushionbaby.member.model.MemberSkuCollect;
import com.fushionbaby.member.service.MemberSkuCollectService;

/**
 * @author mengshaobo
 * 
 */
@Controller
@RequestMapping("collect")
public class MemberCollectController {

	@Autowired
	private MemberSkuCollectService<MemberSkuCollect> skuCollectService;
	@Autowired
	private MemberSkuCollectFacade collectFacade;

	/**
	 * 取消收藏
	 * 
	 * @param skuId
	 * @param model
	 * @return
	 */
	@RequestMapping("drop-collect")
	public String dropCollect(@RequestParam("sku_code") String skuCode,
			@RequestParam(value="time",defaultValue="")Long time,
			HttpServletRequest request, ModelMap model) {
		String cookieValue = CookieUtil.getCookieValue(request,CookieConstant.COOKIE_SID);
		UserDto user = ObjectUtils.equals(SessionCache.get(cookieValue), null) ? null: (UserDto) SessionCache.get(cookieValue);
		if (ObjectUtils.equals(null, user)) {
			return "login";
		}
		collectFacade.dropCollectBySkuCode(skuCode, user);
		model.addAttribute("pageset", this.getPageset(user.getMember_id(),WebConstant.DEFAULT_PAGE_INDEX));
		model.addAttribute("time", new Date().getTime());
		return "person/collect";
	}

	/**
	 * 添加商品收藏
	 * 
	 * @param skuId
	 *            商品编号
	 * @return
	 */
	@ResponseBody
	@RequestMapping("add-colloct")
	public Object addSkuCollect(@RequestParam("skuCode") String skuCode,
			HttpServletRequest request) {
		String cookieValue = CookieUtil.getCookieValue(request,CookieConstant.COOKIE_SID);
		UserDto user = ObjectUtils.equals(SessionCache.get(cookieValue), null) ? null: (UserDto) SessionCache.get(cookieValue);
		return collectFacade.addSkuCollect(skuCode, user);
	}

	/**
	 * 分页查询收藏商品列表    
	 * 			2014-12-11变动-索亮
	 * @param curPage
	 *            当前页数
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping("collect")
	public String collect(
			@RequestParam(value = "cur_page", defaultValue = WebConstant.DEFAULT_PAGE_INDEX + "") Integer curPage,
			@RequestParam(value = "time",defaultValue = "")Long time,
			HttpServletRequest request,
			ModelMap model) {

		String cookieValue = CookieUtil.getCookieValue(request,CookieConstant.COOKIE_SID);
		UserDto user = (UserDto) SessionCache.get(cookieValue);
		if (ObjectUtils.equals(user, null)) {
			return "login";
		}
		PageSetDto pageSet = this.getPageset(user.getMember_id(), curPage);
		model.addAttribute("pageset", pageSet);
		model.addAttribute("time", time);
		return "person/collect";
	}

	/**
	 * 获得商品分页数据
	 * 
	 * @param memberId
	 * @param curPage
	 * @return
	 */
	private PageSetDto getPageset(Long memberId, Integer curPage) {
		SkuCollectQueryCondition queryCondition = new SkuCollectQueryCondition();
		queryCondition.setMemberId(memberId);
		queryCondition.setStart((curPage - 1) * WebConstant.COLLECT_SIZE);
		queryCondition.setLimit(WebConstant.COLLECT_SIZE);
		queryCondition.setStatus(SkuStatusEnum.SKU_STATUS_TOP.getStrVlue());
		queryCondition.setIsAttention(CommonConstant.NO);
		queryCondition.setImageType(ImageTypeConstant.IMAGE_TYPE_WEB_7);
		PageSetDto pageSet = new PageSetDto();
		pageSet.setSkuList(collectFacade.findCollect(queryCondition));
		pageSet.setAmount(skuCollectService.getTotalByCondition(queryCondition));
		pageSet.setCurPage(curPage);
		pageSet.setTotalPage((pageSet.getAmount() - 1) / WebConstant.COLLECT_SIZE + 1);
		return pageSet;
	}

}

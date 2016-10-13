/**
 * 
 */
package com.aladingshop.web.controller.card;

import java.math.BigDecimal;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.aladingshop.alabao.model.AlabaoAccount;
import com.aladingshop.alabao.service.AlabaoAccountService;
import com.aladingshop.card.dto.res.YiDuoBaoCardResDto;
import com.fushionbaby.cache.SessionCache;
import com.fushionbaby.common.constants.CookieConstant;
import com.fushionbaby.common.constants.SourceConstant;
import com.fushionbaby.common.constants.config.ImportanceConfigConstant;
import com.fushionbaby.common.dto.UserDto;
import com.fushionbaby.common.dto.alabao.AlabaoUserDto;
import com.fushionbaby.common.util.CookieUtil;
import com.fushionbaby.common.util.EmptyValidUtils;
import com.fushionbaby.common.util.NumberFormatUtil;
import com.fushionbaby.common.util.jsonp.Jsonp;
import com.fushionbaby.config.service.SysmgrImportanceConfigService;
import com.fushionbaby.core.model.OrderFeeUser;
import com.fushionbaby.core.service.OrderFeeUserService;
import com.fushionbaby.facade.biz.common.member.UserFacade;
import com.fushionbaby.facade.biz.common.yiduobao.YiDuoBaoCardFacade;

/**
 * @description 益多宝储蓄卡control
 * @author 孙涛
 * @date 2015年9月8日下午1:12:31
 */
@Controller
@RequestMapping("/card")
public class CardController {
	@Autowired
	private YiDuoBaoCardFacade yiDuoBaoCardFacade;
	@Autowired
	private UserFacade userFacade;
	@Autowired
	private OrderFeeUserService<OrderFeeUser> orderFeeUserService;
	@Autowired
	private SysmgrImportanceConfigService sysmgrImportanceConfigService;
	/**如意宝账户*/
	@Autowired
	private AlabaoAccountService<AlabaoAccount> alabaoAcountService;

	/**
	 * 生成订单并跳转到订单确认页面
	 * 
	 * @return
	 */
	@RequestMapping("createCardOrder")
	@ResponseBody
	public Object changeOrderReq(String configId, String faceValue, HttpServletRequest request) {
		String sid = CookieUtil.getCookieValue(request, CookieConstant.COOKIE_SID);
		if (StringUtils.isEmpty(sid)) {
			return Jsonp.error();
		}
		UserDto user= (UserDto) SessionCache.get(sid);
		if (user == null) {
			return Jsonp.error();
		}
		AlabaoAccount account=this.alabaoAcountService.findByMemberId(user.getMemberId());
		if(account==null){
			return Jsonp.error("您还没有注册如意宝账号，请到‘ 阿拉丁玛特 ’ 注册如意宝。");
		}
		AlabaoUserDto alabao=new AlabaoUserDto();
		alabao.setAccount(account.getAccount());
		alabao.setMemberId(account.getMemberId());
		alabao.setMemberName(account.getMemberName());
		return yiDuoBaoCardFacade.createOrder(configId, faceValue,alabao, SourceConstant.WEB_CODE);
	}

	/***
	 * 会员购卡（益多宝）记录
	 * 
	 * @param currPage
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping("/yiduobao.htm")
	public String myCollection(HttpServletRequest request, Model model) {
		String sid = CookieUtil.getCookieValue(request, CookieConstant.COOKIE_SID);
		List<YiDuoBaoCardResDto> memberCards = yiDuoBaoCardFacade.getCardListByMember(sid);
		if (EmptyValidUtils.arrayIsEmpty(memberCards)) {
			return "login/login";
		}

		model.addAttribute("cardList", memberCards);
		/** 我的收藏页面选中 */
		model.addAttribute("marker", "yiduobao");
		return "membercenter/yiduobao";
	}

	/**
	 * 去支付页
	 * 
	 * @param sid
	 * @param orderCode
	 *            订单编号
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "goPay", method = RequestMethod.POST)
	public String goPay(String sid, String payWay, String orderCode, Model model) {
		UserDto user = userFacade.getLatestUserBySid(sid);
		if (ObjectUtils.equals(null, user)) {
			return "redirect:/login/index";
		}

		OrderFeeUser orderFeeUser = orderFeeUserService.findByMIdAndOrdCode(user.getMemberId(), orderCode);
		
		if (ObjectUtils.equals(null, orderFeeUser) || ObjectUtils.equals(null, orderFeeUser.getCreateTime())) {
			return "redirect:/cart/list";
		}
		if (orderFeeUser.getTotalActual().compareTo(BigDecimal.ZERO) == 0) {
			return "redirect:/order/orderList?orderStatus=0";
		}
		String zfbPay = sysmgrImportanceConfigService.findByCode(ImportanceConfigConstant.PAYZFBJSDZURL).getValue();
		String wxPay = sysmgrImportanceConfigService.findByCode(ImportanceConfigConstant.PAYWEBWXURL).getValue();
		String ylPay = sysmgrImportanceConfigService.findByCode(ImportanceConfigConstant.PAYYLURL).getValue();

		model.addAttribute("sid", sid);
		model.addAttribute("payWay", payWay);
		model.addAttribute("zfbPay", zfbPay);
		model.addAttribute("wxPay", wxPay);
		model.addAttribute("ylPay", ylPay);
		model.addAttribute("createOrderTime",
				DateFormatUtils.format(orderFeeUser.getCreateTime(), "yyyy-MM-dd HH:mm:ss"));
		model.addAttribute("totalActual", NumberFormatUtil.numberFormat(orderFeeUser.getTotalActual()));
		model.addAttribute("orderCode", orderCode);
		model.addAttribute("epointMoney", "0.00");
		model.addAttribute("couponMoney", "0.00");
		model.addAttribute("transMoney", "0.00");
		return "cart/cart-pay";
	}

}

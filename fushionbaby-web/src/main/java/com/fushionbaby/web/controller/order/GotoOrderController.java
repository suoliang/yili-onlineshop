package com.fushionbaby.web.controller.order;
import java.math.BigDecimal;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fushionbaby.common.constants.CommonConstant.CommonMessage;
import com.fushionbaby.common.constants.CookieConstant;
import com.fushionbaby.common.constants.OrderConstant;
import com.fushionbaby.common.constants.WebConstant;
import com.fushionbaby.common.constants.config.ImportanceConfigConstant;
import com.fushionbaby.common.dto.UserDto;
import com.fushionbaby.common.dto.order.GotoOrderDto;
import com.fushionbaby.common.util.CookieUtil;
import com.fushionbaby.common.util.jsonp.Jsonp;
import com.fushionbaby.core.model.ShoppingCartSkuUser;
import com.fushionbaby.core.service.ShoppingCartSkuUserService;
import com.fushionbaby.facade.biz.common.order.GotoOrderFacade;
import com.fushionbaby.member.model.Member;
import com.fushionbaby.member.service.MemberService;
import com.fushionbaby.sku.model.Sku;
import com.fushionbaby.sku.service.FindSkuService;
import com.fushionbaby.config.service.SysmgrImportanceConfigService;
import com.fushionbaby.web.controller.GotoAndCreteOrderCommon;

@Controller
@RequestMapping("/order")
public class GotoOrderController extends GotoAndCreteOrderCommon{
	

	private static final Log LOGGER=LogFactory.getLog(GotoOrderController.class);

	@Autowired
	private ShoppingCartSkuUserService<ShoppingCartSkuUser> soShoppingCartSkuWebService;
	@Autowired
	private FindSkuService findSkuService;
	@Autowired
	private GotoOrderFacade gotoOrderFacade;
	@Autowired
	private SysmgrImportanceConfigService sysmgrImportanceConfigService;
	@Autowired
	private MemberService<Member> memberService;
	
	/**
	 * gotoOrder方法之前校验
	 * 
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping("goto_order_check")
	public Object gotoOrderCheck(HttpServletRequest request) {
		try {
			String sid = CookieUtil.getCookieValue(request, CookieConstant.COOKIE_SID);
			boolean checkUserLogin = userFacade.checkUserLogin(sid);
			if(!checkUserLogin){
				return Jsonp.noLoginError(CommonMessage.NO_LOGIN);
			}
			
			UserDto user = userFacade.getLatestUserBySid(sid);
			
			List<ShoppingCartSkuUser> cartItemList  = soShoppingCartSkuWebService.findSelectedCartSkuByMemberId(user.getMember_id());
			if (CollectionUtils.isEmpty(cartItemList)) {
				return Jsonp.paramError("亲!请选择您要购买的商品!在结算!");
			}
			Object checkCartItems = checkCartItemsSkuIsExist(cartItemList);
			if(ObjectUtils.notEqual(null , checkCartItems)){
				return checkCartItems;
			}
		} catch (Exception e) {
			LOGGER.error("生成订单之前的订单校验出错", e);
			return Jsonp.error();
		}
		return Jsonp.success();
	}

	private Object checkCartItemsSkuIsExist(List<ShoppingCartSkuUser> cartItemList) {
		for (ShoppingCartSkuUser cartSkuItem : cartItemList) {
			Sku skuEntry = findSkuService.queryBySkuCode(cartSkuItem.getSkuCode());// 根据商品code拿到对应的商品
			if (skuEntry == null) {
				// return Jsonp.paramError("亲!您购买的商品在系统没有找到!");
				return Jsonp.error();
			}
		}
		return null;
	}

	/**
	 * 为下订单页面准备数据 
	 * @param model
	 * @param request
	 * @return
	 */
	@RequestMapping("goto_order")
	public String gotoOrder(ModelMap model, HttpServletRequest request) {
		GotoOrderDto gotoOrderDto = new GotoOrderDto();
		try {
			String sid = CookieUtil.getCookieValue(request, CookieConstant.COOKIE_SID);
			this.checkUserLogin(request);
			UserDto user = userFacade.getLatestUserBySid(sid);
			this.checkCart(user);
			List<ShoppingCartSkuUser> cartItemList = soShoppingCartSkuWebService.findSelectedCartSkuByMemberId(user.getMember_id());
			this.checkCartItemsSkuIsExist(cartItemList);
			gotoOrderDto = gotoOrderFacade.initGotoOrderDto(user, cartItemList);
			Member member = memberService.findById(user.getMember_id());
			BigDecimal walletMoney = member.getWalletMoney();
			BigDecimal availableMoney = member.getAvailableMoney();
			user.setWalletMoney(ObjectUtils.equals(null, walletMoney) ? BigDecimal.valueOf(0) : walletMoney);
			user.setAvailableMoney(ObjectUtils.equals(null, availableMoney) ? BigDecimal.valueOf(0) : availableMoney);
			model.put("sid", sid);
			model.put("user", user);
			model.put("gotoOrderDto", gotoOrderDto);
			model.put("addressList", gotoOrderFacade.addressList(sid));
			model.put("payZfbJsdzUrl", sysmgrImportanceConfigService.findByCode(ImportanceConfigConstant.PAYZFBJSDZURL).getValue());
			model.put("payYlUrl", sysmgrImportanceConfigService.findByCode(ImportanceConfigConstant.PAYYLURL).getValue());
			model.put("gotoType", OrderConstant.GOTO_CART_PAYMENT);
		} catch (Exception e) {
              LOGGER.error("下订单准备数据出错", e);
		}

		return "checkout-list";
	}
	/**
	 * 优惠商品关联去支付页
	 * @param combinationId
	 * @param model
	 * @param response
	 * @param request
	 * @return
	 */
	@RequestMapping("carGroupGotoOrder")
	public String carGroupGotoOrder(@RequestParam("combinationId") Long combinationId,ModelMap model,
			HttpServletResponse response,HttpServletRequest request) {
		GotoOrderDto gotoOrderDto = new GotoOrderDto();
		try {
			String loginPage = WebConstant.DOMAIN_URL + "/login/index.do";
			String sid = CookieUtil.getCookieValue(request, CookieConstant.COOKIE_SID);
			if(StringUtils.isBlank(sid)){
				response.sendRedirect(loginPage);
				return null;
			}
			UserDto user = userFacade.getLatestUserBySid(sid);
			if(ObjectUtils.equals(null, user)){
				response.sendRedirect(loginPage);
				return null;
			}
			
			gotoOrderDto = gotoOrderFacade.initGotoOrderDto(user, combinationId);
			Member member = memberService.findById(user.getMember_id());
			BigDecimal walletMoney = member.getWalletMoney();
			BigDecimal availableMoney = member.getAvailableMoney();
			user.setWalletMoney(ObjectUtils.equals(null, walletMoney) ? BigDecimal.valueOf(0) : walletMoney);
			user.setAvailableMoney(ObjectUtils.equals(null, availableMoney) ? BigDecimal.valueOf(0) : availableMoney);
			model.put("sid", sid);
			model.put("user", user);
			model.put("gotoOrderDto", gotoOrderDto);
			model.put("addressList", gotoOrderFacade.addressList(sid));
			model.put("payZfbJsdzUrl", sysmgrImportanceConfigService.findByCode(ImportanceConfigConstant.PAYZFBJSDZURL).getValue());
			model.put("payYlUrl", sysmgrImportanceConfigService.findByCode(ImportanceConfigConstant.PAYYLURL).getValue());
			model.put("gotoType", OrderConstant.GOTO_COMBINATION_PAYMENT);
		} catch (Exception e) {
              LOGGER.error("下订单准备数据出错", e);
		}
		
		return "checkout-list";
	}
	
	
}

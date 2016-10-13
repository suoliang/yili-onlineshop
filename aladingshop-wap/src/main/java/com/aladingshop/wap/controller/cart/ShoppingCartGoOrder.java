package com.aladingshop.wap.controller.cart;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fushionbaby.common.condition.ShoppingCartBo;
import com.fushionbaby.common.constants.CommonConstant;
import com.fushionbaby.common.constants.CookieConstant;
import com.fushionbaby.common.constants.CommonConstant.CommonCode;
import com.fushionbaby.common.constants.CommonConstant.CommonMessage;
import com.fushionbaby.common.dto.UserDto;
import com.fushionbaby.common.dto.cart.CartItemDto;
import com.fushionbaby.common.dto.order.GotoOrderDto;
import com.fushionbaby.common.enums.ShoppingCartCaptChaEnum;
import com.fushionbaby.common.util.CookieUtil;
import com.fushionbaby.common.util.jsonp.Jsonp;
import com.fushionbaby.common.util.jsonp.Jsonp_data;
import com.fushionbaby.core.condition.ShoppingCartQueryCondition;
import com.fushionbaby.facade.biz.common.cart.ShoppingCartQueryFacade;
import com.fushionbaby.facade.biz.common.member.MemberAddressFacade;
import com.fushionbaby.facade.biz.common.member.UserFacade;
import com.fushionbaby.facade.biz.common.order.CreateOrderFacade;
import com.fushionbaby.facade.biz.common.order.GotoOrderFacade;
import com.fushionbaby.sku.model.Sku;
import com.fushionbaby.sku.service.SkuService;

@Controller
@RequestMapping("/order")
public class ShoppingCartGoOrder {
	@Autowired
	protected UserFacade userFacade;
	@Autowired
	private ShoppingCartQueryFacade cartQueryFacade;
	@Autowired
	private SkuService skuService;
	@Autowired
	private CreateOrderFacade createOrderFacade;
	@Autowired
	private GotoOrderFacade gotoOrderFacade;
	@Autowired
	private MemberAddressFacade memberAddressFacade;

	/**
	 * 去结算页前验证
	 * 
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "goOrderCheck", method = RequestMethod.POST)
	public Object goOrderCheck(HttpServletRequest request) {

		String sid = CookieUtil.getCookieValue(request,
				CookieConstant.COOKIE_SID);

		boolean checkUserLogin = userFacade.checkUserLogin(sid);

		if (!checkUserLogin) {
			return Jsonp.noLoginError(CommonMessage.NO_LOGIN);
		}
		UserDto user = userFacade.getLatestUserBySid(sid);
		ShoppingCartQueryCondition queryCondition = new ShoppingCartQueryCondition();
		queryCondition.setIsSelected(CommonConstant.YES);
		queryCondition.setMemberId(user.getMemberId());
		List<CartItemDto> itemList = cartQueryFacade
				.findSelectedCartSkuByMemberId(queryCondition);
		if (ObjectUtils.equals(null, itemList)) {
			String resultCode = ShoppingCartCaptChaEnum.SHOPPING_CART_SELECTED_FALSE
					.getCode();
			return Jsonp.newInstance(resultCode,
					ShoppingCartCaptChaEnum.getTitle(resultCode));
		}
		List<CartItemDto> notExistSkuList = new ArrayList<CartItemDto>();
		for (CartItemDto itemDto : itemList) {
			Sku skuEntry = skuService.queryBySkuCode(itemDto.getSkuCode());// 根据商品code拿到对应的商品
			if (skuEntry == null) {
				CartItemDto item = new CartItemDto();
				item.setSkuCode(itemDto.getSkuCode());
				item.setName(itemDto.getName());
				notExistSkuList.add(item);
			}
		}
		if (!CollectionUtils.isEmpty(notExistSkuList)
				&& notExistSkuList.size() > 0) {
			String code = ShoppingCartCaptChaEnum.SKU_NO.getCode();
			return Jsonp_data.newInstance(code, notExistSkuList,
					ShoppingCartCaptChaEnum.getTitle(code));
		}
		ShoppingCartBo shoppingCartBo = new ShoppingCartBo();
		shoppingCartBo.setUser(user);
		Jsonp checkSkuInventory = createOrderFacade.checkSkuInventory(shoppingCartBo);
		if (!StringUtils.equals(checkSkuInventory.getResponseCode(),
				CommonCode.SUCCESS_CODE)) {
			return checkSkuInventory;
		}

		return Jsonp.success();
	}

	@RequestMapping("goCartConfirm")
	public Object goOrder(HttpServletRequest request, Model model) {

		String sid = CookieUtil.getCookieValue(request,
				CookieConstant.COOKIE_SID);
		if (StringUtils.isBlank(sid)) {
			return Jsonp.noLoginError(CommonMessage.NO_LOGIN);
		}
		UserDto user = userFacade.getLatestUserBySid(sid);
		if (ObjectUtils.equals(null, user)) {
			return Jsonp.noLoginError(CommonMessage.NO_LOGIN);
		}

		GotoOrderDto gotoOrderDto = new GotoOrderDto();
		ShoppingCartBo ShoppingCartBo = new ShoppingCartBo();
		ShoppingCartBo.setUser(user);
		
		gotoOrderDto = gotoOrderFacade.initGotoOrderDto(ShoppingCartBo);

		if (CollectionUtils.isEmpty(gotoOrderDto.getOrderLineItems())
				|| gotoOrderDto.getOrderLineItems().size() <= 0) {

			return "redirect:/cart/list";
		}

		model.addAttribute("sid", sid);
		model.addAttribute("user", user);
		model.addAttribute("gotoOrderDto", gotoOrderDto);
		model.addAttribute("addressList", memberAddressFacade.addressList(sid));
		model.addAttribute("time", new Date().getTime());
		
		return "cart/cart-confirm";

	}
}

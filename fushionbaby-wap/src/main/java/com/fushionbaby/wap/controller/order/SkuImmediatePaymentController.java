package com.fushionbaby.wap.controller.order;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import util.CartUtils;

import com.fushionbaby.common.constants.CookieConstant;
import com.fushionbaby.common.constants.OrderConstant;
import com.fushionbaby.common.constants.WebConstant;
import com.fushionbaby.common.constants.config.ImportanceConfigConstant;
import com.fushionbaby.common.dto.UserDto;
import com.fushionbaby.common.dto.order.GotoOrderDto;
import com.fushionbaby.common.enums.SkuStatusEnum;
import com.fushionbaby.common.util.CookieUtil;
import com.fushionbaby.common.util.jsonp.Jsonp;
import com.fushionbaby.common.util.jsonp.Jsonp_data;
import com.fushionbaby.facade.biz.common.cart.ShoppingCartAddFacade;
import com.fushionbaby.facade.biz.common.member.UserFacade;
import com.fushionbaby.facade.biz.common.order.GotoOrderFacade;
import com.fushionbaby.facade.biz.common.order.OrderImmediatePaymentFacade;
import com.fushionbaby.member.model.Member;
import com.fushionbaby.member.service.MemberService;
import com.fushionbaby.sku.model.Sku;
import com.fushionbaby.sku.service.SkuService;
import com.fushionbaby.config.service.SysmgrImportanceConfigService;

/**
 * @author mengshaobo
 *
 */
@Controller
@RequestMapping("/immediate")
public class SkuImmediatePaymentController {
	@Autowired
	private ShoppingCartAddFacade addFacade;
	@Autowired
	private OrderImmediatePaymentFacade paymentFacade;
	@Autowired
	private SkuService<Sku> skuService;
	@Autowired
	private GotoOrderFacade gotoOrderFacade;
	@Autowired
	private MemberService<Member> memberService;
	@Autowired
	private SysmgrImportanceConfigService sysmgrImportanceConfigService;
	@Autowired
	private UserFacade userFacade;
	private static final Log logg= LogFactory.getLog(SkuImmediatePaymentController.class);

	/**
	 * 进入立即支付页面
	 * @param skuId 商品编号
	 * @param quantity 商品数量
	 * @param request
	 * @return
	 */
	@RequestMapping("paymentPage")
	public String  immediatePaymentPage(@RequestParam("skuCode") String skuCode,
			@RequestParam("quantity") String quantity,HttpServletRequest request,
			HttpServletResponse response,ModelMap model){
		GotoOrderDto gotoOrderDto = new GotoOrderDto();
		String loginPage = WebConstant.DOMAIN_URL + "/login/index.do";
		String indexPage = WebConstant.DOMAIN_URL + "/index/indexList.do";
		String sid = CookieUtil.getCookieValue(request, CookieConstant.COOKIE_SID);
		try{
			UserDto user = userFacade.getLatestUserBySid(sid);
			if(ObjectUtils.equals(null, user)){
				response.sendRedirect(loginPage);
				return null;
			}
			if (ObjectUtils.equals(null, skuCode)) {
				response.sendRedirect(loginPage);
				return null;
			}
			Object checkQuantity = CartUtils.checkQuantity(quantity);
			if (ObjectUtils.notEqual(null, checkQuantity)) {
				response.sendRedirect(indexPage);
				return null;
			}
			Integer quantityNum = Integer.valueOf(quantity);
			quantityNum = quantityNum < 1 ? 1 : quantityNum;
			
			Object checkSkuInventoryNum = addFacade.checkSkuInventoryNum(quantityNum,skuCode);
			if(checkSkuInventoryNum != null){
				response.sendRedirect(indexPage);
				return null;
			}
			Sku skuEntry = skuService.queryBySkuCode(skuCode);
			if (ObjectUtils.equals(null, skuEntry)) {
				response.sendRedirect(indexPage);
				return null;
			}
			if (!StringUtils.equals(skuEntry.getStatus(), SkuStatusEnum.SKU_STATUS_TOP.getStrVlue())) {
				response.sendRedirect(indexPage);
				return null;
			}
			gotoOrderDto = paymentFacade.immediatePayment(user, skuEntry, quantityNum);
			
			model.put("sid", sid);
		} catch (Exception e) {
			logg.error("立即支付immediatePayment方法异常:"+e);
			Jsonp.error();
		}
		model.put("gotoOrderDto", gotoOrderDto);
		model.put("addressList", gotoOrderFacade.addressList(sid));
		model.put("payZfbJsdzUrl", sysmgrImportanceConfigService.findByCode(ImportanceConfigConstant.PAYZFBJSDZURL).getValue());
		model.put("payYlUrl", sysmgrImportanceConfigService.findByCode(ImportanceConfigConstant.PAYYLURL).getValue());
		model.put("gotoType", OrderConstant.GOTO_IMMEDIATE_PAYMENT);
		
		return "checkout-list";
	}
	/**
	 * 立即购买前验证商品的可购买性，
	 * @param skuId 商品序号
	 * @param quantity 商品数量
	 * @param request
	 * @return 商品序号和 商品数量
	 */
	@ResponseBody
	@RequestMapping("immediatePayment")
	public Object  gotoImmediatePaymentPage(@RequestParam("sku_code") String skuCode,
			@RequestParam("quantity") String quantity,HttpServletRequest request){
		try{
			UserDto user = CartUtils.getUser(CartUtils.getClientSid(request));
			if (ObjectUtils.equals(null, user)) {
				return Jsonp.paramError("请您先登录！");
			}
			Object obj =  null;
			obj = CartUtils.checkSkuCode(skuCode);
			if(obj!=null){
				return obj;
			}
			obj = CartUtils.checkQuantity(quantity);
			if(obj!=null){
				return obj;
			}
			int quantityNum = CartUtils.getQuantity(quantity);
			obj = addFacade.checkSkuInventoryNum(quantityNum,skuCode);
			if(obj!=null){
				return obj;
			}
			Sku skuEntry = skuService.queryBySkuCode(skuCode);
			if (ObjectUtils.equals(null, skuEntry)) {
				return Jsonp.error("该商品已下架!");
			}
			if (!StringUtils.equals(skuEntry.getStatus(), SkuStatusEnum.SKU_STATUS_TOP.getStrVlue())) {
				return Jsonp.error("该商品已下架!");
			}
		} catch (Exception e) {
			logg.error("立即支付immediatePayment方法异常:"+e);
			Jsonp.error();
		}
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("skuCode", skuCode);
		map.put("quantity", quantity);
		return Jsonp_data.success(map);
	}
	
}

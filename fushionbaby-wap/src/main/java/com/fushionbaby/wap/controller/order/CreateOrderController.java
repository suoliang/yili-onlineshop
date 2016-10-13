package com.fushionbaby.wap.controller.order;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fushionbaby.common.constants.CookieConstant;
import com.fushionbaby.common.constants.GlobalConfigConstant;
import com.fushionbaby.common.constants.OrderConstant;
import com.fushionbaby.common.constants.SourceConstant;
import com.fushionbaby.common.dto.UserDto;
import com.fushionbaby.common.enums.PaymentTypeEnum;
import com.fushionbaby.common.util.CookieUtil;
import com.fushionbaby.common.util.EpointsUtil;
import com.fushionbaby.common.util.jsonp.Jsonp;
import com.fushionbaby.common.util.jsonp.Jsonp_data;
import com.fushionbaby.core.model.SoShoppingCartSkuUser;
import com.fushionbaby.core.service.SoShoppingCartSkuWebUserService;
import com.fushionbaby.core.vo.OrderParamUser;
import com.fushionbaby.facade.biz.common.order.CreateOrderFacade;
import com.fushionbaby.facade.biz.common.member.UserFacade;
import com.fushionbaby.member.model.MemberAddress;
import com.fushionbaby.member.service.MemberAddressService;
import com.fushionbaby.sku.model.Sku;
import com.fushionbaby.sku.model.SkuInventory;
import com.fushionbaby.sku.service.SkuInventoryService;
import com.fushionbaby.sku.service.SkuService;

/**
 * @author 张明亮 订单相关的操作
 */
@Controller
@RequestMapping("/order")
public class CreateOrderController {
	private static final Log logg = LogFactory.getLog(CreateOrderController.class);
	@Autowired
	private CreateOrderFacade createOrderFacade;
	@Autowired
	private MemberAddressService<MemberAddress> memberAddressService;
	@Autowired
	private SoShoppingCartSkuWebUserService<SoShoppingCartSkuUser> soShoppingCartSkuWebService; 
	@Autowired
	private SkuInventoryService<SkuInventory> skuInventoryService;
	@Autowired
	private UserFacade userFacade;
	@Autowired	
	private SkuService skuService;
	/***
	 * 
	 * 创建订单
	 * 
	 * @param orderParam
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping("createOrder")
	public Object createOrder(OrderParamUser orderParam, HttpServletRequest request) {
		try {
			String addressId = orderParam.getAddressId();	
			String sid = CookieUtil.getCookieValue(request, CookieConstant.COOKIE_SID);
			if(!userFacade.checkUserLogin(sid)){
				return Jsonp.noLoginError("请先登录!");
			}
			if (StringUtils.isBlank(addressId)){
				return Jsonp.noLoginError("收货地址不能为空!请选择收货地址");
			}
			boolean flag = createOrderFacade.checkIsSelectedPaymentType(Long.valueOf(addressId), orderParam.getPaymentType(), PaymentTypeEnum.PAYMENT_HDFK_WEB.getCode(), GlobalConfigConstant.HDFK_AREA_CODE);
			if(!flag){
				return Jsonp.error("此区域暂不支持货到付款，请选择其它支付方式");
			}
			UserDto user = userFacade.getLatestUserBySid(sid);
			if(StringUtils.equals(OrderConstant.GOTO_CART_PAYMENT, orderParam.getGotoType())){
				List<SoShoppingCartSkuUser> cartItemList = soShoppingCartSkuWebService.
						findSelectedCartSkuByMemberId(user.getMember_id());
				if (CollectionUtils.isEmpty(cartItemList)) {
					return Jsonp.paramError("亲!请选择您要购买的商品!在结算!");
				}
				
			}
			String checkSkuInventory = createOrderFacade.checkSkuInventory(user,orderParam.getGotoType(),orderParam.getPayOffId());
			if(ObjectUtils.notEqual(null,checkSkuInventory)  ){
				return Jsonp.error(checkSkuInventory);
			}
			String orderCode = EpointsUtil.generateOrdrNo();
			orderParam.setOrderCode(orderCode);
			orderParam.setSourceCode(SourceConstant.WAP_CODE);
			createOrderFacade.initOrderParam(orderParam);
			createOrderFacade.saveOrder(user,orderParam);
			if(StringUtils.equals(OrderConstant.GOTO_CART_PAYMENT, orderParam.getGotoType())){
				createOrderFacade.saveOrderLineByCart(orderParam.getOrderCode(), user);
				createOrderFacade.updateInventoryByCart(user);
				createOrderFacade.updateCartByCart(user);
			}else{
				createOrderFacade.saveOrderLineByImmediate(orderParam);
				Long skuId = createOrderFacade.updateInventoryByImmediate(orderParam, user);
				Sku sku=(Sku) skuService.findById(skuId);
				createOrderFacade.updateCartByImmediate(sku.getCode(), user);
			}
			createOrderFacade.updateOrderStatus(orderParam,user,PaymentTypeEnum.PAYMENT_HDFK_WEB.getCode());
			createOrderFacade.saveMemberAddress(user,Long.valueOf(addressId),orderParam.getOrderCode());
			memberAddressService.setMemberDefaultAddress(user.getMember_id(), Long.valueOf(orderParam.getAddressId()));
		} catch (Exception e) {
			logg.error("create订单创建异常:"+e);
			return Jsonp.error();
		}
		return Jsonp_data.success(orderParam.getOrderCode());
	}

}

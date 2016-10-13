package com.aladingshop.wap.controller.integral;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fushionbaby.cache.DataCache;
import com.fushionbaby.common.constants.CommonConstant.CommonMessage;
import com.fushionbaby.common.constants.CookieConstant;
import com.fushionbaby.common.constants.SettlementConstant;
import com.fushionbaby.common.constants.SourceConstant;
import com.fushionbaby.common.dto.UserDto;
import com.fushionbaby.common.dto.cart.CartItemDto;
import com.fushionbaby.common.enums.GoOrderPayEnum;
import com.fushionbaby.common.enums.ShoppingCartCaptChaEnum;
import com.fushionbaby.common.enums.SkuInventoryCheckEnum;
import com.fushionbaby.common.enums.SkuStatusEnum;
import com.fushionbaby.common.util.CookieUtil;
import com.fushionbaby.common.util.EpointsUtil;
import com.fushionbaby.common.util.jsonp.Jsonp;
import com.fushionbaby.common.util.jsonp.Jsonp_data;
import com.fushionbaby.core.model.OrderEpoint;
import com.fushionbaby.core.service.OrderEpointService;
import com.fushionbaby.facade.biz.common.member.UserFacade;
import com.fushionbaby.facade.biz.common.order.CreateOrderFacade;
import com.fushionbaby.member.model.Member;
import com.fushionbaby.member.service.MemberService;
import com.fushionbaby.sku.model.Sku;
import com.fushionbaby.sku.model.SkuInventory;
import com.fushionbaby.sku.service.SkuEpointService;
import com.fushionbaby.sku.service.SkuInventoryService;
import com.fushionbaby.sku.service.SkuService;

@Controller
@RequestMapping("/integral")
public class CommitIntegralOrderController {
	
	private static final Log LOGGER = LogFactory.getLog(CommitIntegralOrderController.class);
	
	@Autowired
	private UserFacade userFacade;
	@Autowired
	private SkuService skuService;
	@Autowired
	private SkuInventoryService<SkuInventory> skuInventoryService;
	@Autowired
	private MemberService<Member> memberService;
	@Autowired
	private CreateOrderFacade createOrderFacade;
	@Autowired
	private OrderEpointService<OrderEpoint> orderEpointService;
	@Autowired
	private SkuEpointService skuEpointService;
	
	/***
	 * @param payOffId   支付标识
	 * @param addressId  地址id
	 * @param sid		   登录用户标识
	 * @param skuCode    商品编码
	 * @param memo		   会员留言
	 * @param sendDate	   配送日期
	 * @param request	
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "integralCreateOrder")
	public Object createOrder(String payOffId, String addressId,
			@RequestParam(value="sid",defaultValue="") String sid,
			@RequestParam(value="skuCode",defaultValue="") String skuCode,
			String memo,@RequestParam(value="sendDate",defaultValue="") String sendDate,
			HttpServletRequest request) {
		final String payoffIdValue = (String) DataCache.get(payOffId);
		if (StringUtils.isBlank(payoffIdValue)) {
			return Jsonp.newInstance(GoOrderPayEnum.PAY_Off_FAILED_ERROR_MSG.getCode(),
					GoOrderPayEnum.PAY_Off_FAILED_ERROR_MSG.getMsg());
		}
		if (StringUtils.isBlank(sid)) {
			sid = CookieUtil.getCookieValue(request,CookieConstant.COOKIE_SID);
		}
		if (StringUtils.isBlank(sid)) {
			return Jsonp.noLoginError(CommonMessage.NO_LOGIN);
		}
		UserDto user = userFacade.getLatestUserBySid(sid);
		if (ObjectUtils.equals(null, user)) {
			return Jsonp.noLoginError(CommonMessage.NO_LOGIN);
		}
		if (StringUtils.isBlank(addressId)) {
			return Jsonp.newInstance(GoOrderPayEnum.ADDRESS_ISNULL.getCode(), GoOrderPayEnum.ADDRESS_ISNULL.getMsg());
		}
		List<CartItemDto> notExistSkuList = new ArrayList<CartItemDto>();
		Sku skuEntry = skuService.queryBySkuCode(skuCode);// 根据商品code拿到对应的商品
		if (skuEntry == null) {
			CartItemDto item = new CartItemDto();
			item.setSkuCode(skuCode);
			notExistSkuList.add(item);
		}
		if (!CollectionUtils.isEmpty(notExistSkuList) && notExistSkuList.size() > 0) {
			String code = ShoppingCartCaptChaEnum.SKU_NO.getCode();
			return Jsonp_data.newInstance(code, notExistSkuList,ShoppingCartCaptChaEnum.getTitle(code));
		}
		// 库存不足校验、出现库存不足客户端提示、停止整个订单
		if (!StringUtils.equals(skuEntry.getStatus(),SkuStatusEnum.SKU_STATUS_TOP.getStrVlue())) {
			return Jsonp.newInstance(
					SkuInventoryCheckEnum.SKU_OFF_SHElVES.getCode(),
					skuEntry.getName() + SkuInventoryCheckEnum.SKU_OFF_SHElVES.getMsg());
		}
		SkuInventory skuInventory = skuInventoryService.findBySkuCode(skuEntry.getUniqueCode());
		Integer availableQty = skuInventory == null ? 0 : skuInventory.getAvailableQty();
		int quantityNumber = 1;
		if (availableQty == null || availableQty < quantityNumber) {
			return Jsonp.newInstance(
					SkuInventoryCheckEnum.SKU_INVENTORY_FULL.getCode(),
					skuEntry.getName() + SkuInventoryCheckEnum.SKU_INVENTORY_FULL.getMsg());
		}
		
		try {
			Long memberId = user.getMemberId();
			Member userMember = memberService.findById(memberId);
			BigDecimal totalActual = (BigDecimal) DataCache.get(payOffId + SettlementConstant._TOTAL_ACTUAL);
			/** 首先判断用户现有积分能不能下单 */
			if (userMember.getEpoints().compareTo(totalActual) < 0) {
				return Jsonp.error("您当前积分不足");
			}
			
			OrderEpoint orderEpoint = new OrderEpoint();
			orderEpoint.setMemberId(memberId);
			orderEpoint.setSkuCode(skuCode);
			String orderCode = EpointsUtil.generateOrdrNo();
			orderEpoint.setOrderCode(orderCode);
			orderEpoint.setMemberName(userMember.getLoginName());
			orderEpoint.setSkuName(skuEntry.getName());
			orderEpoint.setSize(skuEntry.getSize());
			orderEpoint.setColor(skuEntry.getColor());
			orderEpoint.setQuantity(1);
			orderEpoint.setOrderStatus("1");
			orderEpoint.setSourceCode(SourceConstant.WAP_CODE);
			orderEpoint.setTotalEpointUsed(totalActual);
			orderEpoint.setMemo(memo);
			
			orderEpointService.add(orderEpoint);

			//List<ShoppingCartSku> cartItemList = createOrderFacade.saveOrderLineByCart(orderParam.getOrderCode(), shoppingCartBo);
			//createOrderFacade.updateInventoryByCart(cartItemList);
			
			// 库存数量减少更新
			//SkuInventory skuInventory = skuInventoryService.findBySkuCode(cartSkuItem.getSkuCode());
			if (skuInventory != null) {
				int usableQty = skuInventory.getAvailableQty() - 1;
				skuInventoryService.updateInventoryQuantity(skuInventory.getSkuCode(), usableQty);
			}
			
			/** 减掉用户现有积分 */
			userMember.setEpoints(userMember.getEpoints().subtract(totalActual));
			memberService.update(userMember);
			
			//memberService.updateDefaultAddressIdByMemberId(memberId, Long.valueOf(addressId));	
			
			Map<String, Object> result = new HashMap<String, Object>();
			result.put("sid", sid);
			result.put("orderCode", orderCode);
			return Jsonp_data.success(result);
		} catch (NumberFormatException e) {
			LOGGER.error("create积分商城订单创建异常:" + e);
			e.printStackTrace();
			return Jsonp.newInstance(GoOrderPayEnum.ORDER_EXCEPTION.getCode(), GoOrderPayEnum.ORDER_EXCEPTION.getMsg());
		}
	}
	
}

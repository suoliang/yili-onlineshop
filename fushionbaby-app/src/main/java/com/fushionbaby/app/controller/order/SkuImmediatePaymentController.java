	/**
 * 
 */
package com.fushionbaby.app.controller.order;

import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fushionbaby.cache.SessionCache;
import com.fushionbaby.common.constants.CommonConstant.CommonMessage;
import com.fushionbaby.common.dto.UserDto;
import com.fushionbaby.common.dto.order.GotoOrderDto;
import com.fushionbaby.common.enums.SkuStatusEnum;
import com.fushionbaby.common.util.jsonp.Jsonp;
import com.fushionbaby.common.util.jsonp.Jsonp_data;
import com.fushionbaby.facade.biz.common.order.OrderImmediatePaymentFacade;
import com.fushionbaby.sku.model.Sku;
import com.fushionbaby.sku.model.SkuInventory;
import com.fushionbaby.sku.service.SkuService;
import com.fushionbaby.sku.service.SkuInventoryService;

/**
 * @author mengshaobo
 *
 */
@Deprecated
@Controller
@RequestMapping("/immediate")
public class SkuImmediatePaymentController {
	private static final Log LOGG= LogFactory.getLog(SkuImmediatePaymentController.class);
	@Autowired
	private SkuService skuService;
	@Autowired
	private SkuInventoryService<SkuInventory> skuInventoryService;
	@Autowired
	private OrderImmediatePaymentFacade paymentFacade;
	
	
	/**
	 * 立即支付
	 * 
	 * @param sid
	 * @param sku_id
	 * @param quantity
	 * @return
	 */
	@ResponseBody
	@RequestMapping("immediate_payment")
	public Object immediatePayment(@RequestParam("sid") String sid, 
			@RequestParam("skuCode") String skuCode, @RequestParam("quantity") String quantity) {
		GotoOrderDto gotoOrderDto = new GotoOrderDto();
		try {
			if (StringUtils.isBlank(sid)) {
				return Jsonp.paramError("sid不能为空!");
			}
			UserDto user = null;
			if (StringUtils.isNotBlank(sid)) {
				user = (UserDto) SessionCache.get(sid);
			}
			if (user == null) {
				return Jsonp.noLoginError(CommonMessage.NO_LOGIN);
			}
			int quantityNumber = 0;
			try {
				quantityNumber = Integer.valueOf(quantity);
				quantityNumber = quantityNumber < 1 ? 1 : quantityNumber;
			} catch (NumberFormatException ne) {
				return Jsonp.paramError("购买数量quantity参数内容有误,必须是整数!");
			}
			Sku skuEntry = skuService.queryBySkuCode(skuCode);
			if (ObjectUtils.equals(null, skuEntry)) {
				return Jsonp.error("该商品已下架!");
			}
			if (!StringUtils.equals(skuEntry.getStatus(), SkuStatusEnum.SKU_STATUS_TOP.getStrVlue())) {
				return Jsonp.error("该商品已下架!");
			}
			SkuInventory skuInventory = skuInventoryService.findBySkuCode(skuCode);
			Integer availableQty = ObjectUtils.equals(null, skuInventory) ? 0 : skuInventory.getAvailableQty();
			if (ObjectUtils.equals(null, availableQty) || availableQty <= quantityNumber) {
				return Jsonp.error("该商品库存不足!");
			}
			gotoOrderDto = paymentFacade.immediatePayment(user, skuEntry, quantityNumber);
			
		} catch (Exception e) {
			LOGG.error("立即支付immediatePayment方法异常:"+e);
			Jsonp.error();
		}
		return Jsonp_data.success(gotoOrderDto);
	}
}

package com.fushionbaby.web.controller.pay;

import java.math.BigDecimal;
import java.text.DecimalFormat;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fushionbaby.common.util.jsonp.Jsonp;
import com.fushionbaby.common.util.jsonp.Jsonp_data;
import com.fushionbaby.core.model.SoSalesOrderUser;
import com.fushionbaby.core.service.SoSalesOrderWebUserService;

/**
 * 
 * @author King 索亮
 *
 */
@Controller
@RequestMapping("/webwx")
public class PayWxController {
	/**
	 * 订单操作(web)service
	 */
	@Autowired
	private SoSalesOrderWebUserService<SoSalesOrderUser> soSalesOrderService;

	/**
	 * 通过订单号获得订单价格
	 * 
	 * @param orderCode
	 * @return
	 */
	@ResponseBody
	@RequestMapping("getOrderPrice")
	public Object getOrderPrice(@RequestParam("order_code") String orderCode) {
		SoSalesOrderUser order = soSalesOrderService.queryByOrderCode(orderCode);
		if (order == null)
			return Jsonp.error();

		DecimalFormat df = new DecimalFormat("0");
		BigDecimal totalActual = new BigDecimal(df.format(order.getTotalActual().multiply(
				new BigDecimal(100))));
		return Jsonp_data.success(totalActual);
	}

	/**
	 * 支付成功回调订单方法
	 * 
	 * @param orderCode
	 *            订单编号
	 * @param paymentType
	 *            支付类型
	 * @return
	 */
	@ResponseBody
	@RequestMapping("callbackOrder")
	public Object callbackOrder(@RequestParam("order_code") String orderCode,
			@RequestParam("payment_type") String paymentType) {
		if (soSalesOrderService.modifyOrderPayment(orderCode, paymentType) == false)
			return Jsonp.error();
		return Jsonp.success();

	}
}

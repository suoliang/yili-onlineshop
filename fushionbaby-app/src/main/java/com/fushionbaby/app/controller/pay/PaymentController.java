package com.fushionbaby.app.controller.pay;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.Date;

import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.aladingshop.periodactivity.model.ActivityRedEnvlopeUseRecord;
import com.aladingshop.periodactivity.service.ActivityRedEnvlopeUseRecordService;
import com.fushionbaby.cache.SessionCache;
import com.fushionbaby.common.constants.CommonConstant;
import com.fushionbaby.common.dto.UserDto;
import com.fushionbaby.common.enums.OrderConfigServerEnum;
import com.fushionbaby.common.util.jsonp.Jsonp;
import com.fushionbaby.common.util.jsonp.Jsonp_data;
import com.fushionbaby.core.model.OrderBaseUser;
import com.fushionbaby.core.model.OrderFeeUser;
import com.fushionbaby.core.model.OrderFinanceUser;
import com.fushionbaby.core.service.OrderBaseUserService;
import com.fushionbaby.core.service.OrderFeeUserService;
import com.fushionbaby.core.service.OrderFinanceUserService;
import com.fushionbaby.facade.biz.common.yiduobao.YiDuoBaoCardFacade;

/**
 * 
 * @author mengshaobo 订单支付
 */
@Controller
@RequestMapping("/payment")
public class PaymentController {
	private final static Log LOGGER = LogFactory.getLog(PaymentController.class);
	@Autowired
	private OrderFinanceUserService<OrderFinanceUser> orderFinanceUserService;
	@Autowired
	private OrderFeeUserService<OrderFeeUser> orderFeeUserService;
	@Autowired
	private OrderBaseUserService<OrderBaseUser> orderBaseUserService;
	@Autowired
	private YiDuoBaoCardFacade yiDuoBaoCardFacade;
	@Autowired
	private ActivityRedEnvlopeUseRecordService<ActivityRedEnvlopeUseRecord> activityRedEnvlopeUseRecordService;

	/**
	 * 通过订单号获得订单价格
	 * 
	 * @param orderCode
	 * @return
	 */
	@ResponseBody
	@RequestMapping("getOrderPrice")
	public Object getOrderPrice(@RequestParam("sid") String sid, @RequestParam("orderCode") String orderCode) {
		UserDto user = (UserDto) SessionCache.get(sid);
		if (ObjectUtils.equals(user, null)) {
			return Jsonp.error();
		}
		OrderFeeUser orderFeeUser = orderFeeUserService.findByMIdAndOrdCode(user.getMemberId(), orderCode);
		if (ObjectUtils.equals(orderFeeUser, null)) {
			return Jsonp.error();
		}
		DecimalFormat df = new DecimalFormat("0");
		BigDecimal totalActual = new BigDecimal(df.format(orderFeeUser.getTotalActual().multiply(
				new BigDecimal(100))));
		return Jsonp_data.success(totalActual);
	}

	/**
	 * 支付成功回调订单方法
	 * 
	 * @param memberId
	 *            会员id
	 * @param orderCode
	 *            订单编号
	 * @param paymentType
	 *            支付类型
	 * @return
	 */
	@ResponseBody
	@RequestMapping("callbackOrder")
	public Object callbackOrder(@RequestParam("memberId") Long memberId, @RequestParam("orderCode") String orderCode,
			@RequestParam("paymentType") String paymentType) {
		LOGGER.info(" app PaymentController.class  callbackOrder 进入 *************************");
		try {
			OrderFinanceUser orderFinanceUser = new OrderFinanceUser();
			orderFinanceUser.setMemberId(memberId);
			orderFinanceUser.setOrderCode(orderCode);
			orderFinanceUser.setPaymentType(paymentType);
			orderFinanceUser.setFinanceStatus(CommonConstant.YES);
			orderFinanceUser.setPaymentCompleteTime(new Date());
			orderFinanceUserService.updateByMemberIdAndOrderCode(orderFinanceUser);
			OrderBaseUser orderBaseUser = new OrderBaseUser();
			orderBaseUser.setOrderCode(orderCode);
			orderBaseUser.setMemberId(memberId);
			orderBaseUser.setOrderStatus(OrderConfigServerEnum.AUDIT.getCode());
			orderBaseUserService.updateOrderStatus(orderBaseUser);
//			if (orderCode.contains("ydb")) {
//				/** 创建益多宝 */
//				LOGGER.info(" app PaymentController.class  开始创建 *************益多宝创建************");
//				yiDuoBaoCardFacade.createMemberCard(memberId, orderCode);
//			} else {
//				/** 更新益多宝收益额或赠券额 */
//				LOGGER.info(" app PaymentController.class  更新益多宝 *************************");
//				yiDuoBaoCardFacade.opeYiDuoBao(orderCode, memberId, null);
//			}
			this.redEnvlopeUseSuccess(orderCode);
		} catch (Exception e) {
			e.printStackTrace();
			LOGGER.error("支付回调出错" + e);
			return Jsonp.error();
		}
		return Jsonp.success();
	}
	/**
	 * 红包使用成功
	 * @param orderCode 订单编号
	 */
	private void redEnvlopeUseSuccess(String orderCode){
		
		ActivityRedEnvlopeUseRecord activityRedEnvlopeUseRecord = activityRedEnvlopeUseRecordService.findByOrderCode(orderCode);
		if(activityRedEnvlopeUseRecord== null || activityRedEnvlopeUseRecord.getUseStatus()!=0){
			return ;
		}
		activityRedEnvlopeUseRecord.setUseStatus(1);
		activityRedEnvlopeUseRecordService.updateUseStatus(activityRedEnvlopeUseRecord);
		
	}
}

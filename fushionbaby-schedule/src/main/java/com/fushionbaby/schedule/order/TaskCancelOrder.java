/**
 * 
 */
package com.fushionbaby.schedule.order;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.aladingshop.periodactivity.model.ActivityRedEnvlopeUseRecord;
import com.aladingshop.periodactivity.model.ActivityShareMember;
import com.aladingshop.periodactivity.service.ActivityRedEnvlopeUseRecordService;
import com.aladingshop.periodactivity.service.ActivityShareMemberService;
import com.aladingshop.sku.cms.model.SkuInventory;
import com.aladingshop.sku.cms.service.SkuInventoryService;
import com.fushionbaby.act.activity.model.ActCard;
import com.fushionbaby.act.activity.service.ActCardService;
import com.fushionbaby.common.constants.ScheduleConstant;
import com.fushionbaby.common.enums.OrderConfigServerEnum;
import com.fushionbaby.common.enums.RedEnvelopeUseStatusEnum;
import com.fushionbaby.member.model.Member;
import com.fushionbaby.member.service.MemberService;
import com.fushionbaby.order.model.OrderBase;
import com.fushionbaby.order.model.OrderFee;
import com.fushionbaby.order.model.OrderLine;
import com.fushionbaby.order.service.OrderBaseService;
import com.fushionbaby.order.service.OrderFeeService;
import com.fushionbaby.order.service.OrderLineService;

/**
 * @author mengshaobo 超过24小时定时取消订单,如果使用代金券将代金券使用次数减1
 */
@Service
public class TaskCancelOrder {
	private static boolean runFlag = false;
	/***日志*/
	private static final Log LOGGER = LogFactory.getLog(TaskCancelOrder.class);
	@Autowired
	private OrderBaseService<OrderBase> orderBaseService;
	@Autowired
	private OrderFeeService<OrderFee> orderFeeService;
	/**代金券service*/
	@Autowired
	private ActCardService<ActCard> actCardService;
	/**会员service*/
	@Autowired
	private MemberService<Member> memberService;
	/**订单行信息查询service*/
	@Autowired
	private OrderLineService<OrderLine> orderLineService;
	/**商品库存操作service*/
	@Autowired
	private SkuInventoryService<SkuInventory> skuInventoryService;
	
	@Autowired
	private ActivityRedEnvlopeUseRecordService<ActivityRedEnvlopeUseRecord> activityRedEnvlopeUseRecordService;
	
	@Autowired
	private ActivityShareMemberService<ActivityShareMember> activityShareMemberService;

	public void excute() {
		if (runFlag) {
			LOGGER.info("TaskCancelOrder正在运行");
			return;
		}
		runFlag = true;

		LOGGER.info("TaskCancelOrder开始运行");
		run();
		LOGGER.info("TaskCancelOrder运行结束");
		runFlag = false;
	}

	/**
	 * @see 1.查询出订单表中 订单状态为（等待付款）的订单列表
	 * @see 2.在订单集合中找到 创建时间大于24小时的订单信息
	 * @see 3.修改订单表添加 系统取消时间 ，订单状态为系统取消
	 * 
	 */
	private void run() {
		try{
			List<OrderBase> list = orderBaseService.findListByOrderStatus(OrderConfigServerEnum.WAITING_PAYMENT.getCode());
			if (CollectionUtils.isEmpty(list)) {
				return;
			}
			for (OrderBase order : list) {
				Date createTime = order.getCreateTime();
				if (createTime == null) {
					continue;
				}
				Long differTime = System.currentTimeMillis() - createTime.getTime();
				long hour = differTime / ScheduleConstant.NH;
				if (hour < ScheduleConstant.CANCEL_TIME_ORDER) {
					continue ;
				}
				//查询订单行信息
				List<OrderLine> orderLineList = orderLineService.findByOrderCode(order.getOrderCode(), "");
				for (OrderLine orderLine : orderLineList) {
					SkuInventory skuInventory = skuInventoryService.findBySkuCode(orderLine.getSkuCode());
					if (skuInventory != null) {
						//更新库存量--增加
						int availableQty = skuInventory.getAvailableQty() + orderLine.getQuantity();
						skuInventoryService.updateInventoryQuantity(orderLine.getSkuCode(), availableQty);
						LOGGER.info("取消订单库存更新正常");
					}
				}
				Long memberId = order.getMemberId();
				String orderCode = order.getOrderCode();
				OrderBase orderBase = new OrderBase();
				orderBase.setMemberId(memberId);
				orderBase.setOrderCode(orderCode);
				orderBase.setOrderStatus(OrderConfigServerEnum.CMS_TIMERTASK_CANCEL.getCode());
				orderBase.setSysCancelTime(new Date());
				orderBase.setSysCancelReason("24小时内未付款");
				orderBaseService.update(orderBase);
				LOGGER.info("取消订单订单状态更新正常");
				OrderFee orderFee = orderFeeService.findByMemberIdAndOrderCode(memberId, orderCode);
				//代金券号如果存在，说明使用了代金券
				if (ObjectUtils.notEqual(orderFee, null) && !StringUtils.isBlank(orderFee.getCardno())) {
					actCardService.updateBySysByCardNo(orderFee.getCardno());
					LOGGER.info("取消订单代金券更新正常");
				}
				Member member = memberService.findById(memberId);
				if (ObjectUtils.equals(member, null)) {
					continue;
				}
				//如果用户使用了积分
				if (order.getTotalPointUsed() != null) {
					//用户使用了的积分+会员表里的剩余积分
					BigDecimal epoints = order.getTotalPointUsed().add(member.getEpoints());
					member.setEpoints(epoints);
					memberService.update(member);
					LOGGER.info("取消订单会员积分更新正常");
				}
				if(ObjectUtils.notEqual(orderFee, null) && ObjectUtils.notEqual(orderFee.getUseWalletMoney(), null)){
					BigDecimal newAvailableMoney = orderFee.getUseWalletMoney().add(member.getAvailableMoney());
					member.setAvailableMoney(newAvailableMoney);
					memberService.update(member);
					LOGGER.info("取消订单钱包更新正常");
				}
				this.redEnvlopeUse(orderCode);
			}
		}catch(Exception e){
			e.printStackTrace();
			LOGGER.error("取消订单有错",e);
		}
	}
	/***
	 * 红包使用
	 * @param orderCode
	 */
	private void redEnvlopeUse(String orderCode){
		
		ActivityRedEnvlopeUseRecord activityRedEnvlopeUseRecord = activityRedEnvlopeUseRecordService.findByOrderCode(orderCode);
		if(activityRedEnvlopeUseRecord==null){
			return ;
		}
		if(activityRedEnvlopeUseRecord.getUseStatus() == 0){
			ActivityShareMember activityShareMember = activityShareMemberService.findByMemberId(activityRedEnvlopeUseRecord.getMemberId());
			BigDecimal newRedEnvelope = activityShareMember.getExistingRedEnvelope().add(activityRedEnvlopeUseRecord.getRedEnvelopeAmount());
			activityShareMember.setExistingRedEnvelope(newRedEnvelope);	
			activityShareMemberService.update(activityShareMember);
			activityRedEnvlopeUseRecord.setUseStatus(Integer.valueOf(RedEnvelopeUseStatusEnum.FAIL.getCode()));//返还红包金额
			activityRedEnvlopeUseRecordService.updateUseStatus(activityRedEnvlopeUseRecord);
		}
		
		LOGGER.info("取消红包更新正常");
		
	}

}

/**
 * 
 */
package com.fushionbaby.schedule.order;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.fushionbaby.common.constants.CommonConstant;
import com.fushionbaby.common.constants.ScheduleConstant;
import com.fushionbaby.common.enums.OrderConfigServerEnum;
import com.fushionbaby.log.model.LogMemberMoney;
import com.fushionbaby.log.service.LogMemberMoneyService;
import com.fushionbaby.member.model.Member;
import com.fushionbaby.member.service.MemberService;
import com.fushionbaby.order.model.OrderBase;
import com.fushionbaby.order.model.OrderFee;
import com.fushionbaby.order.service.OrderBaseService;
import com.fushionbaby.order.service.OrderFeeService;

/**
 * @author mengshaobo
 * 
 */
@Service
public class TaskScoreByCount {
	private static boolean runFlag = false;
	//日志
	private static final Log LOGGER = LogFactory.getLog(TaskScoreByCount.class);
	@Autowired
	private OrderBaseService<OrderBase> orderBaseService;
	@Autowired
	private OrderFeeService<OrderFee> orderFeeService;
	@Autowired
	private MemberService<Member> memberService;
	@Autowired
	private LogMemberMoneyService logMemberMoneyService;

	/**
	 * 从订单里面计算积分 1. 当 a.订单状态:8交易成功b.订单提交成功后，在规定时间内 发个 会员 积分c.积分状态为未派发积分 2.
	 * 找到该订单中对应的会员号，修改会员积分 3. 修改订单 积分状态
	 */
	public void excute() {

		if (runFlag) {
			LOGGER.info("TaskScoreByCount正在运行");
			return;
		}
		runFlag = true;

		LOGGER.info("TaskScoreByCount开始运行");
		this.run();
		LOGGER.info("TaskScoreByCount运行结束");
		runFlag = false;
	}

	/**
	 * @see 计算积分操作
	 * @see 1.查询出订单状态为（已完成）积分状态为（未处理）的订单
	 * @see 2.在订单集合中找到最后收货时间超过 24小时的订单信息
	 * @see 3.修改会员表积分
	 * @see 4.修改订单表积分处理状态
	 */
	private void run() {
		try {
			List<OrderBase> list = orderBaseService.findListByStatusAndIsHandlePoint(
							OrderConfigServerEnum.SUCCESS_TRANSACTION.getCode(),
							CommonConstant.NO);
			if (CollectionUtils.isEmpty(list)) {
				return;
			}
			for (OrderBase orderBase : list) {
				Date lastReceiveTime = orderBase.getLastReceiveTime();
				if (lastReceiveTime == null) {
					continue;
				}
				Long differTime = System.currentTimeMillis() - lastReceiveTime.getTime();
				long hour = differTime / ScheduleConstant.NH;
				if (hour > ScheduleConstant.COUNT_EPOINT_MEMBER) {
					if (this.modifyMember(orderBase)) {
						orderBase.setIsHandlePoint(CommonConstant.YES);
						orderBaseService.update(orderBase);
						LOGGER.info("会员积分处理正常");
					}
					OrderFee orderFee = orderFeeService.findByMemberIdAndOrderCode(
									orderBase.getMemberId(),
									orderBase.getOrderCode());
					if (ObjectUtils.notEqual(orderFee, null)&& ObjectUtils.notEqual(orderFee.getUseWalletMoney(), null)) {
						this.modifyMemberMoney(orderFee);
						LOGGER.info("会员钱包处理正常");
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			LOGGER.error("订单积分处理出错", e);
		}
	}

	/**
	 * 修改订单积分
	 * 
	 * @param order
	 *            订单号
	 */
	private boolean modifyMember(OrderBase order) {
		try {
			Long memberId = order.getMemberId();
			BigDecimal orderPointGained = order.getOrderPointGained();// 订单应获取的积分
			Member member = memberService.findById(memberId);
			if (ObjectUtils.equals(member, null)) {
				return false;
			}
			BigDecimal epoints = member.getEpoints();
			BigDecimal newEpoints = (orderPointGained != null ? orderPointGained : BigDecimal.valueOf(0))
					.add(epoints != null ? epoints : BigDecimal.valueOf(0));
			member.setEpoints(newEpoints);
			memberService.update(member);
		} catch (DataAccessException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}
	
	/**
	 * 修改账号余额
	 * @param order
	 */
	private void modifyMemberMoney(OrderFee orderFee){
		Long memberId = orderFee.getMemberId();
		String orderCode = orderFee.getOrderCode();
		Member member = memberService.findById(memberId);
		if (ObjectUtils.equals(member, null)) {
			return;
		}
		LogMemberMoney logMemberMoney = new LogMemberMoney();
		logMemberMoney.setMemberId(memberId);
		logMemberMoney.setAmount(orderFee.getUseWalletMoney());
		BigDecimal currentAmount = member.getAvailableMoney().subtract(orderFee.getUseWalletMoney());
		logMemberMoney.setCurrentAmount(currentAmount);
		logMemberMoney.setTransTime(new Date());
		logMemberMoney.setTransType(2);
		logMemberMoney.setDescription(DateFormatUtils.format(new Date(), "yyyy-MM-dd")+
				"使用余额: "+orderFee.getUseWalletMoney()+"支付订单:"+orderCode);
		logMemberMoneyService.add(logMemberMoney);
		member.setWalletMoney(currentAmount);
		memberService.update(member);
	}

}

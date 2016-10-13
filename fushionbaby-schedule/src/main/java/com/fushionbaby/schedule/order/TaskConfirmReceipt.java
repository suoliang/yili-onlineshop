package com.fushionbaby.schedule.order;

import java.util.Date;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.fushionbaby.common.constants.ScheduleConstant;
import com.fushionbaby.common.enums.OrderConfigServerEnum;
import com.fushionbaby.order.model.OrderBase;
import com.fushionbaby.order.service.OrderBaseService;

/**
 * cms已确认收货，用户未确认收货（订单状态=7），超过一周强制用户确认收货
 * @author King 索亮
 *
 */
@Service
public class TaskConfirmReceipt {
	private static boolean runFlag = false;
	//日志
	private static final Log LOGGER = LogFactory.getLog(TaskConfirmReceipt.class);
	@Autowired
	private OrderBaseService<OrderBase> orderBaseService;
	
	public void excute() {
		if (runFlag) {
			LOGGER.info("TaskConfirmReceipt正在运行");
			return;
		}
		runFlag = true;

		LOGGER.info("TaskConfirmReceipt开始运行");
		this.run();
		LOGGER.info("TaskConfirmReceipt运行结束");
		runFlag = false;
	}
	/**
	 * 1.查询出所有cms管理员确认收货的订单(订单状态为7)
	 * 2.根据cms管理员确认收货的时间，判断时间有没有超过一周
	 * 3.修改订单的状态为8交易完成
	 */
	private void run(){
		try {
			List<OrderBase> list = orderBaseService
					.findListByOrderStatus(OrderConfigServerEnum.CMS_CONFIRM_RECEIPT
							.getCode());
			if (CollectionUtils.isEmpty(list)) {
				return;
			}
			for (OrderBase orderBase : list) {
				Date lastReceiveTime = orderBase.getLastReceiveTime();
				if (lastReceiveTime == null) {
					continue;
				}
				//当前时间和管理员确认收货时间的差值
				Long differTime = System.currentTimeMillis() - lastReceiveTime.getTime();
				Long day = differTime / ScheduleConstant.ND;
				if (day > ScheduleConstant.FORCED_CONFIRM_RECEIPT) {
					orderBase.setOrderStatus(OrderConfigServerEnum.SUCCESS_TRANSACTION.getCode());
					orderBaseService.update(orderBase);
					LOGGER.info("强制用户确认收货正常");
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			LOGGER.error("强制用户确认收货有错",e);
		}
	}
}

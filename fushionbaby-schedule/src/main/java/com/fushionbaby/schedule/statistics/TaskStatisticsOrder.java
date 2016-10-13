package com.fushionbaby.schedule.statistics;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fushionbaby.order.model.OrderFinance;
import com.fushionbaby.order.service.OrderFinanceService;
import com.fushionbaby.statistics.model.StatisticsOrder;
import com.fushionbaby.statistics.service.StatisticsOrderService;


/***
 * 订单金额定时统计
 * @author cyla
 *
 */
@Service
public class TaskStatisticsOrder {
	@Autowired
	private OrderFinanceService<OrderFinance> orderFinanceService;
	
	private static boolean runFlag = false;
	//日志
	private static final Log LOGGER = LogFactory.getLog(TaskStatisticsOrder.class);
	
	@Autowired
	private StatisticsOrderService<StatisticsOrder> statisticsOrderService;
	
	public void excute() {
		
		if (runFlag) {
			LOGGER.info("TaskStatisticsOrder正在运行");
			return;
		}
		runFlag = true;
		LOGGER.info("TaskStatisticsOrder开始运行");
		this.run();
		LOGGER.info("TaskStatisticsOrder运行结束");

		runFlag = false;
	}
	/***
	 * 订单统计每隔24小时往数据库里添加销售额和和订单数量
	 */
	@Transactional
	private void run() {
		try {
			Date dNow = new Date();   //当前时间
			Calendar calendar = Calendar.getInstance(); //得到日历
			calendar.setTime(dNow);//把当前时间赋给日历
			calendar.add(Calendar.DAY_OF_MONTH, -1);  //设置为前一天
			Date dBefore = calendar.getTime();   //得到前一天的时间
			SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); //设置时间格式
			String defaultStartDate = sdf.format(dBefore);    //格式化前一天
			OrderFinance orderFinance = orderFinanceService.getOneDayCountByCreateTime(defaultStartDate);
			StatisticsOrder statisticsOrder = new StatisticsOrder();
			statisticsOrder.setTodaySalesMoney(orderFinance.getSalesTotalMoney() == null ? new BigDecimal(0) : orderFinance.getSalesTotalMoney());
			statisticsOrder.setTodayOrderNumber(orderFinance.getOrderTotalNumber());
			statisticsOrder.setBuyabovetwo(orderFinanceService.getBuyOverOneNumberByCreateTime(dNow.toString()));
			statisticsOrderService.add(statisticsOrder);
			LOGGER.info("计算订单正常");
		} catch (Exception e){
			e.printStackTrace();
			LOGGER.error("订单统计出错"+e);
		}
	}
}

package com.fushionbaby.schedule.statistics;

import java.math.BigDecimal;
import java.rmi.RemoteException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.b2m.eucp.sdkhttp.example.SmsClient;

import com.fushionbaby.common.util.date.DateFormat;
import com.fushionbaby.sms.model.Sms;
import com.fushionbaby.sms.service.SmsService;
import com.fushionbaby.statistics.model.StatisticsSms;
import com.fushionbaby.statistics.service.StatisticsSmsService;

/***
 * 计算短信费用
 * @author King 索亮
 *
 */
@Service
public class TaskStatisticsSms {
	
	@Autowired
	private SmsService<Sms> smsService;
	
	private static boolean runFlag = false;
	//日志
	private static final Log LOGGER = LogFactory.getLog(TaskStatisticsSms.class);
	@Autowired
	private StatisticsSmsService<StatisticsSms> statisticsSmsService;
	
	public void excute() {
		if (runFlag) {
			LOGGER.info("TaskStatisticsSms正在运行");
			return;
		}
		runFlag = true;
		LOGGER.info("TaskStatisticsSms开始运行");
		this.run();
		LOGGER.info("TaskStatisticsSms运行结束");

		runFlag = false;
	}
	/***
	 * 短信费用每隔24小时往数据库里添加剩余的费用
	 */
	@Transactional
	private void run() {
		//剩余短信费用
		double leftAmount = 0;
		try {
			
			Date dNow = new Date();   //当前时间
			Date dBefore = new Date();
			Calendar calendar = Calendar.getInstance(); //得到日历
			calendar.setTime(dNow);//把当前时间赋给日历
			calendar.add(Calendar.DAY_OF_MONTH, -1);  //设置为前一天
			dBefore = calendar.getTime();   //得到前一天的时间
			SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); //设置时间格式
			String defaultStartDate = sdf.format(dBefore);    //格式化前一天
			 
			leftAmount = SmsClient.getBalance();//后续会进行短信费用的预警
			StatisticsSms statisticsSms = new StatisticsSms();
			statisticsSms.setSmsNumber(smsService.findByCreateTime(DateFormat.stringToDate(defaultStartDate)));
			statisticsSms.setLeftAmount(new BigDecimal(leftAmount));
			statisticsSmsService.add(statisticsSms);
			LOGGER.info("计算短信费用正常");
		} catch (RemoteException e) {
			e.printStackTrace();
			LOGGER.error("统计短信出错"+e);
		}
	}
}

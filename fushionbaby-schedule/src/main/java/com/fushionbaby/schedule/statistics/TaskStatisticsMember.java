package com.fushionbaby.schedule.statistics;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fushionbaby.common.util.date.DateFormat;
import com.fushionbaby.member.model.Member;
import com.fushionbaby.member.service.MemberService;
import com.fushionbaby.statistics.model.StatisticsMember;
import com.fushionbaby.statistics.service.StatisticsMemberService;


/***
 * 会员注册统计
 * @author cyla
 *
 */
@Service
public class TaskStatisticsMember {
	
	@Autowired
	private MemberService<Member> memberService;
	
	private static boolean runFlag = false;
	//日志
	private static final Log LOGGER = LogFactory.getLog(TaskStatisticsMember.class);
	
	@Autowired
	private StatisticsMemberService<StatisticsMember> statisticsMemberService;
	
	public void excute() {
		
		if (runFlag) {
			LOGGER.info("TaskStatisticsMember正在运行");
			return;
		}
		runFlag = true;
		LOGGER.info("TaskStatisticsMember开始运行");
		this.run();
		LOGGER.info("TaskStatisticsMember运行结束");

		runFlag = false;
	}
	/***
	 * 注册渠道统计每隔24小时往数据库里添加会员注册数量
	 */
	@Transactional
	private void run() {
		
		try {
			Date dNow = new Date();   //当前时间
			Date dBefore = new Date();
			Calendar calendar = Calendar.getInstance(); //得到日历
			calendar.setTime(dNow);//把当前时间赋给日历
			calendar.add(Calendar.DAY_OF_MONTH, -1);  //设置为前一天
			dBefore = calendar.getTime();   //得到前一天的时间
			SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); //设置时间格式
			String defaultStartDate = sdf.format(dBefore);    //格式化前一天
			
			StatisticsMember statisticsMember = new StatisticsMember();
			Map<String, Object> todayMap = memberService.findByCreateTime(DateFormat.stringToDate(defaultStartDate));
			Map<String, Object> totalMap = memberService.findByCreateTime(null);
			statisticsMember.setTodayMemberNumber(Long.valueOf(todayMap.get("cnt")+""));
			statisticsMember.setAndroidCode(todayMap.get("android")== null ? "0" : todayMap.get("android").toString());
			statisticsMember.setIosCode(todayMap.get("ios")== null ? "0" : todayMap.get("ios").toString());
			statisticsMember.setWebCode(todayMap.get("web")== null ? "0" : todayMap.get("web").toString());
			statisticsMember.setCmsCode(todayMap.get("cms")== null ? "0" : todayMap.get("cms").toString());
			statisticsMember.setDefaultChannel(todayMap.get("DEFAULT_CHANNEL")== null ? "0" : todayMap.get("DEFAULT_CHANNEL").toString());;
			statisticsMember.setQqChannel(todayMap.get("QQ_CHANNEL")== null ? "0" : todayMap.get("QQ_CHANNEL").toString());
			statisticsMember.setWxChannel(todayMap.get("WX_CHANNEL")== null ? "0" : todayMap.get("WX_CHANNEL").toString());
			statisticsMember.setSinaChannel(todayMap.get("SINA_CHANNEL")== null ? "0" : todayMap.get("SINA_CHANNEL").toString());
			statisticsMember.setTotalNumber(Long.valueOf(totalMap.get("cnt")+""));
			statisticsMemberService.add(statisticsMember);
			LOGGER.info("计算会员正常");
		} catch (Exception e){
			e.printStackTrace();
			LOGGER.error("统计会员出错"+e);
		}
	}
}

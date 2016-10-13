package com.fushionbaby.web.controller.activities;

import java.math.BigDecimal;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fushionbaby.cache.SessionCache;
import com.fushionbaby.common.constants.CommonConstant;
import com.fushionbaby.common.constants.CommonConstant.CommonCode;
import com.fushionbaby.common.constants.CookieConstant;
import com.fushionbaby.common.constants.LotteryConstant;
import com.fushionbaby.common.dto.UserDto;
import com.fushionbaby.common.util.CookieUtil;
import com.fushionbaby.common.util.date.DateFormat;
import com.fushionbaby.common.util.jsonp.Jsonp;
import com.fushionbaby.common.util.jsonp.Jsonp_data;
import com.fushionbaby.core.model.SoSalesOrderUser;
import com.fushionbaby.core.service.SoSalesOrderWebUserService;
import com.fushionbaby.sysactivity.model.LotteryConfig;
import com.fushionbaby.sysactivity.model.LotteryDetail;
import com.fushionbaby.sysactivity.model.LotteryInfo;
import com.fushionbaby.sysactivity.model.LotteryPrize;
import com.fushionbaby.sysactivity.service.LotterDetailService;
import com.fushionbaby.sysactivity.service.LotteryConfigService;
import com.fushionbaby.sysactivity.service.LotteryInfoService;
import com.fushionbaby.sysactivity.service.LotteryPrizeService;

/**
 * 抽奖活动
 * @author 张明亮
 */
@Controller
@RequestMapping("/lottery")
public class LotteryController {
	/**记录日志*/
	private static final Log logger = LogFactory.getLog(LotteryController.class);
	
	/**
	 * 抽奖活动配置service
	 */
	@Autowired
	private LotteryConfigService<LotteryConfig> lotteryConfigService;
	
	/**
	 * 奖池,奖品配置表
	 */
	@Autowired
	private LotteryPrizeService<LotteryPrize> lotteryPrizeService;
	
	/**
	 * 抽奖记录service
	 */
	@Autowired
	private LotteryInfoService<LotteryInfo> lotteryInfoService;
	
	/**
	 * 抽奖结果记录表
	 */
	@Autowired
	private LotterDetailService<LotteryDetail> lotterDetailService;
	
	/**
	 * 订单操作service
	 */
	@Autowired
	private SoSalesOrderWebUserService<SoSalesOrderUser> soSalesOrderWebService;
	
	@RequestMapping("goto_lottery")
	public String gotoLottery(){
		return "will/activity/choujiang";
	}
	
	@ResponseBody
	@RequestMapping("run")
	public Object lottery(HttpServletRequest request) {
		try {
			String sid = CookieUtil.getCookieValue(request, CookieConstant.COOKIE_SID);
			if (StringUtils.isBlank(sid)) {
				return Jsonp.noLoginError("sid不能为空!");
			}
			UserDto user = (UserDto) SessionCache.get(sid);
			if (user == null) {
				return Jsonp.noLoginError("请先登录!");
			}

			/*获取配置信息 */
			LotteryConfig lotteryConfig = lotteryConfigService.findByLotteryCode(LotteryConstant.SAN_YANG_QI_GUO_NIAN);
			if (lotteryConfig == null) {
				return Jsonp.paramError("抽奖参数配置有误!");
			}
			Date startTime = lotteryConfig.getStartTime();
			Date endTime = lotteryConfig.getEndTime();
			Date nowDate = new Date();//当前系统时间

			//当前日期在startTime之后,endTime之前,取反
			if (!(startTime.before(nowDate) && endTime.after(nowDate))) {
				return Jsonp.newInstance(CommonCode.EVENT_END,"抽奖活动结束了!");
			}
			String startFrom = DateFormat.dateTimeToDateString(startTime);
			String endFrom = DateFormat.dateTimeToDateString(endTime);
			int orderStint = lotteryConfig.getOrderStint();//订单数量

			//活动日期范围内的订单总数量;已付款状态
			int orderCount = soSalesOrderWebService.findByOrderCount(startFrom, endFrom, CommonConstant.YES);

			Long memberId = user.getMember_id();
			//活动期间会员在本系统消费总金额;已付款状态
			BigDecimal memberTotalActual = soSalesOrderWebService.findByOrderAllTotalActual(memberId, startFrom, endFrom, CommonConstant.YES);
			memberTotalActual = memberTotalActual == null ? new BigDecimal(0) : memberTotalActual;
			int lotteryNumTmp = memberTotalActual.divide(new BigDecimal(LotteryConstant.REACH_MONEY)).intValue();

			//不满足抽奖条件
			if (lotteryNumTmp <= 0) {
				return Jsonp.newInstance(CommonCode.COMPLY_NOT,"很抱歉您不能参与抽奖:您还没有购物满"+LotteryConstant.REACH_MONEY+"元,不能参与抽奖!");
			}
			LotteryInfo lotteryInfo = lotteryInfoService.findByMemberId(memberId);
			int num = lotteryInfo == null ? 0 : lotteryInfo.getNum();//使用的抽奖次数

			//总抽奖次数<=已经使用过的抽奖次数
			if (lotteryNumTmp <= num) {//抽奖次数已经用完
				return Jsonp.newInstance(CommonCode.NUMBER_NOT_ENOUGH,"您的抽奖次数已经用完,您可以继续购物,订单满"+LotteryConstant.REACH_MONEY+"元,可继续抽奖!");
			}

			LotteryPrize lotteryPrize = null;
			//开始抽奖业务、返回奖品对象
			lotteryPrize = lotteryPrizeService.getRandomLotteryPrize(orderStint,orderCount);

			//更新或者新增会员抽奖记录
			lotteryInfoService.updateOrSaveLotteryInfo(user,lotteryInfo);

			//保存抽奖明细记录
			lotterDetailService.saveLotteryDetail(user,lotteryPrize.getId());

			//返回抽奖结果对象
			return Jsonp_data.success(lotteryPrize);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("抽奖出现异常:"+e);
			return Jsonp.error();
		}
	}
}
package com.aladingshop.wap.controller.integral;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.aladingshop.wap.vo.OrderEpointRecordDto;
import com.fushionbaby.cache.SessionCache;
import com.fushionbaby.common.constants.CookieConstant;
import com.fushionbaby.common.dto.UserDto;
import com.fushionbaby.common.util.CookieUtil;
import com.fushionbaby.common.util.NumberFormatUtil;
import com.fushionbaby.common.util.date.DateFormat;
import com.fushionbaby.core.model.OrderEpoint;
import com.fushionbaby.core.service.OrderEpointService;
import com.fushionbaby.facade.image.ImageProcess;

/***
 * @description 积分商品兑换记录
 * @author 索亮
 * @date 2016年3月1日下午4:36:49
 */
@Controller
@RequestMapping("integral")
public class ExChangeRecordController {
	
	private final Log LOGGER = LogFactory.getLog(ExChangeRecordController.class);
	
	@Autowired
	private OrderEpointService<OrderEpoint> orderEpointService;
	@Autowired
	private ImageProcess imageProcess;
	
	/***
	 * 积分商城消费记录
	 * @param sid  登录用户标识
	 * @param type 类型-用于判断是不是APP(传值为APP)端请求的
	 * @return
	 */
	@RequestMapping("epointRecord")
	public String changeRecord(
			HttpServletRequest request,
			@RequestParam(value="sid",defaultValue="")String sid,
			@RequestParam(value="type",defaultValue="")String type,
			ModelMap model){
		try {
			if (StringUtils.isBlank(sid)) {
				sid = CookieUtil.getCookieValue(request,CookieConstant.COOKIE_SID);
			}
			UserDto user = (UserDto) SessionCache.get(sid);
			if (ObjectUtils.equals(user, null)) {
				return "login/login";
			}
			/**积分商城订单记录集合*/
			List<OrderEpoint> list = orderEpointService.findByMemberId(user.getMemberId());
			
			List<OrderEpointRecordDto> recordDtos = new ArrayList<OrderEpointRecordDto>();
			for (OrderEpoint orderEpoint : list) {
				OrderEpointRecordDto recordDto = new OrderEpointRecordDto();
				recordDto.setExChangeTime(DateFormat.dateTimeToDateString(orderEpoint.getCreateTime()));
				recordDto.setUsePoint(NumberFormatUtil.numberFormat(orderEpoint.getTotalEpointUsed()));
				recordDto.setSkuName(orderEpoint.getSkuName());
				recordDto.setQuantity(String.valueOf(orderEpoint.getQuantity()));
				recordDto.setImgPath(imageProcess.getOrigImagePath(orderEpoint.getSkuCode()));
				recordDtos.add(recordDto);
			}
			
			model.addAttribute("epointRecords", recordDtos);
			model.addAttribute("type", type);
			
		} catch (Exception e) {
			e.printStackTrace();
			LOGGER.error("积分兑换记录查询有误", e);
		}
		return "point/point-cash-record";
	}
	
}

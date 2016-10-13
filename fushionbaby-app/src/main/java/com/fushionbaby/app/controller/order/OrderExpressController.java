package com.fushionbaby.app.controller.order;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fushionbaby.common.constants.CommonConstant.CommonMessage;
import com.fushionbaby.common.dto.UserDto;
import com.fushionbaby.common.dto.express.ExpressResDto;
import com.fushionbaby.common.security.MD5Util;
import com.fushionbaby.common.util.BeanNullConverUtil;
import com.fushionbaby.common.util.CheckIsEmpty;
import com.fushionbaby.common.util.jsonp.Jsonp;
import com.fushionbaby.common.util.jsonp.Jsonp_data;
import com.fushionbaby.config.model.SysmgrExpressConfig;
import com.fushionbaby.config.service.SysmgrExpressConfigService;
import com.fushionbaby.core.model.OrderTransUser;
import com.fushionbaby.core.service.OrderTransUserService;
import com.fushionbaby.core.vo.OrderExperssReqDto;
import com.fushionbaby.facade.biz.common.express.ExpressFacade;
import com.fushionbaby.facade.biz.common.member.UserFacade;
import com.google.gson.Gson;
/***
 * 订单物流快递追踪
 * @description 类描述...
 * @author 徐培峻
 * @date 2015年11月2日下午7:38:21
 */
@Controller
@RequestMapping("/order")
public class OrderExpressController  {
	/** 登录会员用户*/
	@Autowired
	private UserFacade userFacade;
	/** 订单快递信息*/
	@Autowired
	private OrderTransUserService<OrderTransUser> orderTransUserService;
	/** 快递公司配置*/
	@Autowired
	private SysmgrExpressConfigService sysmgrExpressConfigService;
	/** 快递信息*/
	@Autowired
	private ExpressFacade expressFacade;
	
	private static final Log LOGGER=LogFactory.getLog(OrderExpressController.class);
	
	@ResponseBody
	@RequestMapping("getOrderExpress")
	public Object gotoOrder(@RequestParam("data") String data, @RequestParam("mac") String mac) {
		try {
			LOGGER.info("获取订单快递状态     action--请求报文：{" + data + "}");
			if (!MD5Util.validMd5(data, MD5Util.getKey(), mac)) {
				return Jsonp.error_md5();
			}
			Gson gson = new Gson();
			OrderExperssReqDto reqDto=gson.fromJson(data, OrderExperssReqDto.class);
			if (CheckIsEmpty.isEmpty(reqDto.getSid(),reqDto.getOrderCode())) {
				return Jsonp.paramError("参数传递有误");
			}
			UserDto user = userFacade.getLatestUserBySid(reqDto.getSid());
			if (ObjectUtils.equals(null, user)) {
				return Jsonp.noLoginError(CommonMessage.NO_LOGIN);
			}
		    Map<String, Object> paramMap=new HashMap<String, Object>();
		    paramMap.put("memberId", user.getMemberId());
		    paramMap.put("orderCode",reqDto.getOrderCode() );
			OrderTransUser orderTransUser=this.orderTransUserService.findByParam(paramMap);
			if(orderTransUser==null||orderTransUser.getTransName()==null)
				return Jsonp.searchFailureError("查询不到该订单更多物流信息");
			SysmgrExpressConfig expressConfig=sysmgrExpressConfigService.findByExpressCompanyName(orderTransUser.getTransName().trim());
			if(expressConfig==null||expressConfig.getExpressCompanyCode()==null)
				return Jsonp.searchFailureError("查询快递公司编码异常");
			reqDto.setCom(expressConfig.getExpressCompanyCode());
			reqDto.setNu(orderTransUser.getTransCode());
			//获得该订单的详细物流信息
			ExpressResDto resDto=expressFacade.getExpressInfo(reqDto);
			if(resDto==null)
				return Jsonp.error("查无该订单信息");
			resDto.setName(orderTransUser.getTransName());
			BeanNullConverUtil.nullConver(resDto);
			return Jsonp_data.success(resDto);
		} catch (Exception e) {
			e.printStackTrace();
			LOGGER.error("查询订单物流信息出异常   app OrderExpressController.java ", e);
			return Jsonp.error();
		}
	}
	
	
}

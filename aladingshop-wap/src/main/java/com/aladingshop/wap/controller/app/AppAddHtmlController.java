package com.aladingshop.wap.controller.app;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @description APP端展示页面
 * @author 索亮
 * @date 2016年1月13日下午5:45:27
 */
@Controller
@RequestMapping("/app")
public class AppAddHtmlController {
	
	/**
	 * 如意消费卡收益宣传页
	 * @return
	 */
	@RequestMapping("toSpeRuyi")
	public String toSpeRuyi(){
		return "app/spe-ruyi";
	}
	
	/**
	 * 申请开通如意消费卡页
	 * @return
	 */
	@RequestMapping("toSpeRuyiApply")
	public String toSpeRuyiApply(){
		return "app/spe-ruyi-vip-supply";
	}
	
	/**
	 * 微信加盟商宣传页
	 * @return
	 */
	@RequestMapping("toWeixinJoin")
	public String toWeixinJoin(){
		return "app/weixin-join";
	}
	
}

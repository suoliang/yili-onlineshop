package com.fushionbaby.pay.controller.app.union.service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/***
 * 阿拉丁卡 订单 使用银联支付
 * @description 类描述...
 * @author 徐培峻
 * @date 2015年10月30日下午4:24:42
 */
public interface AppUnionYiduobaoService {

	/***
	 * 获得交易流水号
	 * @param request
	 * @param data
	 * @param mac
	 * @return
	 */
	Object getYiduobaoUnionTn(HttpServletRequest request, String data,String mac);
	/***
	 * 支付 回调通知
	 * @param request
	 * @param response
	 */
	void YiduobaoUnionNotify(HttpServletRequest request,HttpServletResponse response);

}

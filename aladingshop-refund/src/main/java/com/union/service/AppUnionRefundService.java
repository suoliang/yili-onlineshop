package com.union.service;

import javax.servlet.http.HttpServletRequest;

public interface AppUnionRefundService {
	
	/***
	 * 发起退款
	 * 
	 * @param request
	 * @param sid
	 * @param orderCode
	 * @return
	 */
	public void refund(HttpServletRequest request,Long memberId,String orderCode); 
	
	/***
	 * 异步通知到后台，交易信息
	 * 
	 * @param request
	 * @return
	 */
	public void notifyBack(HttpServletRequest request) throws Exception;

	
}

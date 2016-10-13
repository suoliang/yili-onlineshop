package com.fushionbaby.pay.controller.app.alabao.service;

import java.rmi.RemoteException;

import javax.servlet.http.HttpServletRequest;

/***
 *  阿拉丁卡订单用 如意宝支付
 * @description 类描述...
 * @author 徐培峻
 * @date 2015年10月26日上午9:51:31
 */
public interface AppAlabaoYiduobaoService {

	Object getAlabaoYiduobaoPayModel(HttpServletRequest request, String data,String mac);

	Object alabaoPay(String data, String mac) throws RemoteException;

	
}

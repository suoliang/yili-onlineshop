package com.fushionbaby.sms.service;

import com.fushionbaby.sms.dto.Sms5cReqDto;
import com.fushionbaby.sms.dto.Sms5cResDto;

/***
 * 美联软通短信服务 
 * @description 类描述...
 * @author 徐培峻
 * @date 2015年11月24日下午4:48:03
 */
public interface Sms5cService {
	
	/***
	 * 发送短信
	 * @param req
	 * @return
	 */
	Sms5cResDto sendSmsMessage(Sms5cReqDto req);

	/***
	 * 查询余额
	 * @param req
	 * @return
	 */
	Sms5cResDto querySmsMessage(Sms5cReqDto req);
	
	/***
	 * 绑定ip
	 * action 为0时代表绑定（需带ip参数） 1为查询 2 位清空
	 * @param req
	 * @return
	 */
	Sms5cResDto bindIp(Sms5cReqDto req);
}

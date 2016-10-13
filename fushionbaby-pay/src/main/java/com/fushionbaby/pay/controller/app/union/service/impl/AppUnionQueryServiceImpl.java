package com.fushionbaby.pay.controller.app.union.service.impl;

import java.util.HashMap;
import java.util.Map;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;
import com.fushionbaby.common.util.jsonp.Jsonp_data;
import com.fushionbaby.pay.controller.app.union.conf.UpmpConfig;
import com.fushionbaby.pay.controller.app.union.service.AppUnionQueryService;
import com.fushionbaby.pay.controller.app.union.util.DemoBase;
import com.unionpay.acp.sdk.SDKConfig;

@Service
public class AppUnionQueryServiceImpl extends DemoBase implements AppUnionQueryService{

	private final static Log LOGGER = LogFactory.getLog(AppUnionQueryServiceImpl.class);
	
	public Object queryUnionRecord(String orderId, String txnTime) {
		/**
		 * 参数初始化
		 * 在java main 方式运行时必须每次都执行加载
		 * 如果是在web应用开发里,这个方写在可使用监听的方式写入缓存,无须在这出现
		 */
		SDKConfig.getConfig().loadPropertiesFromSrc();// 从classpath加载acp_sdk.properties文件

		/**
		 * 组装请求报文
		 */
		Map<String, String> data = new HashMap<String, String>();
		// 版本号
		data.put("version", "5.0.0");
		// 字符集编码 默认"UTF-8"
		data.put("encoding", "UTF-8");
		// 签名方法 01 RSA
		data.put("signMethod", "01");
		// 交易类型 
		data.put("txnType", "00");
		// 交易子类型 
		data.put("txnSubType", "00");
		// 业务类型
		data.put("bizType", "000000");
		// 渠道类型，07-PC，08-手机
		data.put("channelType", "08");
		// 接入类型，商户接入填0 0- 商户 ， 1： 收单， 2：平台商户
		data.put("accessType", "0");
		// 商户号码，请改成自己的商户号
		data.put("merId", UpmpConfig.MER_ID);
		// 商户订单号，请修改被查询的交易的订单号
		data.put("orderId", orderId);
		// 订单发送时间，请修改被查询的交易的订单发送时间
		data.put("txnTime", txnTime);

		data = signData(data);

		// 交易请求url 从配置文件读取
		String url = SDKConfig.getConfig().getSingleQueryUrl();

		Map<String, String> resmap = submitUrl(data, url);
		
		LOGGER.info("请求报文=["+data.toString()+"]");
		LOGGER.info("应答报文=["+resmap.toString()+"]");
		
		return Jsonp_data.success(resmap);
	}
	
}

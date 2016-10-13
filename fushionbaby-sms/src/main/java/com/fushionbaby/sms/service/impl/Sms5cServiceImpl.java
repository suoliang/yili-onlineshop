package com.fushionbaby.sms.service.impl;

import java.net.URLEncoder;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;

import com.fushionbaby.common.util.HttpRequest;
import com.fushionbaby.sms.dto.Sms5cReqDto;
import com.fushionbaby.sms.dto.Sms5cResDto;
import com.fushionbaby.sms.service.Sms5cService;
import com.google.gson.Gson;

/***
 * 美联软通短信服务 
 * @description 类描述...
 * @author 徐培峻
 * @date 2015年11月24日下午4:48:03
 */
@Service
public class Sms5cServiceImpl implements Sms5cService {

	
	
	private static  final Log LOGGER=LogFactory.getLog(Sms5cServiceImpl.class);
	
	private static String  send_url="http://m.5c.com.cn/api/send/index.php"; 
	private static String  query_url="http://m.5c.com.cn/api/query/index.php";
	private static String  bind_url="http://m.5c.com.cn/api/bind/index.php";
	
	public Sms5cResDto sendSmsMessage(Sms5cReqDto req) {
		  Sms5cResDto   resDto=new Sms5cResDto();
		  try {
			  String content=URLEncoder.encode(req.getContent(),"UTF-8");
			  String data="{\"type\":\"send\",\"username\":\""+req.getUserName()+"\",\"password\":\""+req.getPassword()+"\",\"apikey\":\""+req.getApiKey()+"\",\"mobile\":\""+req.getMobile()+"\",\"content\":\""+content+"\"}";
			  LOGGER.info("美联软通的发送短信的请求数据：data="+data);
			  String param="format=json&data="+data;
			  LOGGER.info("美联软通的发送短信的请求数据参数：param="+data);
			 
			  String json=HttpRequest.sendPost(send_url, param);
			  
	          Gson gson=new Gson();
	          resDto=gson.fromJson(json, Sms5cResDto.class);
		} catch (Exception e) {
			LOGGER.error("sms:Sms5cServiceImpl.java 发送短信异常", e);
		}
		  return resDto;
	}

	public Sms5cResDto querySmsMessage(Sms5cReqDto req) {
		Sms5cResDto   resDto=new Sms5cResDto();
		String param="username="+req.getUserName()+"&password="+req.getPassword()+"&apikey="+req.getApiKey();
		String json=HttpRequest.sendPost(query_url, param);
		if(json.contains("error")){
			resDto.setMsg("请求参数有误");
			resDto.setStatus("error");
			return resDto;
		}
		String totalNum=json.substring(json.indexOf("/")+1);
		String balanceNum=json.substring(0,json.indexOf("/"));
		LOGGER.info("总有短信数量 totalNum="+totalNum);
		LOGGER.info("还剩短信数量 balanceNum="+balanceNum);
		resDto.setBalanceNum(balanceNum);
		resDto.setTotalNum(totalNum);
		resDto.setStatus("success");
		return resDto;
	}
	
	
	public static void main(String[] args) {
		Sms5cReqDto req=new Sms5cReqDto();
		req.setMobile("17717648664");
		req.setContent("【阿拉丁玛特】您本次操作的验证码为123036，如不是你本人操作，请忽略。");
		Sms5cResDto   resDto=new Sms5cServiceImpl().sendSmsMessage(req);
		Sms5cResDto   resDto2=new Sms5cServiceImpl().querySmsMessage(req);
		System.out.println(resDto.getStatus());
		System.out.println(resDto.getMsg());
		System.out.println(resDto2.getStatus());
		System.out.println(resDto2.getMsg());
		
	}

	public Sms5cResDto bindIp(Sms5cReqDto req) {
		Sms5cResDto   resDto=new Sms5cResDto();
		try {
			String param="username="+req.getUserName()+"&password="+req.getPassword()+"&apikey="+req.getApiKey()+"&ip="+req.getIp()+"&action="+req.getAction();
			String json=HttpRequest.sendPost(bind_url, param);
			String msg=json.substring(json.indexOf(":")+1);
			String status=json.substring(0,json.indexOf(":"));
			resDto.setStatus(status);
			resDto.setMsg(msg);
		} catch (Exception e) {
			resDto.setStatus("error");
		}
		return resDto;
	}

}

package com.fushionbaby.cms.controller.jpush;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import cn.jpush.api.JPushClient;
import cn.jpush.api.common.resp.APIConnectionException;
import cn.jpush.api.common.resp.APIRequestException;
import cn.jpush.api.push.PushResult;
import cn.jpush.api.push.model.PushPayload;

import com.fushionbaby.common.constants.CommonConstant;
import com.fushionbaby.common.constants.JpushConstant;
import com.fushionbaby.common.dto.sysmgr.JpushDto;
import com.fushionbaby.common.model.JsonResponseModel;
import com.fushionbaby.common.util.BasePagination;
import com.fushionbaby.push.jpush.PushMessageService;
import com.fushionbaby.push.model.SysmgrJpush;
import com.fushionbaby.push.service.SysmgrJpushService;

/***
 * 极光推送
 * 
 * @author xupeijun
 * 
 */
@Controller
@RequestMapping("/push")
public class PushMessageController {
	 
	private static  String masterSecret=JpushConstant.JPUSH_MASTER_SECRET;
	private static  String appKey=JpushConstant.JPUSH_APP_KEY;
	
	private static final String connection_error_msg="推送失败，链接出错，稍后重试";
	private static final String response_error_msg="推送失败，响应出错，检查参数";
	private static final String push_success="恭喜你，推送成功";
	public static final Log logg = LogFactory.getLog(PushMessageController.class);
	
	
	@Autowired
	private  SysmgrJpushService<SysmgrJpush> sysmgrJpushService;
	
    private static JPushClient jpushClient = new JPushClient(masterSecret, appKey, 3);

    
	@SuppressWarnings("unchecked")
	@RequestMapping("listAllPushMessage")
	public String listAllPushMessage(BasePagination page,Model model){
		try {
			if (page == null) {
				page = new BasePagination();
			}
			Map<String, String> params = new HashMap<String, String>();
			page.setParams(params);
			page = this.sysmgrJpushService.getListPage(page);
			List<SysmgrJpush> pushMessageList = (List<SysmgrJpush>) page.getResult();
			model.addAttribute("pushMessageList", pushMessageList);
			model.addAttribute("page", page);
			return "models/push/pushRecordList";
		} catch (Exception e) {
			logg.error("推送消息列表查询出错", e);
			return "";
		}
		
	}
    
    @RequestMapping("goToPush")
	public String goToPush(){
		return "push/push";
	}
	
	@RequestMapping("pushMain")
	public String pushMain(JpushDto jpush,Model model){
		JsonResponseModel json=null;
		try {
			json = pushMessageToAlltagAndAlias(jpush, json);
			json = pushMessageToAllTag(jpush, json);
			json = pushMessageToAllAlias(jpush, json);
			json = pushMessageToDevice(jpush, json);
			if(json.getResult()=="success"){
				return listAllPushMessage(new BasePagination(),model);
			}
			return goToPush();
		} catch (Exception e) {
			logg.error("推送失败", e);
			return goToPush();
		}
	}

	private JsonResponseModel pushMessageToAllAlias(JpushDto jpush,JsonResponseModel json) {
		if(StringUtils.isNotBlank(jpush.getAlias())){
			json=pushToAllAlias(jpush,json);
		}
		return json;
	}

	private JsonResponseModel pushMessageToAllTag(JpushDto jpush,JsonResponseModel json) {
		if(StringUtils.isNotBlank(jpush.getTag())){
			json=pushToAllTag(jpush,json);
		}
		return json;
	}

	private JsonResponseModel pushMessageToAlltagAndAlias(JpushDto jpush,JsonResponseModel json) {
		if(StringUtils.isNotBlank(jpush.getTag())&&StringUtils.isNotBlank(jpush.getAlias())){
			json=pushToTagAndAlias(jpush,json);
		}
		return json;
	}

	/***
	 * 推送到所有的alias下
	 * @param jpush
	 * @param json
	 * @return
	 */
	private JsonResponseModel pushToAllAlias(JpushDto jpush,
			JsonResponseModel json) {
		Map<String, String> map=setParamMap(jpush);
		
		try {
			PushPayload payload=PushMessageService.pushMessageToAllAlias(jpush,map);
				PushResult result=jpushClient.sendPush(payload);
				if(dealResult(result)){
					savePushMessage(jpush,CommonConstant.YES);
					json.Success(push_success);
				}else{
					savePushMessage(jpush,CommonConstant.NO);
					json.Fail("推送失败");
				}
				logg.info("推送到安卓和IOS的结果是："+result);
			} catch (APIConnectionException e) {
				    jpushAPIConnectFail(json, e);
			} catch (APIRequestException e) {
				    jpushAPIRequestFail(json, e);
			}
			return json;
	}

	/***
	 * 推送到所有的tag下 
	 * @param jpush
	 * @param json
	 * @return
	 */
	private JsonResponseModel pushToAllTag(JpushDto jpush,JsonResponseModel json) {
		Map<String, String> map=setParamMap(jpush);
		try {
			PushPayload payload=PushMessageService.pushMessageToAllTag(jpush,map);
				PushResult result=jpushClient.sendPush(payload);
				if(dealResult(result)){
					savePushMessage(jpush,CommonConstant.YES);
					json.Success(push_success);
				}else{
					savePushMessage(jpush,CommonConstant.NO);
					json.Fail("推送失败");
				}
				logg.info("推送到安卓和IOS的结果是："+result);
			} catch (APIConnectionException e) {
				    jpushAPIConnectFail(json, e);
			} catch (APIRequestException e) {
				    jpushAPIRequestFail(json, e);
			}
			return json;
	}

	/***
	 *推送到  tag和 alias下
	 * @param jpush
	 * @param json
	 * @return
	 */
	private JsonResponseModel pushToTagAndAlias(JpushDto jpush,JsonResponseModel json) {
		Map<String, String> map = setParamMap(jpush);
		try {
		PushPayload payload=PushMessageService.pushMessageToAllTagAndAlias(jpush,map);
			PushResult result=jpushClient.sendPush(payload);
			if(dealResult(result)){
				savePushMessage(jpush,CommonConstant.YES);
				json.Success(push_success);
			}else{
				savePushMessage(jpush,CommonConstant.NO);
				json.Fail("推送失败");
			}
			logg.info("推送到安卓和IOS的结果是："+result);
		} catch (APIConnectionException e) {
			    jpushAPIConnectFail(json, e);
		} catch (APIRequestException e) {
			    jpushAPIRequestFail(json, e);
		}
		return json;
	}

	private Map<String, String> setParamMap(JpushDto jpush) {
		Map<String, String> map=new HashMap<String, String>();
		map.put("content", jpush.getContent());
		map.put("url", jpush.getUrl());
		map.put("type", jpush.getAccessType());
		map.put("data", jpush.getData());
		return map;
	}

	/***
	 * 推送消息
	 * @param jpush
	 * @param json
	 * @return
	 */
	private JsonResponseModel pushMessageToDevice(JpushDto jpush,JsonResponseModel json) {
		if(pushToAllDevices(jpush))
				json=pushToIosAndAndroid(jpush);
		if(pushToAndroidDevices(jpush))
				json=pushToAndroid(jpush);
		if(pushToIOSDevices(jpush))
				json=pushToIOS(jpush);
		
		return json;
	}

	/***
	 * 推送到ios
	 * @param jpush
	 * @return
	 */
	private boolean pushToIOSDevices(JpushDto jpush) {
		return JpushConstant.IOS_SOURCE_PUSH.equals(jpush.getDevice());
	}

	/***
	 * 推送到Android
	 * @param jpush
	 * @return
	 */
	private boolean pushToAndroidDevices(JpushDto jpush) {
		return JpushConstant.ANDROID_SOURCE_PUSH.equals(jpush.getDevice());
	}

	/***
	 * 推送到所有的设备（Android-ios）
	 * @param jpush
	 * @return
	 */
	private boolean pushToAllDevices(JpushDto jpush) {
		return JpushConstant.ANDROID_IOS_SOURCE_PUSH.equals(jpush.getDevice());
	}
	
	/***
	 * 推送到IOS
	 * @param jpush
	 * @return
	 */
	private JsonResponseModel pushToIOS(JpushDto jpush) {
		JsonResponseModel json=new JsonResponseModel();
		Map<String, String> iosMap = setParamMap(jpush);
		try {
		PushPayload payload=PushMessageService.pushMessageToIOS(jpush, iosMap);
			PushResult result=jpushClient.sendPush(payload);
			if(dealResult(result)){
				savePushMessage(jpush,CommonConstant.YES);
				json.Success(push_success);
			}else{
				savePushMessage(jpush,CommonConstant.NO);
				json.Fail("推送失败");
			}
			logg.info("推送到安卓和IOS的结果是："+result);
		} catch (APIConnectionException e) {
			    jpushAPIConnectFail(json, e);
		} catch (APIRequestException e) {
			    jpushAPIRequestFail(json, e);
		}
		return json;
	}

	/***
	 * api 请求出错
	 * @param json
	 * @param e
	 */
	private void jpushAPIRequestFail(JsonResponseModel json,APIRequestException e) {
		json.Fail(response_error_msg);
		logg.error("推送失败,api访问响应出错", e);
	}

	/***
	 * api 链接出错
	 * @param json
	 * @param e
	 */
	private void jpushAPIConnectFail(JsonResponseModel json,APIConnectionException e) {
		json.Fail(connection_error_msg);
		logg.error("推送到失败,api访问连接出错", e);
	}


	/***
	 * 发送到安卓和IOS,推送的消息
	 * @param tag
	 * @param content
	 * @param title
	 */
	public  JsonResponseModel pushToIosAndAndroid (JpushDto jpush){
		JsonResponseModel json=new JsonResponseModel();
		try {
		PushPayload payload=PushMessageService.pushMessageToIosAndAndroid(jpush);
			PushResult result=jpushClient.sendPush(payload);
			if(dealResult(result)){
				savePushMessage(jpush,CommonConstant.YES);
				json.Success(push_success);
			}else{
				savePushMessage(jpush,CommonConstant.NO);
				json.Fail("推送失败");
			}
			logg.info("推送到安卓和IOS的结果是："+result);
		} catch (APIConnectionException e) {
			jpushAPIConnectFail(json, e);
		} catch (APIRequestException e) {
			jpushAPIRequestFail(json, e);
		}
		return json;
	}
	
	/***
	 * 保存推送的消息
	 * @param jpush
	 * @param isOk 
	 */
	private void savePushMessage(JpushDto jpush, String isOk) {
	SysmgrJpush pushMessage=new SysmgrJpush();
		pushMessage.setPushContent(jpush.getContent());
		pushMessage.setPushAlias(jpush.getAlias());
		pushMessage.setPushIsOk(isOk);
		pushMessage.setPushTag(jpush.getTag());
		pushMessage.setPushTime(new Date());
		pushMessage.setPushTitle(jpush.getTitle());
		pushMessage.setPushDevice(jpush.getDevice());
		pushMessage.setMessageType(jpush.getType());
		pushMessage.setPushUrl(jpush.getUrl());
		this.sysmgrJpushService.add(pushMessage);
	}


	/***
	 * 处理推送的返回结果
	 * @param result
	 * @return
	 */
	private  static boolean dealResult(PushResult result) {
	   boolean flag=false;
    	if(result.isResultOK())
    		flag= true;
    	return flag;
	}


	/***
	 * 向所有的用户发送推送信息，通知
	 * @param message
	 * @return
	 */
	@RequestMapping("push_all")
	public  JsonResponseModel sendMessageToAll(JpushDto jpush){
		JsonResponseModel json=new JsonResponseModel();
		PushPayload payload = PushMessageService.sendMessageToAll(jpush);
		try {
			PushResult result = jpushClient.sendPush(payload);
			if(dealResult(result)){
				json.Success(push_success);
				savePushMessage(jpush, CommonConstant.YES);
			}else{
				json.Fail("推送失败");
				savePushMessage(jpush, CommonConstant.NO);
			}
			logg.info("广播推送的结果"+result);
		} catch (APIConnectionException e) {
		        jpushAPIConnectFail(json, e);
		} catch (APIRequestException e) {
			    jpushAPIRequestFail(json, e);
		}
		return json;
	}
	/***
	 * 向安卓的标签用户推送消息
	 * @param 
	 * @return
	 */
	public JsonResponseModel pushToAndroid(JpushDto jpush){
		JsonResponseModel json=new JsonResponseModel();
		Map<String, String> map = setParamMap(jpush);
		try {
		PushPayload payload = PushMessageService.pushMessageToAndroid(jpush,map);
			PushResult result=jpushClient.sendPush(payload);
			if(dealResult(result)){
				json.Success("安卓用户推送成功");
				this.savePushMessage(jpush, CommonConstant.YES);
			}
			else{
				json.Fail("安卓用户推送失败");
				this.savePushMessage(jpush, CommonConstant.NO);
			}
			logg.info("安卓根据标签推送的结果"+result);
		} catch (APIConnectionException e) {
			     jpushAPIConnectFail(json, e);
		} catch (APIRequestException e) {
			     jpushAPIRequestFail(json, e);
		}  
		return json;
	}

	
}

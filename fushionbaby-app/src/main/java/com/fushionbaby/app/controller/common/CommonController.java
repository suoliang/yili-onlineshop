package com.fushionbaby.app.controller.common;

import java.rmi.RemoteException;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.aladingshop.alabao.model.AlabaoAccount;
import com.aladingshop.alabao.service.AlabaoAccountService;
import com.aladingshop.card.dto.BaseReqDto;
import com.fushionbaby.app.util.ShareConstantApp;
import com.fushionbaby.cache.DataCache;
import com.fushionbaby.common.constants.AppConstant;
import com.fushionbaby.common.constants.CommonConstant;
import com.fushionbaby.common.constants.CommonConstant.CommonMessage;
import com.fushionbaby.common.constants.GlobalConfigConstant;
import com.fushionbaby.common.constants.SmsConstant;
import com.fushionbaby.common.dto.UserDto;
import com.fushionbaby.common.enums.SendDateEnum;
import com.fushionbaby.common.security.MD5Util;
import com.fushionbaby.common.util.CheckIsEmpty;
import com.fushionbaby.common.util.RandomNumUtil;
import com.fushionbaby.common.util.jsonp.Jsonp;
import com.fushionbaby.common.util.jsonp.Jsonp_data;
import com.fushionbaby.config.model.SysmgrGlobalConfig;
import com.fushionbaby.config.service.SysmgrGlobalConfigService;
import com.fushionbaby.facade.biz.common.member.UserFacade;
import com.fushionbaby.facade.biz.common.sms.SmsServiceFacade;
import com.fushionbaby.member.model.Member;
import com.fushionbaby.member.service.MemberService;
import com.fushionbaby.sms.model.Sms;
import com.fushionbaby.sms.service.SmsService;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

/**
 * 
 * @author King 索亮
 *
 */
@Controller
@RequestMapping("/common")
public class CommonController {
	
	private static final Log LOGGER=LogFactory.getLog(CommonController.class);
	
	@Autowired
	private SysmgrGlobalConfigService globalConfig;
	
	
	@Autowired
	private UserFacade  userFacade;
	@Autowired
	private SmsServiceFacade smsServiceFacade;
	
	@Autowired
	private SmsService<Sms> smsService;
	
	@Autowired
	private AlabaoAccountService<AlabaoAccount> alabaoAccountService;
	
	@Autowired
	private MemberService<Member> memberService;
	/***
	 * 用户点击'忘记密码'或'注册'按钮时请求
	 * @return
	 * code_id
	 */
	@ResponseBody
	@RequestMapping("getRandomNum")
	public Object getRandomNum(){
		try {
			/**获取32位唯一标识码*/
			return Jsonp_data.success(RandomNumUtil.getCharacterAndNumber(AppConstant.UNIQUE_CODE));
		} catch (Exception e) {
			e.printStackTrace();
			return Jsonp.error("获取唯一标识码失败!");
		}
	}
	
	/**
	 * 
	 * @return
	 */
	@ResponseBody
	@RequestMapping("freightPrompt")
	public Object freightPrompt(){
		Integer fullCondition = globalConfig.getConfigValueByCode(GlobalConfigConstant.FREE_FREIGHT_CODE);
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("fullCondition", "满"+fullCondition+"元免运费");
		result.put("freeFreight",ShareConstantApp.FREE_FREIGHT );
		return Jsonp_data.success(result);
	}
	
	/***
	 * app 通过code 获得的一部分配置说明
	 * 
	 * @param code
	 * @return
	 */
	@RequestMapping("getGolbalValue")
	@ResponseBody
	public Object getValueByCode(@RequestParam("code") String code){
		SysmgrGlobalConfig golbalConfig=globalConfig.findByCode(code);
		if(golbalConfig!=null)
			return Jsonp_data.success(golbalConfig.getValue());
		return Jsonp_data.success("");
	}
	
	
	/***
	 * 发送确认短信
	 * @return  
	 */
	@ResponseBody
	@RequestMapping("sendSmsCode")
	public Object sendSmsCode(@RequestParam("data") String data, @RequestParam("mac") String mac){
		LOGGER.info("发送短信验证码action--请求报文：{" + data + "}");
		if (!MD5Util.validMd5(data, MD5Util.getKey(), mac)) {
			return Jsonp.error_md5();
		}
		Gson gson = new Gson();
		BaseReqDto reDto = gson.fromJson(data, BaseReqDto.class);
		if (CheckIsEmpty.isEmpty(reDto.getSid(),reDto.getSourceCode(),reDto.getSmsRandomNum(),reDto.getPhone())) {
			return Jsonp.paramError(CommonMessage.PARAM_ERROR_MESSAGE);
		}
		UserDto userDto= userFacade.getLatestUserBySid(reDto.getSid());
		if (userDto == null) {
			return Jsonp.noLoginError("请先登录");
		}
		if (smsServiceFacade.getNumByPhone(reDto.getPhone())) {
			return Jsonp.smsNumberLimit();
		}
		String smsCode = RandomNumUtil.getRandom(RandomNumUtil.NUM, SmsConstant.SMS_CODE_LENGTH);
		DataCache.put(reDto.getSmsRandomNum(), smsCode);
		LOGGER.info("手机号为："+reDto.getPhone()+"发送的验证码为："+smsCode);
		//发送验证短信
		try {
			smsService.sendAlabaoTradeMessage(reDto.getPhone(),reDto.getSourceCode(),smsCode);
		} catch (RemoteException e) {
			return Jsonp.error("发送验证短信异常");
		}
		return Jsonp.success();
	}
	
	
	/***
	 * 验证验证码是否正确
	 * @param data
	 * @param mac
	 * @return
	 */
	@RequestMapping("checkSmsCode")
	@ResponseBody
	public Object checkSmsCode(@RequestParam("data") String data, @RequestParam("mac") String mac){
		LOGGER.info("校验短信验证码 action--请求报文：{" + data + "}");
		if (!MD5Util.validMd5(data, MD5Util.getKey(), mac)) {
			return Jsonp.error_md5();
		}
		Gson gson = new Gson();
		BaseReqDto reDto = gson.fromJson(data, BaseReqDto.class);

		if (CheckIsEmpty.isEmpty(reDto.getSid(),reDto.getSourceCode(),reDto.getSmsRandomNum(),reDto.getSmsCode())) {
			return Jsonp.paramError(CommonMessage.PARAM_ERROR_MESSAGE);
		}
		UserDto userDto= userFacade.getLatestUserBySid(reDto.getSid());
		if (userDto == null) {
			return Jsonp.noLoginError("请先登录");
		}
		String code=reDto.getSmsCode();
		String oldCode = (String) DataCache.get(reDto.getSmsRandomNum());
		LOGGER.info("缓存中的验证码为："+oldCode+".请求接口传的验证码为："+code);
		if (!StringUtils.equals(code,oldCode)) {
			return Jsonp.error("验证码输入有误!");
		}
		/***删除缓存验证码*/
		//DataCache.remove(reDto.getSmsRandomNum());
		return Jsonp.success();
	}
	/***
	 * 验证如意消费卡是否存在
	 * @param data
	 * @param mac
	 * @return
	 */
	@RequestMapping("accountIsUnique")
	@ResponseBody
	public  Object checkAccountIsUnique(@RequestParam("data") String data,@RequestParam("mac") String mac){
		LOGGER.info("校验如意消费卡是否唯一 action--请求报文：{" + data + "}");
		if (!MD5Util.validMd5(data, MD5Util.getKey(), mac)) {
			return Jsonp.error_md5();
		}	
		JsonParser jsonParser=new JsonParser();
		JsonElement json = jsonParser.parse(data);
		String  phone= json.getAsJsonObject().get("phone").getAsString();
		if (CheckIsEmpty.isEmpty(phone)) {
				return Jsonp.paramError(CommonMessage.PARAM_ERROR_MESSAGE);
			}
		AlabaoAccount account = this.alabaoAccountService.findByAccount(phone);
		if(ObjectUtils.equals(account, null))
			return Jsonp_data.success(CommonConstant.YES);
		return Jsonp_data.success(CommonConstant.NO);
	}
	
	/***
	 * 验证如意消费卡是否存在
	 * @param data
	 * @param mac
	 * @return
	 */
	@RequestMapping("memberIsUnique")
	@ResponseBody
	public  Object checkMemberIsUnique(@RequestParam("data") String data,@RequestParam("mac") String mac){
		LOGGER.info("校验会员是否唯一 action--请求报文：{" + data + "}");
		if (!MD5Util.validMd5(data, MD5Util.getKey(), mac)) {
			return Jsonp.error_md5();
		}	
		JsonParser jsonParser=new JsonParser();
		JsonElement json = jsonParser.parse(data);
		String  phone= json.getAsJsonObject().get("phone").getAsString();
		if (CheckIsEmpty.isEmpty(phone)) {
			return Jsonp.paramError(CommonMessage.PARAM_ERROR_MESSAGE);
		}
		Member member=this.memberService.findByLoginName(phone);
		if(ObjectUtils.equals(member, null))
			return Jsonp_data.success(CommonConstant.YES);
		return Jsonp_data.success(CommonConstant.NO);
	}
	
	
	public static void main(String[] args) {
		System.out.println(SendDateEnum.getTitle("1"));
	}
}

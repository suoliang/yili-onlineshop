package com.fushionbaby.app.controller.alabao;

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
import com.fushionbaby.cache.DataCache;
import com.fushionbaby.cache.SessionCache;
import com.fushionbaby.common.constants.CommonConstant.CommonMessage;
import com.fushionbaby.common.constants.SmsConstant;
import com.fushionbaby.common.security.MD5Util;
import com.fushionbaby.common.util.CheckIsEmpty;
import com.fushionbaby.common.util.RandomNumUtil;
import com.fushionbaby.common.util.jsonp.Jsonp;
import com.fushionbaby.facade.biz.common.sms.SmsServiceFacade;
import com.fushionbaby.sms.model.Sms;
import com.fushionbaby.sms.service.SmsService;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

/***
 * @description 如意宝忘记登录密码和支付密码的操作
 * @author 索亮
 * @date 2015年11月13日下午1:49:59
 */
@Controller
@RequestMapping("/alabao")
public class AlabaoForgotPasswordController {
	
	private static final Log LOGGER = LogFactory.getLog(AlabaoForgotPasswordController.class);
	
	@Autowired
	private AlabaoAccountService<AlabaoAccount> alabaoAccountService;
	@Autowired
	private SmsService<Sms> smsService;
	@Autowired
	private SmsServiceFacade smsServiceFacade;
	
	/***
	 * 找回登录或支付密码 -- 获取验证码
	 * @param sid
	 * @param account
	 * @param forgetCode
	 * @param sourceCode
	 * @return
	 */
	@ResponseBody
	@RequestMapping("getLoginOrPayPwdCode")
	public Object registerGetSmsCode(
			@RequestParam(value="data",defaultValue="")String data,
			@RequestParam(value="mac",defaultValue="")String mac){
		try {
			LOGGER.info("忘记登录密码获取验证码如意宝接口action--请求报文：{" + data + "}");
			if (!MD5Util.validMd5(data, MD5Util.getKey(), mac)) {
				return Jsonp.error_md5();
			}
			JsonParser jsonParser = new JsonParser();
			JsonElement json = jsonParser.parse(data);
			String sid = json.getAsJsonObject().get("sid").getAsString();
			String account = json.getAsJsonObject().get("account").getAsString();
			String forgetCode = json.getAsJsonObject().get("forgetCode").getAsString();
			String sourceCode = json.getAsJsonObject().get("sourceCode").getAsString();
			if (ObjectUtils.equals(SessionCache.get(sid),null)) {
				return Jsonp.noLoginError("请先登录或重登APP账户");
			}
			AlabaoAccount alabaoAccount = alabaoAccountService.findByAccount(account);
			if (ObjectUtils.equals(alabaoAccount, null)) {
				return Jsonp.error("用户不存在");
			}
			if (smsServiceFacade.getNumByPhone(account)) {
				return Jsonp.smsNumberLimit();
			}
			/**获取纯数字的验证码*/
			String randomNum = RandomNumUtil.getRandom(RandomNumUtil.NUM,RandomNumUtil.UPDATE_PASSWARD_LENGTH);
			/**存放在DataCache中*/
			DataCache.put(forgetCode, randomNum);
			
			/**忘记密码发送短信验证码*/
			smsService.sendSmsCode(randomNum, account, sourceCode,SmsConstant.SMS_TYPE_FORGET_ID);
			return Jsonp.success();
		} catch (Exception e) {
			e.printStackTrace();
			LOGGER.error("如意宝找回登录密码获取验证码出错",e);
			return Jsonp.error("获取验证码出错");
		}
	}
	
	/***
	 * 找回登录密码 -- 检查验证码
	 * @param sid
	 * @param account
	 * @param forgetCode
	 * @param smsCode
	 * @return
	 */
	@ResponseBody
	@RequestMapping("checkLoginPwdCode")
	public Object checkLoginPwdCode(
			@RequestParam(value="data",defaultValue="")String data,
			@RequestParam(value="mac",defaultValue="")String mac){
		try {
			LOGGER.info("忘记登录密码检查验证码如意宝接口action--请求报文：{" + data + "}");
			if (!MD5Util.validMd5(data, MD5Util.getKey(), mac)) {
				return Jsonp.error_md5();
			}
			JsonParser jsonParser = new JsonParser();
			JsonElement json = jsonParser.parse(data);
			String sid = json.getAsJsonObject().get("sid").getAsString();
			String account = json.getAsJsonObject().get("account").getAsString();
			String forgetCode = json.getAsJsonObject().get("forgetCode").getAsString();
			String smsCode = json.getAsJsonObject().get("smsCode").getAsString();
			if (CheckIsEmpty.isEmpty(account, smsCode, forgetCode, sid)) {
				return Jsonp.paramError(CommonMessage.PARAM_ERROR_MESSAGE);
			}
			if (ObjectUtils.equals(SessionCache.get(sid),null)) {
				return Jsonp.noLoginError("请先登录或重登APP账户");
			}
			AlabaoAccount alabaoAccount = alabaoAccountService.findByAccount(account);
			if (ObjectUtils.equals(alabaoAccount, null)) {
				return Jsonp.error("用户不存在");
			}
			/**获得DataCache里的验证码的值*/
			String validSmsCode = (String) DataCache.get(forgetCode);
			if (!StringUtils.equals(smsCode, validSmsCode)) {
				return Jsonp.error("验证码输入有误!");
			}
			return Jsonp.success();
		} catch (Exception e) {
			e.printStackTrace();
			LOGGER.error("如意宝找回登录密码校验验证码出错",e);
			return Jsonp.error("校验出错");
		}
	}
	
	/***
	 * 找回登录密码 -- 设置新登录密码
	 * @param sid
	 * @param account
	 * @param forgetCode
	 * @param loginPassword
	 * @return
	 */
	@ResponseBody
	@RequestMapping("setNewLoginPwd")
	public Object setNewLoginPwd(
			@RequestParam(value="data",defaultValue="")String data,
			@RequestParam(value="mac",defaultValue="")String mac){
		try {
			LOGGER.info("忘记登录密码设置新密码如意宝接口action--请求报文：{" + data + "}");
			if (!MD5Util.validMd5(data, MD5Util.getKey(), mac)) {
				return Jsonp.error_md5();
			}
			JsonParser jsonParser = new JsonParser();
			JsonElement json = jsonParser.parse(data);
			String sid = json.getAsJsonObject().get("sid").getAsString();
			String account = json.getAsJsonObject().get("account").getAsString();
			String forgetCode = json.getAsJsonObject().get("forgetCode").getAsString();
			String loginPassword = json.getAsJsonObject().get("loginPassword").getAsString();
			if (CheckIsEmpty.isEmpty(account, loginPassword, forgetCode, sid)) {
				return Jsonp.paramError(CommonMessage.PARAM_ERROR_MESSAGE);
			}
			if (ObjectUtils.equals(SessionCache.get(sid),null)) {
				return Jsonp.noLoginError("请先登录或重登APP账户");
			}
			AlabaoAccount alabaoAccount = alabaoAccountService.findByAccount(account);
			if (ObjectUtils.equals(alabaoAccount, null)) {
				return Jsonp.error("用户不存在");
			}
			alabaoAccount.setLoginPassword(MD5Util.MD5(loginPassword+MD5Util.getPasswordkey()));
			alabaoAccountService.updateByAccount(alabaoAccount);
			/**销毁标识码*/
			DataCache.remove(forgetCode);
			return Jsonp.success();
		} catch (Exception e) {
			e.printStackTrace();
			LOGGER.error("如意宝找回登录密码校验验证码出错",e);
			return Jsonp.error("校验出错");
		}
	}
	
	/***
	 * 找回支付密码 -- 校验验证码和身份证号
	 * @param sid
	 * @param account
	 * @param forgetCode
	 * @param identityCardNo
	 * @param smsCode
	 * @return
	 */
	@ResponseBody
	@RequestMapping("checkPayPwdCode")
	public Object checkPayPwdCode(
			@RequestParam(value="data",defaultValue="")String data,
			@RequestParam(value="mac",defaultValue="")String mac){
		try {
			LOGGER.info("忘记支付密码检查验证码如意宝接口action--请求报文：{" + data + "}");
			if (!MD5Util.validMd5(data, MD5Util.getKey(), mac)) {
				return Jsonp.error_md5();
			}
			JsonParser jsonParser = new JsonParser();
			JsonElement json = jsonParser.parse(data);
			String sid = json.getAsJsonObject().get("sid").getAsString();
			String account = json.getAsJsonObject().get("account").getAsString();
			String forgetCode = json.getAsJsonObject().get("forgetCode").getAsString();
			String identityCardNo = json.getAsJsonObject().get("identityCardNo").getAsString();
			String smsCode = json.getAsJsonObject().get("smsCode").getAsString();
			if (CheckIsEmpty.isEmpty(account, smsCode, forgetCode, sid, identityCardNo)) {
				return Jsonp.paramError(CommonMessage.PARAM_ERROR_MESSAGE);
			}
			if (ObjectUtils.equals(SessionCache.get(sid),null)) {
				return Jsonp.noLoginError("请先登录或重登APP账户");
			}
			AlabaoAccount alabaoAccount = alabaoAccountService.findByAccount(account);
			if (ObjectUtils.equals(alabaoAccount, null)) {
				return Jsonp.error("用户不存在");
			}
			if (!StringUtils.equalsIgnoreCase(identityCardNo, alabaoAccount.getIdentityCardNo())) {
				return Jsonp.error("身份证号不匹配");
			}
			/**获得DataCache里的验证码的值*/
			String validSmsCode = (String) DataCache.get(forgetCode);
			if (!StringUtils.equals(smsCode, validSmsCode)) {
				return Jsonp.error("验证码输入有误!");
			}
			return Jsonp.success();
		} catch (Exception e) {
			e.printStackTrace();
			LOGGER.error("如意宝找回支付密码校验验证码出错",e);
			return Jsonp.error("校验出错");
		}
	}
	
	/***
	 * 找回支付密码 -- 设置新支付密码
	 * @param sid
	 * @param account
	 * @param forgetCode
	 * @param payPassword
	 * @return
	 */
	@ResponseBody
	@RequestMapping("setNewPayPwd")
	public Object setNewPayPwd(
			@RequestParam(value="data",defaultValue="")String data,
			@RequestParam(value="mac",defaultValue="")String mac){
		try {
			LOGGER.info("忘记登录密码设置新密码如意宝接口action--请求报文：{" + data + "}");
			if (!MD5Util.validMd5(data, MD5Util.getKey(), mac)) {
				return Jsonp.error_md5();
			}
			JsonParser jsonParser = new JsonParser();
			JsonElement json = jsonParser.parse(data);
			String sid = json.getAsJsonObject().get("sid").getAsString();
			String account = json.getAsJsonObject().get("account").getAsString();
			String forgetCode = json.getAsJsonObject().get("forgetCode").getAsString();
			String payPassword = json.getAsJsonObject().get("payPassword").getAsString();
			if (CheckIsEmpty.isEmpty(account, payPassword, forgetCode, sid)) {
				return Jsonp.paramError(CommonMessage.PARAM_ERROR_MESSAGE);
			}
			if (ObjectUtils.equals(SessionCache.get(sid),null)) {
				return Jsonp.noLoginError("请先登录或重登APP账户");
			}
			AlabaoAccount alabaoAccount = alabaoAccountService.findByAccount(account);
			if (ObjectUtils.equals(alabaoAccount, null)) {
				return Jsonp.error("用户不存在");
			}
			alabaoAccount.setPayPassword(MD5Util.MD5(payPassword+MD5Util.getPasswordkey()));
			alabaoAccountService.updateByAccount(alabaoAccount);
			/**销毁标识码*/
			DataCache.remove(forgetCode);
			return Jsonp.success();
		} catch (Exception e) {
			e.printStackTrace();
			LOGGER.error("如意宝找回登录密码校验验证码出错",e);
			return Jsonp.error("校验出错");
		}
	}
	
}

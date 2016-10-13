/**
 * 
 */
package com.fushionbaby.app.controller.yiduobao;

import java.net.URLDecoder;
import java.rmi.RemoteException;
import java.util.List;

import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.aladingshop.card.dto.BaseReqDto;
import com.aladingshop.card.dto.req.YiDuoBaoCardBackRepDto;
import com.aladingshop.card.dto.req.YiDuoBaoConfigRepDto;
import com.aladingshop.card.dto.req.YiDuoBaoCreateOrderRepDto;
import com.aladingshop.card.dto.res.YiDuoBaoConfigResDto;
import com.aladingshop.card.model.MemberCardBack;
import com.aladingshop.card.service.MemberCardBackService;
import com.fushionbaby.cache.DataCache;
import com.fushionbaby.cache.util.AlabaoSessionCache;
import com.fushionbaby.common.constants.CommonConstant.CommonMessage;
import com.fushionbaby.common.constants.SmsConstant;
import com.fushionbaby.common.dto.alabao.AlabaoUserDto;
import com.fushionbaby.common.security.MD5Util;
import com.fushionbaby.common.util.CheckIsEmpty;
import com.fushionbaby.common.util.RandomNumUtil;
import com.fushionbaby.common.util.jsonp.Jsonp;
import com.fushionbaby.common.util.jsonp.Jsonp_data;
import com.fushionbaby.facade.biz.common.yiduobao.YiDuoBaoCardFacade;
import com.fushionbaby.sms.model.Sms;
import com.fushionbaby.sms.service.SmsService;
import com.google.gson.Gson;

/**
 * @description app端益多宝接口
 * @author 孙涛
 * @date 2015年9月21日上午11:06:21
 */
@Controller
@RequestMapping("/yiduobao")
public class YiDuoBaoController {
	private static final Log LOGGER = LogFactory.getLog(YiDuoBaoController.class);

	@Autowired
	private YiDuoBaoCardFacade yiDuoBaoCardFacade;
	
	/**注入短信*/
	@Autowired
	private SmsService<Sms> smsService;
    /**退卡记录*/
	@Autowired
	private MemberCardBackService<MemberCardBack> memberCardBackService;
	/**
	 * 获取当前所有卡配置请求
	 * 
	 * @param data
	 * @param mac
	 * @return
	 */
	@ResponseBody
	@RequestMapping("getAllCardsConfig")
	public Object getAllCardsConfigReq(@RequestParam("data") String data, @RequestParam("mac") String mac) {
		try {
			LOGGER.info("获取益多宝卡配置接口action--请求报文：{" + data + "}");
			if (!MD5Util.validMd5(data, MD5Util.getKey(), mac)) {
				return Jsonp.error_md5();
			}

			Gson gson = new Gson();
			YiDuoBaoConfigRepDto reDto = gson.fromJson(data, YiDuoBaoConfigRepDto.class);

			if (CheckIsEmpty.isEmpty(reDto.getTimeStamp())) {
				return Jsonp.paramError(CommonMessage.PARAM_ERROR_MESSAGE);
			}
			List<YiDuoBaoConfigResDto> resDtos = yiDuoBaoCardFacade.getAllConfig();
			return Jsonp_data.success(resDtos);
		} catch (Exception e) {
			LOGGER.error("请求出错" + e);
			return Jsonp.error();
		}
	}

	/**
	 * 益多宝购卡下订单请求 加入一个sourceCode  新加一个 alabaoSid
	 * 
	 * @param data
	 * @param mac
	 * @return
	 */
	@ResponseBody
	@RequestMapping("createCardOrder")
	public Object createCardOrderReq(@RequestParam("data") String data, @RequestParam("mac") String mac) {
		try {
			LOGGER.info("购买益多宝下单接口action--请求报文：{" + data + "}");
			if (!MD5Util.validMd5(data, MD5Util.getKey(), mac)) {
				return Jsonp.error_md5();
			}
			Gson gson = new Gson();
			YiDuoBaoCreateOrderRepDto reDto = gson.fromJson(data, YiDuoBaoCreateOrderRepDto.class);

			if (CheckIsEmpty.isEmpty(reDto.getConfigId(), reDto.getFaceValue(), reDto.getSid())) {
				return Jsonp.paramError(CommonMessage.PARAM_ERROR_MESSAGE);
			}
			/** 判断用户有没有进行如意宝用户登录 */
			String alabaoSid = reDto.getAlabaoSid();
			AlabaoUserDto alabaoUserDto = (AlabaoUserDto) AlabaoSessionCache.get(alabaoSid);
			if (ObjectUtils.equals(alabaoUserDto, null)) {
				return Jsonp.noLoginError("您还未登录如意宝，请登录");
			}
			return yiDuoBaoCardFacade.createOrder(reDto.getConfigId(), reDto.getFaceValue(),alabaoUserDto,reDto.getSourceCode());
		} catch (Exception e) {
			LOGGER.error("请求出错" + e);
			return Jsonp.error();
		}
	}

	/**
	 * 获取当前用户益多宝列表请求
	 * 
	 * @param data
	 * @param mac
	 * @return
	 */
	@ResponseBody
	@RequestMapping("getydbList")
	public Object getydbListReq(@RequestParam("data") String data, @RequestParam("mac") String mac) {
		try {
			LOGGER.info("获取当前用户益多宝列表接口action--请求报文：{" + data + "}");
			if (!MD5Util.validMd5(data, MD5Util.getKey(), mac)) {
				return Jsonp.error_md5();
			}
			Gson gson = new Gson();
			BaseReqDto reDto = gson.fromJson(data, BaseReqDto.class);
			String alabaoSid = reDto.getAlabaoSid();
			if (CheckIsEmpty.isEmpty(alabaoSid)) {
				return Jsonp.paramError(CommonMessage.PARAM_ERROR_MESSAGE);
			}
			AlabaoUserDto alabaoUserDto = (AlabaoUserDto) AlabaoSessionCache.get(alabaoSid);
			if (ObjectUtils.equals(alabaoUserDto, null)) {
				return Jsonp.noLoginError("您还未登录如意宝，请登录");
			}
			return Jsonp_data.success(yiDuoBaoCardFacade.getCardListByMember(alabaoSid));
		} catch (Exception e) {
			LOGGER.error("请求出错" + e);
			return Jsonp.error();
		}
	}

	/**
	 * 申请退卡初始化请求
	 * 
	 * @param data
	 * @param mac
	 * @return
	 */
	@ResponseBody
	@RequestMapping("applyRefund")
	public Object backCardInitReq(@RequestParam("data") String data, @RequestParam("mac") String mac) {
		try {
			LOGGER.info("获取当前用户申请退卡初始化接口action--请求报文：{" + data + "}");
			if (!MD5Util.validMd5(data, MD5Util.getKey(), mac)) {
				return Jsonp.error_md5();
			}

			Gson gson = new Gson();
			YiDuoBaoCardBackRepDto reDto = gson.fromJson(data, YiDuoBaoCardBackRepDto.class);
			if (CheckIsEmpty.isEmpty(reDto.getSid(), reDto.getAlabaoSid(), reDto.getCardNo(), reDto.getSourceCode())) {
				return Jsonp.paramError(CommonMessage.PARAM_ERROR_MESSAGE);
			}
			String alabaoSid = reDto.getAlabaoSid();
			AlabaoUserDto alabaoUserDto = (AlabaoUserDto) AlabaoSessionCache.get(alabaoSid);
			if (ObjectUtils.equals(alabaoUserDto, null)) {
				return Jsonp.noLoginError("您还未登录如意宝，请登录");
			}
			MemberCardBack back=this.memberCardBackService.findByCardNo(reDto.getCardNo());
			if(ObjectUtils.notEqual(back, null))
				return Jsonp.error("该卡已申请过退卡");
		//	UserDto user = userFacade.getLatestUserBySid(reDto.getSid());
			return Jsonp_data.success(yiDuoBaoCardFacade.backCardInit(reDto.getCardNo(), alabaoUserDto.getMemberId()));
		} catch (Exception e) {
			LOGGER.error("请求出错" + e);
			return Jsonp.error();
		}
	}

	/**
	 * 申请退卡提交请求
	 * 
	 * @param data
	 * @param mac
	 * @return
	 */
	@ResponseBody
	@RequestMapping("submitRefund")
	public Object backCardCommitReq(@RequestParam("data") String data, @RequestParam("mac") String mac) {
		try {
			LOGGER.info("获取当前用户申请退卡提交接口action--请求报文：{" + data + "}");
			if (!MD5Util.validMd5(data, MD5Util.getKey(), mac)) {
				return Jsonp.error_md5();
			}
			Gson gson = new Gson();
			YiDuoBaoCardBackRepDto reDto = gson.fromJson(data, YiDuoBaoCardBackRepDto.class);
			if (CheckIsEmpty.isEmpty(reDto.getSid(), reDto.getAlabaoSid(), reDto.getCardNo(), reDto.getSourceCode(),
					reDto.getBankCardNo(), reDto.getBankName(), reDto.getCardHolder())) {
				return Jsonp.paramError(CommonMessage.PARAM_ERROR_MESSAGE);
			}
			// 中文转码
			reDto.setBankName(URLDecoder.decode(reDto.getBankName(), "UTF-8"));
			reDto.setCardHolder(URLDecoder.decode(reDto.getCardHolder(), "UTF-8"));
			reDto.setBankBranchName(URLDecoder.decode(reDto.getBankBranchName(), "UTF-8"));
			String alabaoSid = reDto.getAlabaoSid();
			AlabaoUserDto alabaoUserDto = (AlabaoUserDto) AlabaoSessionCache.get(alabaoSid);
			if (ObjectUtils.equals(alabaoUserDto, null)) {
				return Jsonp.noLoginError("您还未登录如意宝，请登录");
			}
			//UserDto user = userFacade.getLatestUserBySid(reDto.getSid());
			return Jsonp_data.success(yiDuoBaoCardFacade.backCardCommit(alabaoSid,reDto));
		} catch (Exception e) {
			LOGGER.error("请求出错" + e);
			return Jsonp.error();
		}
	}
	/**
	 * 删除已经退卡的会员阿拉丁卡记录
	 * 
	 * @param data
	 * @param mac
	 * @return
	 */
	@ResponseBody
	@RequestMapping("delCard")
	public Object delMemberCard(@RequestParam("data") String data, @RequestParam("mac") String mac) {
		try {
			LOGGER.info("获取当前用户删除已经推掉的卡的列表 接口action--请求报文：{" + data + "}");
			if (!MD5Util.validMd5(data, MD5Util.getKey(), mac)) {
				return Jsonp.error_md5();
			}
			Gson gson = new Gson();
			BaseReqDto reDto = gson.fromJson(data, BaseReqDto.class);

			if (CheckIsEmpty.isEmpty(reDto.getSid(), reDto.getAlabaoSid(), reDto.getCardNo(), reDto.getSourceCode())) {
				return Jsonp.paramError(CommonMessage.PARAM_ERROR_MESSAGE);
			}
			// 中文转码
			String alabaoSid = reDto.getAlabaoSid();
			AlabaoUserDto alabaoUserDto = (AlabaoUserDto) AlabaoSessionCache.get(alabaoSid);
			if (ObjectUtils.equals(alabaoUserDto, null)) {
				return Jsonp.noLoginError("您还未登录如意宝，请登录");
			}
			//UserDto user = userFacade.getLatestUserBySid(reDto.getSid());
			return Jsonp_data.success(yiDuoBaoCardFacade.delCard(reDto.getCardNo(),alabaoUserDto.getMemberId()));
		} catch (Exception e) {
			LOGGER.error("请求出错" + e);
			return Jsonp.error();
		}
	}
	/***
	 * 发送确认短信
	 * @return  
	 */
	@ResponseBody
	@RequestMapping("sendSmsCode")
	public Object sendSmsCode(@RequestParam("data") String data, @RequestParam("mac") String mac){
		LOGGER.info("阿拉丁卡退卡确认发送短信  接口action--请求报文：{" + data + "}");
		if (!MD5Util.validMd5(data, MD5Util.getKey(), mac)) {
			return Jsonp.error_md5();
		}
		Gson gson = new Gson();
		BaseReqDto reDto = gson.fromJson(data, BaseReqDto.class);

		if (CheckIsEmpty.isEmpty(reDto.getSid(), reDto.getAlabaoSid(),reDto.getSourceCode(),reDto.getSmsRandomNum())) {
			return Jsonp.paramError(CommonMessage.PARAM_ERROR_MESSAGE);
		}
		String alabaoSid = reDto.getAlabaoSid();
		AlabaoUserDto alabaoUserDto = (AlabaoUserDto) AlabaoSessionCache.get(alabaoSid);
		if (ObjectUtils.equals(alabaoUserDto, null)) {
			return Jsonp.noLoginError("您还未登录如意宝，请登录");
		}
		String smsCode = RandomNumUtil.getRandom(RandomNumUtil.NUM,SmsConstant.SMS_CODE_LENGTH);
		DataCache.put(reDto.getSmsRandomNum(), smsCode);
		//发送验证短信
		try {
			smsService.sendSmsToConfirm(alabaoUserDto.getAccount(),reDto.getSourceCode(),smsCode);
		} catch (RemoteException e) {
			LOGGER.error("YiDuoBaoController.java 发送验证短信异常", e);
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
		LOGGER.info("阿拉丁卡退卡确认发送短信验证   接口action--请求报文：{" + data + "}");
		if (!MD5Util.validMd5(data, MD5Util.getKey(), mac)) {
			return Jsonp.error_md5();
		}
		Gson gson = new Gson();
		BaseReqDto reDto = gson.fromJson(data, BaseReqDto.class);

		if (CheckIsEmpty.isEmpty(reDto.getSid(), reDto.getAlabaoSid(),reDto.getSourceCode(),reDto.getSmsRandomNum())) {
			return Jsonp.paramError(CommonMessage.PARAM_ERROR_MESSAGE);
		}
		String alabaoSid = reDto.getAlabaoSid();
		AlabaoUserDto alabaoUserDto = (AlabaoUserDto) AlabaoSessionCache.get(alabaoSid);
		if (ObjectUtils.equals(alabaoUserDto, null)) {
			return Jsonp.noLoginError("您还未登录如意宝，请登录");
		}
		String code=reDto.getSmsCode();
		String oldCode = (String) DataCache.get(reDto.getSmsRandomNum());
		if (!StringUtils.equals(code,oldCode)) {
			return Jsonp.error("验证码输入有误!");
		}
		return Jsonp.success();
	}
	
}

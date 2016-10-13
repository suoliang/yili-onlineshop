package com.fushionbaby.app.controller.alabao;

import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.net.URLDecoder;
import java.util.Date;
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

import com.aladingshop.alabao.model.AlabaoAccount;
import com.aladingshop.alabao.service.AlabaoAccountService;
import com.aladingshop.alabao.vo.OpenAlabaoDto;
import com.aladingshop.alabao.vo.RegisterAlabaoDto;
import com.fushionbaby.cache.DataCache;
import com.fushionbaby.cache.SessionCache;
import com.fushionbaby.cache.util.AlabaoSessionCache;
import com.fushionbaby.common.constants.AppConstant;
import com.fushionbaby.common.constants.CommonConstant;
import com.fushionbaby.common.constants.CommonConstant.CommonMessage;
import com.fushionbaby.common.constants.GlobalConfigConstant;
import com.fushionbaby.common.constants.SmsConstant;
import com.fushionbaby.common.constants.VerificationConstant;
import com.fushionbaby.common.constants.alabao.AlabaoAccountConstant;
import com.fushionbaby.common.dto.UserDto;
import com.fushionbaby.common.dto.alabao.AlabaoUserDto;
import com.fushionbaby.common.dto.verification.IDVerificationReqDto;
import com.fushionbaby.common.dto.verification.VerificationReqDto;
import com.fushionbaby.common.dto.verification.VerificationResDto;
import com.fushionbaby.common.security.MD5Util;
import com.fushionbaby.common.util.CheckIsEmpty;
import com.fushionbaby.common.util.CheckObjectIsNull;
import com.fushionbaby.common.util.RandomNumUtil;
import com.fushionbaby.common.util.date.DateFormat;
import com.fushionbaby.common.util.jsonp.Jsonp;
import com.fushionbaby.common.util.jsonp.Jsonp_data;
import com.fushionbaby.config.model.SysmgrGlobalConfig;
import com.fushionbaby.config.service.SysmgrGlobalConfigService;
import com.fushionbaby.core.condition.ShoppingCartQueryCondition;
import com.fushionbaby.core.model.ShoppingCartSku;
import com.fushionbaby.core.service.ShoppingCartSkuUserService;
import com.fushionbaby.facade.biz.common.verification.IDVerificationFacade;
import com.fushionbaby.member.model.Member;
import com.fushionbaby.member.model.VerificationRecord;
import com.fushionbaby.member.service.MemberService;
import com.fushionbaby.member.service.VerificationRecordService;
import com.fushionbaby.sku.model.SkuExtraInfo;
import com.fushionbaby.sku.model.SkuPrice;
import com.fushionbaby.sku.service.SkuExtraInfoService;
import com.fushionbaby.sku.service.SkuPriceService;
import com.fushionbaby.sms.model.Sms;
import com.fushionbaby.sms.service.SmsService;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

/**
 * @description 阿拉宝账户信息
 * @author 索亮
 * @date 2015年9月9日上午11:54:15
 */
@Controller
@RequestMapping("/alabao")
public class AlabaoAccountController {
	private static final Log LOGGER = LogFactory.getLog(AlabaoAccountController.class);
	
	@Autowired
	private AlabaoAccountService<AlabaoAccount> alabaoAccountService;
	@Autowired
	private SmsService<Sms> smsService;
	
	@Autowired
	private ShoppingCartSkuUserService<ShoppingCartSku> shoppingCartSkuUserService;
	@Autowired
	private SkuExtraInfoService<SkuExtraInfo> skuExtraInfoService;
	@Autowired
	private SkuPriceService<SkuPrice> skuPriceService;
	/**身份认证（姓名和身份证号）*/
	@Autowired
	private IDVerificationFacade iDVerificationFacade;

	/**商城会员*/
	@Autowired
	private MemberService<Member> memberService;
	
	/**验证记录*/
	@Autowired
	private VerificationRecordService<VerificationRecord> verificationRecordService;
	
	/**全局配置*/
	@Autowired
	private SysmgrGlobalConfigService sysmgrGlobalConfigService;
	/***
	 * 如意宝-注册--请求短信验证码
	 * @param sid
	 * @param phone
	 * @param registerCode
	 * @param sourceCode
	 * @return
	 */
	@ResponseBody
	@RequestMapping("getAlabaoSmsCode")
	public Object registerGetSmsCode(
			@RequestParam(value="data",defaultValue="")String data,
			@RequestParam(value="mac",defaultValue="")String mac){
		try {
			LOGGER.info("注册如意宝接口action--请求报文：{" + data + "}");
			if (!MD5Util.validMd5(data, MD5Util.getKey(), mac)) {
				return Jsonp.error_md5();
			}
			Gson gson = new Gson();
			RegisterAlabaoDto registerAlabaoDto = gson.fromJson(data, RegisterAlabaoDto.class);
			
			if (CheckIsEmpty.isEmpty(registerAlabaoDto.getSid(),registerAlabaoDto.getPhone(),registerAlabaoDto.getRegisterCode(),registerAlabaoDto.getSourceCode())) {
				return Jsonp.paramError(CommonMessage.PARAM_ERROR_MESSAGE);
			}
			UserDto userDto = (UserDto) SessionCache.get(registerAlabaoDto.getSid());
			if(ObjectUtils.equals(null, userDto)){
				return Jsonp.noLoginError("请先登录或重登APP");
			}
			/**此处限制一个APP用户只能绑一个账户*/
			if (ObjectUtils.notEqual(alabaoAccountService.findByMemberId(userDto.getMemberId()), null)) {
				return Jsonp.error("此APP用户已绑定");
			}
			if (ObjectUtils.notEqual(alabaoAccountService.findByAccount(registerAlabaoDto.getPhone()), null)) {
				return Jsonp.error("此手机号已注册,您可直接登录!");
			}
			
			/**获取纯数字的验证码*/
			String registerRandomNum = RandomNumUtil.getRandom(RandomNumUtil.NUM, RandomNumUtil.REGISTER_USER_LENGTH);
			DataCache.put(registerAlabaoDto.getRegisterCode(), registerRandomNum);
			try {
				//注册发送的短信验证码
				smsService.sendSmsCode(registerRandomNum,registerAlabaoDto.getPhone(),registerAlabaoDto.getSourceCode(),SmsConstant.SMS_TYPE_REGISTER_ID);
			} catch (Exception e) {
				e.printStackTrace();
				return Jsonp.error("短信服务器响应超时");
			}
			return Jsonp.success();
		} catch (Exception e) {
			e.printStackTrace();
			LOGGER.error("请求出错" + e);
			return Jsonp.error();
		}
	}
	
	/**
	 * 开通我的如意宝
	 * @param sid
	 * @param account
	 * @param loginPassword
	 * @param payPassword
	 * @param trueName
	 * @param identityCardNo
	 * @param registerCode
	 * @param sourceCode
	 * @param smsCode
	 * @return
	 */
	@ResponseBody
	@RequestMapping("openAlabao")
	public Object openAlabao(@RequestParam("data") String data,
			@RequestParam("mac") String mac){
		LOGGER.info("开通如意宝接口action--start");
		LOGGER.info("开通如意宝接口action--请求报文：{" + data + "}");
		try {
			if (!MD5Util.validMd5(data, MD5Util.getKey(), mac)) {
				return Jsonp.error_md5();
			}
			// 解析data
			Gson gson = new Gson();
			OpenAlabaoDto openAlabaoDto = gson.fromJson(data, OpenAlabaoDto.class);
			if (CheckIsEmpty.isEmpty(openAlabaoDto.getSid())) {
				return Jsonp.paramError(CommonMessage.PARAM_ERROR_MESSAGE);
			}
			UserDto user = (UserDto) SessionCache.get(openAlabaoDto.getSid());
			if (CheckObjectIsNull.isNull(user)) {
				return Jsonp.noLoginError("请先登录或重新登录");
			}
			/**获得DataCache里的验证码的值*/
			String randomSmsCode = (String) DataCache.get(openAlabaoDto.getRegisterCode());
			if (!StringUtils.equals(openAlabaoDto.getSmsCode(),randomSmsCode)) {
				return Jsonp.error("验证码输入有误!");
			}
			if (ObjectUtils.notEqual(alabaoAccountService.findByAccount(openAlabaoDto.getAccount()), null)) {
				return Jsonp.error("此手机号已注册,您可直接登录!");
			}
			if (ObjectUtils.notEqual(alabaoAccountService.findByIDCard(openAlabaoDto.getIdentityCardNo()), null)) {
				return Jsonp.error("身份证号已绑定");
			}
			AlabaoAccount alabaoAccount = new AlabaoAccount();
			alabaoAccount.setAccount(openAlabaoDto.getAccount());
			alabaoAccount.setLoginPassword(MD5Util.MD5(openAlabaoDto.getLoginPassword()+MD5Util.getPasswordkey()));
			alabaoAccount.setPayPassword(MD5Util.MD5(openAlabaoDto.getPayPassword()+MD5Util.getPasswordkey()));
			alabaoAccount.setMemberId(user.getMemberId());
			alabaoAccount.setMobilePhone(openAlabaoDto.getAccount());
			String trueName = openAlabaoDto.getTrueName();
			LOGGER.info("解码前真实姓名:"+trueName);
			trueName = URLDecoder.decode(trueName, "UTF-8");
			LOGGER.info("解码后真实姓名:"+trueName);
			alabaoAccount.setTrueName(trueName);
			alabaoAccount.setIdentityCardNo(openAlabaoDto.getIdentityCardNo());
			alabaoAccount.setSourceCode(openAlabaoDto.getSourceCode());
			alabaoAccount.setLockedBalance(BigDecimal.ZERO);
			alabaoAccount.setBalance(BigDecimal.ZERO);
			alabaoAccount.setYesterdayIncome(BigDecimal.ZERO);
			alabaoAccount.setTotalIncome(BigDecimal.ZERO);
			
			alabaoAccount.setStatus(AlabaoAccountConstant.STATUS_PASS);//此处默认审核通过
			alabaoAccount.setLevel(AlabaoAccountConstant.LEVEL_GENGRAL);//普通用户
			alabaoAccountService.add(alabaoAccount);
			this.modifyCartSkuPriceByVip(user.getMemberId());
			/**销毁注册标识码*/
			DataCache.remove(openAlabaoDto.getRegisterCode());
			return Jsonp.success();
		} catch (Exception e) {
			e.printStackTrace();
			LOGGER.error("开通我的如意宝出错", e);
			return Jsonp.error("开通出错");
		}
	}
	
	
	/*到购物车中 修改VIP会员商品的价格*/
	private void modifyCartSkuPriceByVip(Long memberId){
		
		AlabaoAccount alabaoAccount= alabaoAccountService.findByMemberId(memberId);
		if(alabaoAccount==null||!StringUtils.equals("3",alabaoAccount.getLevel())){
			return;
		}
		ShoppingCartQueryCondition cartQueryCondition = new ShoppingCartQueryCondition();
		cartQueryCondition.setMemberId(memberId);
		List<ShoppingCartSku>  cartList = shoppingCartSkuUserService.findByQueryCondition(cartQueryCondition);
		if(cartList==null){
			return ;
		}
		for (ShoppingCartSku cartSku : cartList) {
			
			SkuExtraInfo extraInfo = skuExtraInfoService.findBySkuCode(cartSku.getSkuCode());
			if(extraInfo==null || !StringUtils.equals(extraInfo.getIsMemberSku(),CommonConstant.YES)){
				continue;
			}
			SkuPrice skuPrice = skuPriceService.findBySkuCode(cartSku.getSkuCode());
			if(skuPrice==null || skuPrice.getAladingPrice()==null){
				continue;
			}
			cartSku.setCurrentPrice(skuPrice.getAladingPrice());
			cartSku.setLineTotalPrice(skuPrice.getAladingPrice().multiply(BigDecimal.valueOf(cartSku.getQuantity())));
			shoppingCartSkuUserService.update(cartSku);
		}
		
	}
	
	
	/***
	 * 身份认证（验证阿拉宝账户是否真实姓名和身份证号一致）
	 * @param data
	 * @param mac
	 * @return
	 */
	
	@RequestMapping("verificationID")
	@ResponseBody
	public Object verificationID(@RequestParam(value="data")String data,@RequestParam(value="mac")String mac){
		LOGGER.info("如意消费卡姓名身份证验证   信息验证  action--请求报文：{" + data + "}");
		try {
			if (!MD5Util.validMd5(data, MD5Util.getKey(), mac)) {
				return Jsonp.error_md5();
			}
			Gson gson = new Gson();
			VerificationReqDto req = gson.fromJson(data, VerificationReqDto.class);
			if (CheckIsEmpty.isEmpty(req.getAlabaoSid(),req.getID(),req.getTrueName())) {
				return Jsonp.paramError(CommonMessage.PARAM_ERROR_MESSAGE);
			}
			LOGGER.info("解码前真实姓名:"+req.getTrueName());
			String trueName = URLDecoder.decode(req.getTrueName(), "UTF-8");
			LOGGER.info("解码后真实姓名:"+trueName);
			AlabaoUserDto alabaoUserDto = (AlabaoUserDto) AlabaoSessionCache.get(req.getAlabaoSid());
			if (ObjectUtils.equals(alabaoUserDto, null)) {
				return Jsonp.noLoginError("未登录或重新登录");
			}
			AlabaoAccount  alabao=  alabaoAccountService.findByAccount(alabaoUserDto.getAccount());
			if(alabao==null){
				return Jsonp.error("您的账户不存在");
			}
			LOGGER.info(DateFormat.dateToString(new Date())+"身份验证开始       ***********    ID为： "+req.getID()+"姓名为："+req.getTrueName());
			String flag=CommonConstant.NO;
			SysmgrGlobalConfig globalConfig = sysmgrGlobalConfigService.findByCode(VerificationConstant.OPEN_OR_CLOSE_VERIFICATION_ID);
			if(globalConfig!=null&&StringUtils.isNotBlank(globalConfig.getValue())){
				flag=globalConfig.getValue();
			}
			
			if(CommonConstant.YES.equals(flag)){
				/**已经验证过*/
				if(VerificationConstant.IS_VALIDATE_STATUS_SUCCESS.equals(alabao.getIsValidate()))
					return Jsonp_data.success("您已经进行过认证");
				AlabaoAccount accounTemp=this.alabaoAccountService.findByIDCard(req.getID());
				if(ObjectUtils.notEqual(accounTemp, null)&&!alabao.getAccount().equals(accounTemp.getAccount())){
					Jsonp.error("该身份证已被其他账户验证过，请重试其他身份证号");
				}
				List<VerificationRecord> recordList=this.verificationRecordService.findByBankCardNoAndID("", req.getID(), trueName);
				if(recordList!=null&&recordList.size()-VerificationConstant.VERIFICATION_MAX_TIMES_ONEDAY>0){
					return Jsonp.error("认证已超三次，请联系客服");
				}
				IDVerificationReqDto reqDto=new IDVerificationReqDto(trueName, req.getID());
				VerificationResDto res=this.iDVerificationFacade.getIDVerificationInfo(reqDto);
				if(VerificationConstant.VALIDATE_SUCCESS_RESPONSECODE.equals(res.getCode())){
					this.alabaoAccountService.updateIsValidate(trueName, req.getID(),alabaoUserDto.getAccount(), true);
					return Jsonp_data.success("认证成功");
				}else{
					this.alabaoAccountService.updateIsValidate(trueName,req.getID(), alabaoUserDto.getAccount(),false);
					return Jsonp.error("信息认证失败");
				}
			}
			LOGGER.info(DateFormat.dateToString(new Date())+"身份验证结束       *********** 开始更新 如意消费卡账户");
		    saveAlabaoUserDto(this.alabaoAccountService.findByAccount(alabaoUserDto.getAccount()),req.getAlabaoSid());
		    LOGGER.info(DateFormat.dateToString(new Date())+"如意消费卡账户更新结束    ***********");
			return Jsonp.success();
		} catch (Exception e) {
			LOGGER.error("app:AlabaoAccountController.java 身份验证异常", e);
			return Jsonp.error("信息验证异常");	
		}
	}

	
	
	
	/***
	 * 绑定手机号 (第三方用户绑定)
	 * @param data
	 * @param mac
	 * @return
	 */
	@RequestMapping("binding")
	@ResponseBody
	public Object binding(@RequestParam(value="data",defaultValue="")String data,
			@RequestParam(value="mac",defaultValue="")String mac){
		if (!MD5Util.validMd5(data, MD5Util.getKey(), mac)) {
			return Jsonp.error_md5();
		}
		Gson gson = new Gson();
		RegisterAlabaoDto req = gson.fromJson(data, RegisterAlabaoDto.class);
		if (CheckIsEmpty.isEmpty(req.getSid(),req.getPhone())) {
			return Jsonp.paramError(CommonMessage.PARAM_ERROR_MESSAGE);
		}
		UserDto user = (UserDto) SessionCache.get(req.getSid());
		if (CheckObjectIsNull.isNull(user)) {
			return Jsonp.noLoginError("请先登录或重新登录");
		}
		Member member2=this.memberService.findByLoginName(req.getPhone());
		if(member2!=null)
			return Jsonp.error("该手机号已被绑定过");
		Member member=this.memberService.findById(user.getMemberId());
		if(member==null)
			return Jsonp.error("商城会员不存在");
		
	      member.setLoginName(req.getPhone());
	      member.setIsBind(CommonConstant.YES);
		this.memberService.update(member);
		
		return Jsonp.success();
	}
	
	
	
	/**
	 * 开通我的如意宝
	 * @param sid
	 * @param account
	 * @param payPassword
	 * @param trueName
	 * @param identityCardNo
	 * @param sourceCode
	 * @return
	 */
	@ResponseBody
	@RequestMapping("openAlabaoCommon")
	public Object openAlabaoCommon(@RequestParam("data") String data,@RequestParam("mac") String mac){
		LOGGER.info("登陆用户  开通如意宝接口action--start");
		LOGGER.info("登陆用户 开通如意宝接口action--请求报文：{" + data + "}");
		try {
			if (!MD5Util.validMd5(data, MD5Util.getKey(), mac)) {
				return Jsonp.error_md5();
			}	
			// 解析data
			Gson gson = new Gson();
			OpenAlabaoDto openAlabaoDto = gson.fromJson(data, OpenAlabaoDto.class);
			if (CheckIsEmpty.isEmpty(openAlabaoDto.getSid(),openAlabaoDto.getAccount(),openAlabaoDto.getTrueName(),openAlabaoDto.getIdentityCardNo(),openAlabaoDto.getSourceCode())) {
				return Jsonp.paramError(CommonMessage.PARAM_ERROR_MESSAGE);
			}
			UserDto user = (UserDto) SessionCache.get(openAlabaoDto.getSid());
			if (CheckObjectIsNull.isNull(user)) {
				return Jsonp.noLoginError("请先登录或重新登录");
			}
			AlabaoAccount alabaoAccount=alabaoAccountService.findByAccount(openAlabaoDto.getAccount());
			if(ObjectUtils.notEqual(alabaoAccount, null))
				return Jsonp.error("该手机号已开通，您可直接登录!");
			//此处数据可能有问题
		    alabaoAccount=alabaoAccountService.findByIDCard(openAlabaoDto.getIdentityCardNo());
				if (ObjectUtils.notEqual(alabaoAccount,null)){
					return Jsonp.error("该身份证号已绑定，请确认");
				}
			alabaoAccount=  saveAlabaoAccount(openAlabaoDto, user);
			this.modifyCartSkuPriceByVip(user.getMemberId());
			AlabaoUserDto alabaoUserDto = saveAlabaoUserDto(alabaoAccount,"");
			return Jsonp_data.success(alabaoUserDto);
		} catch (Exception e) {
			e.printStackTrace();
			LOGGER.error("登陆用户  开通我的如意宝出错", e);
			return Jsonp.error("开通申请异常");
		}
	}

	/***
	 * 设置阿拉宝的dto对象
	 * @param alabaoAccount
	 * @param alabaoSid 
	 * @return
	 */
	private AlabaoUserDto saveAlabaoUserDto(AlabaoAccount alabaoAccount, String alabaoSid) {
		AlabaoUserDto alabaoUserDto=new AlabaoUserDto();
		if(StringUtils.isBlank(alabaoSid))
		  alabaoSid=RandomNumUtil.getCharacterAndNumber(AppConstant.UNIQUE_CODE);
		alabaoUserDto.setAccount(alabaoAccount.getAccount());
		alabaoUserDto.setAlabaoId(alabaoAccount.getId());
		alabaoUserDto.setAlabaoSid(alabaoSid);
		alabaoUserDto.setID(alabaoAccount.getIdentityCardNo());
		alabaoUserDto.setIsValidate(VerificationConstant.IS_VALIDATE_STATUS_SUCCESS.equals(alabaoAccount.getIsValidate())?CommonConstant.YES:CommonConstant.NO);
		alabaoUserDto.setMemberId(alabaoAccount.getMemberId());
		alabaoUserDto.setMemberName(alabaoAccount.getTrueName());
		AlabaoSessionCache.put(alabaoSid, new Gson().toJson(alabaoUserDto));
		 /**如意消费卡 alabaoSid和账户关联*/
		AlabaoSessionCache.put(AppConstant.APP_ALABAO_SID+alabaoAccount.getAccount(), alabaoSid);
		return alabaoUserDto;
	}

	/***
	 * 保存如意消费卡账号信息
	 * @param openAlabaoDto
	 * @param user
	 * @return 
	 * @throws UnsupportedEncodingException
	 */
	private AlabaoAccount saveAlabaoAccount(OpenAlabaoDto openAlabaoDto, UserDto user)throws UnsupportedEncodingException {
		AlabaoAccount alabaoAccount = new AlabaoAccount();
		alabaoAccount.setAccount(openAlabaoDto.getAccount());
		//alabaoAccount.setLoginPassword(MD5Util.MD5(openAlabaoDto.getLoginPassword()+MD5Util.getPasswordkey()));
		alabaoAccount.setPayPassword(MD5Util.MD5(openAlabaoDto.getPayPassword()+MD5Util.getPasswordkey()));
		alabaoAccount.setMemberId(user.getMemberId());
		alabaoAccount.setMobilePhone(openAlabaoDto.getAccount());
		String trueName = openAlabaoDto.getTrueName();
		LOGGER.info("解码前真实姓名:"+trueName);
		trueName = URLDecoder.decode(trueName, "UTF-8");
		LOGGER.info("解码后真实姓名:"+trueName);
		alabaoAccount.setTrueName(trueName);
		alabaoAccount.setMemberName(trueName);
		alabaoAccount.setIdentityCardNo(openAlabaoDto.getIdentityCardNo());
		alabaoAccount.setSourceCode(openAlabaoDto.getSourceCode());
		alabaoAccount.setLockedBalance(BigDecimal.ZERO);
		alabaoAccount.setBalance(BigDecimal.ZERO);
		alabaoAccount.setYesterdayIncome(BigDecimal.ZERO);
		alabaoAccount.setTotalIncome(BigDecimal.ZERO);
		alabaoAccount.setStatus(AlabaoAccountConstant.STATUS_PASS);//此处默认审核通过
		alabaoAccount.setLevel(AlabaoAccountConstant.LEVEL_GENGRAL);//普通用户
		alabaoAccount.setIsValidate(VerificationConstant.IS_VALIDATE_STATUS_NULL);
		alabaoAccountService.add(alabaoAccount);
		AlabaoAccount alabaoAccount2=this.alabaoAccountService.findByAccount(openAlabaoDto.getAccount());
		return alabaoAccount2;
	}
	
	
	/***
	 * 得到 如意消费卡个人资料
	 * @param data
	 * @param mac
	 * @return
	 */
	@RequestMapping("getAlabaoInfo")
	@ResponseBody
	public Object getAlabaoInfo(@RequestParam("data") String data,@RequestParam("mac") String mac){
		if (!MD5Util.validMd5(data, MD5Util.getKey(), mac)) {
			return Jsonp.error_md5();
		}	
        JsonParser jsonParser=new JsonParser();
		JsonElement json = jsonParser.parse(data);
		String alabaoSid = json.getAsJsonObject().get("alabaoSid").getAsString();
		String sid = json.getAsJsonObject().get("sid").getAsString();
		if (CheckIsEmpty.isEmpty(alabaoSid)) {
				return Jsonp.paramError(CommonMessage.PARAM_ERROR_MESSAGE);
			}
		UserDto user = (UserDto) SessionCache.get(sid);
		if (CheckObjectIsNull.isNull(user)) {
			return Jsonp.noLoginError("请先登录或重新登录");
		}
		AlabaoUserDto alabaoUserDto = (AlabaoUserDto) AlabaoSessionCache.get(alabaoSid);
		if (ObjectUtils.equals(alabaoUserDto, null)) {
			return Jsonp.noLoginError("未登录或重新登录");
		}
		AlabaoAccount account=this.alabaoAccountService.findByAccount(alabaoUserDto.getAccount());
		if(ObjectUtils.equals(null, account))
			return Jsonp.error("账户查询异常");
		alabaoUserDto.setID(account.getIdentityCardNo());
		alabaoUserDto.setIsValidate(VerificationConstant.IS_VALIDATE_STATUS_SUCCESS.equals(account.getIsValidate())?CommonConstant.YES:CommonConstant.NO);
		alabaoUserDto.setMemberName(account.getTrueName());		
		return Jsonp_data.success(alabaoUserDto);
	}
	
	/***
	 * 得到 如意消费卡开通说明字符串
	 * @param data
	 * @param mac
	 * @return
	 */
	@RequestMapping("desc")
	@ResponseBody
	public Object Desc(@RequestParam("data") String data,@RequestParam("mac") String mac){
		if (!MD5Util.validMd5(data, MD5Util.getKey(), mac)) {
			return Jsonp.error_md5();
		}	
        JsonParser jsonParser=new JsonParser();
		JsonElement json = jsonParser.parse(data);
		String sid = json.getAsJsonObject().get("sid").getAsString();
		UserDto user = (UserDto) SessionCache.get(sid);
		if (CheckObjectIsNull.isNull(user)) {
			return Jsonp.noLoginError("请先登录或重新登录");
		}
		
		SysmgrGlobalConfig config = this.sysmgrGlobalConfigService.findByCode(GlobalConfigConstant.OPEN_ALABAO_TIPS);
		String message=GlobalConfigConstant.OPEN_ALABAO_TIPS_MESSAGE;
		if(ObjectUtils.notEqual(config, null))
			 message=config.getValue();
		return Jsonp_data.success(message);
	}
}

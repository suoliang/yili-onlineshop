package com.fushionbaby.app.controller.alabao;

import java.net.URLDecoder;

import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.aladingshop.alabao.model.AlabaoAccountBank;
import com.aladingshop.alabao.model.AlabaoBankConfig;
import com.aladingshop.alabao.service.AlabaoAccountBankService;
import com.aladingshop.alabao.service.AlabaoBankConfigService;
import com.aladingshop.alabao.vo.AddAccountBandDto;
import com.fushionbaby.cache.util.AlabaoSessionCache;
import com.fushionbaby.common.constants.CommonConstant;
import com.fushionbaby.common.dto.alabao.AlabaoUserDto;
import com.fushionbaby.common.security.MD5Util;
import com.fushionbaby.common.util.jsonp.Jsonp;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
/***
 * @description 如意宝转出到银行卡编辑银行卡信息
 * @author 索亮
 * @date 2015年11月14日下午3:12:21
 */
@Controller
@RequestMapping("/alabao")
public class AlabaoEditBankController {
	
	private static final Log LOGGER = LogFactory.getLog(AlabaoEditBankController.class);
	
	@Autowired
	private AlabaoBankConfigService<AlabaoBankConfig> alabaoBankConfigService;
	@Autowired
	private AlabaoAccountBankService<AlabaoAccountBank> alabaoAccountBankService;
	
	/**
	 * 用户的银行卡信息 -- 删除
	 * @param alabaoSid
	 * @param accountBankId
	 * @return
	 */
	@ResponseBody
	@RequestMapping("deleteUserBank")
	public Object deleteUserBank(
			@RequestParam(value="data")String data,
			@RequestParam(value="mac")String mac){
		try {
			LOGGER.info("删除用户银行卡列表如意宝接口action--请求报文：{" + data + "}");
			if (!MD5Util.validMd5(data, MD5Util.getKey(), mac)) {
				return Jsonp.error_md5();
			}
			JsonParser jsonParser = new JsonParser();
			JsonElement json = jsonParser.parse(data);
			String alabaoSid = json.getAsJsonObject().get("alabaoSid").getAsString();
			String accountBankId = json.getAsJsonObject().get("accountBankId").getAsString();
			AlabaoUserDto alabaoUserDto = (AlabaoUserDto) AlabaoSessionCache.get(alabaoSid);
			if (ObjectUtils.equals(alabaoUserDto, null)) {
				return Jsonp.noLoginError("未登录或重新登录");
			}
			AlabaoAccountBank accountBank = alabaoAccountBankService.findById(Long.valueOf(accountBankId));
			if (ObjectUtils.equals(accountBank, null)) {
				return Jsonp.error("无此记录,联系客服");
			}
			/**逻辑删除用户卡信息*/
			accountBank.setIsDelete(CommonConstant.YES);
			alabaoAccountBankService.update(accountBank);
			return Jsonp.success();
		} catch (Exception e) {
			e.printStackTrace();
			LOGGER.error("删除用户银行卡接口出错", e);
			return Jsonp.error("删除卡信息出错");
		}
	}
	
	/**
	 * 用户的银行卡信息 -- 修改
	 * @param alabaoSid
	 * @param accountBankId
	 * @param bankName
	 * @param cardNo
	 * @param cardHolder
	 * @param bankBranchName
	 * @param isEditCardNo(y/n)有没有修改过卡号
	 * @return
	 */
	@ResponseBody
	@RequestMapping("updateUserBank")
	public Object updateUserBank(
			@RequestParam(value="data")String data,
			@RequestParam(value="mac")String mac){
		try {
			LOGGER.info("修改卡信息如意宝接口action--请求报文：{" + data + "}");
			if (!MD5Util.validMd5(data, MD5Util.getKey(), mac)) {
				return Jsonp.error_md5();
			}
			Gson gson = new Gson();
			AddAccountBandDto addAccountBandDto = gson.fromJson(data, AddAccountBandDto.class);
			
			AlabaoUserDto alabaoUserDto = (AlabaoUserDto) AlabaoSessionCache.get(addAccountBandDto.getAlabaoSid());
			if (ObjectUtils.equals(alabaoUserDto, null)) {
				return Jsonp.noLoginError("未登录或重新登录");
			}
			/**如果修改过银行卡卡号*/
			if (StringUtils.endsWithIgnoreCase(addAccountBandDto.getIsEditCardNo(), CommonConstant.YES)) {
				if (ObjectUtils.notEqual(alabaoAccountBankService.findByBankCardNo(addAccountBandDto.getCardNo(),CommonConstant.NO), null)) {
					return Jsonp.error("此银行卡已绑定");
				}
			}
			String bankName = addAccountBandDto.getBankName();
			String cardHolder = addAccountBandDto.getCardHolder();
			String bankBranchName=addAccountBandDto.getBankBranchName();
			Long accountBankId = addAccountBandDto.getAccountBankId();
			String province=addAccountBandDto.getProvince();
			String city=addAccountBandDto.getCity();
			LOGGER.info("转码前银行名称:"+bankName+"转码前持卡人姓名:"+cardHolder);
			bankName = URLDecoder.decode(bankName, "UTF-8");
			cardHolder = URLDecoder.decode(cardHolder, "UTF-8");
			bankBranchName = URLDecoder.decode(bankBranchName, "UTF-8");
			LOGGER.info("解码后银行名称:"+bankName+"解码后持卡人姓名:"+cardHolder);
			AlabaoAccountBank alabaoAccountBank = new AlabaoAccountBank();
			alabaoAccountBank.setAccount(alabaoUserDto.getAccount());
			alabaoAccountBank.setMemberId(alabaoUserDto.getMemberId());
			alabaoAccountBank.setBankName(bankName);
			alabaoAccountBank.setBankBranchName(bankBranchName);
			alabaoAccountBank.setCardNo(addAccountBandDto.getCardNo());
			alabaoAccountBank.setCardHolder(cardHolder);
			alabaoAccountBank.setIsDelete(CommonConstant.NO);
			alabaoAccountBank.setId(accountBankId);
			alabaoAccountBank.setProvince(province);
			alabaoAccountBank.setCity(city);
			
			alabaoAccountBankService.update(alabaoAccountBank);
			return Jsonp.success();
		} catch (Exception e) {
			e.printStackTrace();
			LOGGER.error("修改卡信息接口出错", e);
			return Jsonp.error("修改卡信息出错");
		}
	}
	
}

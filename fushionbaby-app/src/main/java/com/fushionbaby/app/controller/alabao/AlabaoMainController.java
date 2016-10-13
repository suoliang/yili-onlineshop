package com.fushionbaby.app.controller.alabao;

import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.aladingshop.alabao.model.AlabaoAccount;
import com.aladingshop.alabao.service.AlabaoAccountService;
import com.aladingshop.alabao.vo.MainAlabaoDto;
import com.fushionbaby.cache.util.AlabaoSessionCache;
import com.fushionbaby.common.constants.CommonConstant;
import com.fushionbaby.common.constants.VerificationConstant;
import com.fushionbaby.common.dto.alabao.AlabaoUserDto;
import com.fushionbaby.common.security.MD5Util;
import com.fushionbaby.common.util.BeanNullConverUtil;
import com.fushionbaby.common.util.NumberFormatUtil;
import com.fushionbaby.common.util.jsonp.Jsonp;
import com.fushionbaby.common.util.jsonp.Jsonp_data;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

/**
 * @description 阿拉宝主信息展示
 * @author 索亮
 * @date 2015年9月9日下午2:57:22
 */
@Controller
@RequestMapping("/alabao")
public class AlabaoMainController {
	private static final Log LOGGER = LogFactory.getLog(AlabaoMainController.class);
	
	@Autowired
	private AlabaoAccountService<AlabaoAccount> alabaoAccountService;
	
	/***
	 * @param alabaoSid
	 * @return
	 */
	@ResponseBody
	@RequestMapping("mainShow")
	public Object mainShow(
			@RequestParam(value="data",defaultValue="")String data,
			@RequestParam(value="mac",defaultValue="")String mac){
		try {
			LOGGER.info("主显如意宝接口action--请求报文：{" + data + "}");
			if (!MD5Util.validMd5(data, MD5Util.getKey(), mac)) {
				return Jsonp.error_md5();
			}
			JsonParser jsonParser = new JsonParser();
			JsonElement json = jsonParser.parse(data);
			String alabaoSid = json.getAsJsonObject().get("alabaoSid").getAsString();
			AlabaoUserDto alabaoUserDto = (AlabaoUserDto) AlabaoSessionCache.get(alabaoSid);
			if (ObjectUtils.equals(alabaoUserDto, null)) {
				return Jsonp.noLoginError("未登录或重新登录");
			}
			AlabaoAccount alabaoAccount = alabaoAccountService.findById(alabaoUserDto.getAlabaoId());
			if (ObjectUtils.equals(alabaoAccount, null)) {
				return Jsonp.error("无此记录");
			}
			MainAlabaoDto mainAlabaoDto = new MainAlabaoDto();
			mainAlabaoDto.setBalance(NumberFormatUtil.numberFormat(alabaoAccount.getBalance()));
			mainAlabaoDto.setYesterdayIncome(NumberFormatUtil.numberFormat(alabaoAccount.getYesterdayIncome()));
			mainAlabaoDto.setTotalIncome(NumberFormatUtil.numberFormat(alabaoAccount.getTotalIncome()));
			mainAlabaoDto.setIsValidate(VerificationConstant.IS_VALIDATE_STATUS_SUCCESS.equals(alabaoAccount.getIsValidate())?CommonConstant.YES:CommonConstant.NO);
			BeanNullConverUtil.nullConver(mainAlabaoDto);
			return Jsonp_data.success(mainAlabaoDto);
		} catch (Exception e) {
			e.printStackTrace();
			LOGGER.error("主显出错", e);
			return Jsonp.error("主显出错");
		}
	}
	
}

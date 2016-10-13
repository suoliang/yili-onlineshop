package com.fushionbaby.app.controller.alabao;

import java.math.BigDecimal;

import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.aladingshop.alabao.model.AlabaoOrder;
import com.aladingshop.alabao.service.AlabaoOrderService;
import com.fushionbaby.cache.util.AlabaoSessionCache;
import com.fushionbaby.common.constants.CommonConstant;
import com.fushionbaby.common.dto.alabao.AlabaoUserDto;
import com.fushionbaby.common.security.MD5Util;
import com.fushionbaby.common.util.CheckIsEmpty;
import com.fushionbaby.common.util.EpointsUtil;
import com.fushionbaby.common.util.jsonp.Jsonp;
import com.fushionbaby.common.util.jsonp.Jsonp_data;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

/**
 * @description 转入金额操作类
 * @author 索亮
 * @date 2015年9月10日下午3:01:52
 */
@Controller
@RequestMapping("/alabao")
public class AlabaoTransferAmountController {
	private static final Log LOGGER = LogFactory.getLog(AlabaoTransferAmountController.class);
	
	@Autowired
	private AlabaoOrderService<AlabaoOrder> alabaoOrderService;
	
	/***
	 * @param alabaoSid
	 * @param transferAmount
	 * @param sourceCode
	 * @return
	 */
	@ResponseBody
	@RequestMapping("transferAmount")
	public Object transferAmount(
			@RequestParam(value="data",defaultValue="")String data,
			@RequestParam(value="mac",defaultValue="")String mac){
		try {
			LOGGER.info("转入金额如意宝接口action--请求报文：{" + data + "}");
			if (!MD5Util.validMd5(data, MD5Util.getKey(), mac)) {
				return Jsonp.error_md5();
			}
			JsonParser jsonParser = new JsonParser();
			JsonElement json = jsonParser.parse(data);
			String alabaoSid = json.getAsJsonObject().get("alabaoSid").getAsString();
			String transferAmount = json.getAsJsonObject().get("transferAmount").getAsString();
			String sourceCode = json.getAsJsonObject().get("sourceCode").getAsString();
			if (CheckIsEmpty.isEmpty(alabaoSid, transferAmount, sourceCode)) {
				return Jsonp.error("参数无值");
			}
			AlabaoUserDto alabaoUserDto = (AlabaoUserDto) AlabaoSessionCache.get(alabaoSid);
			if (ObjectUtils.equals(alabaoUserDto, null)) {
				return Jsonp.noLoginError("未登录或重新登录");
			}
			String orderCode = EpointsUtil.generateOrdrNo();
			AlabaoOrder alabaoOrder = new AlabaoOrder();
			alabaoOrder.setAccount(alabaoUserDto.getAccount());
			alabaoOrder.setOrderCode(orderCode);
			alabaoOrder.setMemberId(alabaoUserDto.getMemberId());
			alabaoOrder.setMemberName(alabaoUserDto.getAccount());
			alabaoOrder.setTotalActual(new BigDecimal(transferAmount));
			alabaoOrder.setAlabaoStatus(CommonConstant.NO);
			alabaoOrder.setSourceCode(sourceCode);
			alabaoOrderService.add(alabaoOrder);
			return Jsonp_data.success(orderCode);
		} catch (Exception e) {
			e.printStackTrace();
			LOGGER.error("转入金额接口出错", e);
			return Jsonp.error("转入金额出错");
		}
	}
	
}

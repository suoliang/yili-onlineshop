package com.fushionbaby.app.controller.pay;

import java.math.BigDecimal;

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
import com.fushionbaby.app.dto.alabaopay.AlabaoPayDto;
import com.fushionbaby.cache.SessionCache;
import com.fushionbaby.cache.util.AlabaoSessionCache;
import com.fushionbaby.common.constants.config.ImportanceConfigConstant;
import com.fushionbaby.common.dto.UserDto;
import com.fushionbaby.common.dto.alabao.AlabaoUserDto;
import com.fushionbaby.common.util.CheckObjectIsNull;
import com.fushionbaby.common.util.NumberFormatUtil;
import com.fushionbaby.common.util.jsonp.Jsonp;
import com.fushionbaby.common.util.jsonp.Jsonp_data;
import com.fushionbaby.config.model.SysmgrAlabaoConfig;
import com.fushionbaby.config.model.SysmgrImportanceConfig;
import com.fushionbaby.config.service.SysmgrAlabaoConfigService;
import com.fushionbaby.config.service.SysmgrImportanceConfigService;
import com.fushionbaby.core.model.OrderFeeUser;
import com.fushionbaby.core.service.OrderFeeUserService;

/***
 * @description 获取使用如意消费卡支付优惠后价格
 * @author 索亮
 * @date 2016年2月24日下午1:44:58
 */
@Controller
@RequestMapping("/alabaoPay")
public class AlabaoPayPriceController {
	
	private static final Log LOGGER = LogFactory.getLog(AlabaoPayPriceController.class);
	@Autowired
	private SysmgrAlabaoConfigService<SysmgrAlabaoConfig> alabaoConfigService;
	@Autowired
	private SysmgrImportanceConfigService importanceConfigService;
	@Autowired
	private OrderFeeUserService<OrderFeeUser> orderFeeUserService;
	@Autowired
	private AlabaoAccountService<AlabaoAccount> alabaoAccountService;
	
	/***
	 * @description 获取折扣后金额
	 * @param sid
	 * @param alabaoSid
	 * @param orderCode
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/getCutPrice")
	public Object getAlabaoPayPrice(
			@RequestParam("sid") String sid,
			@RequestParam("alabaoSid") String alabaoSid,
			@RequestParam("orderCode") String orderCode){
		try {
			UserDto user = (UserDto) SessionCache.get(sid);
			if (CheckObjectIsNull.isNull(user)) {
				return Jsonp.noLoginError("APP未登录");
			}
			AlabaoUserDto alabaoUserDto = (AlabaoUserDto) AlabaoSessionCache.get(alabaoSid);
			if (ObjectUtils.equals(alabaoUserDto, null)) {
				return Jsonp.noLoginError("如意未登录或重新登录");
			}
			OrderFeeUser orderFeeUser = orderFeeUserService.findByMIdAndOrdCode(user.getMemberId(), orderCode);
			if (ObjectUtils.equals(orderFeeUser, null)) {
				return Jsonp.error("费用查询失败");
			}
			/**订单总计原价*/
			BigDecimal totalActual = orderFeeUser.getTotalActual();
			if (ObjectUtils.equals(totalActual, null)) {
				return Jsonp.error("订单不存在或已取消!");
			}
			AlabaoAccount alabaoAccount = alabaoAccountService.findByAccount(alabaoUserDto.getAccount());
			SysmgrAlabaoConfig alabaoConfig = alabaoConfigService.findByMinMaxValue(NumberFormatUtil.numberFormat(alabaoAccount.getBalance()));
			AlabaoPayDto alabaoPayDto = new AlabaoPayDto();
			/**对象不存在九折优惠*/
			if (ObjectUtils.equals(alabaoConfig, null)) {
				SysmgrImportanceConfig importanceConfig = importanceConfigService.findByCode(ImportanceConfigConstant.ALABAO_PAY_DISCOUNT);
				
				BigDecimal afDiscount = totalActual.multiply(new BigDecimal(importanceConfig.getValue()));
				alabaoPayDto.setDesc(importanceConfig.getRemark());
				alabaoPayDto.setTotalPrice(NumberFormatUtil.numberFormat(totalActual));
				alabaoPayDto.setDiscountPrice(NumberFormatUtil.numberFormat(afDiscount));
				return Jsonp_data.success(alabaoPayDto);
			}
			
			BigDecimal afDiscount = totalActual.multiply(new BigDecimal(alabaoConfig.getDiscountValue()));
			/**打折后的总价*/
			String settleAmount = NumberFormatUtil.numberFormat(afDiscount);
			
			alabaoPayDto.setDesc(alabaoConfig.getRemark());
			alabaoPayDto.setTotalPrice(NumberFormatUtil.numberFormat(totalActual));
			alabaoPayDto.setDiscountPrice(settleAmount);
			
			return Jsonp_data.success(alabaoPayDto);
		} catch (Exception e) {
			e.printStackTrace();
			LOGGER.error("获取如意消费卡支付价格出错", e);
			return Jsonp.error("获取出错");
		}
		
	}
	
}

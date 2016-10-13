package com.fushionbaby.wap.controller.order;

import java.math.BigDecimal;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fushionbaby.act.activity.model.ActCard;
import com.fushionbaby.act.activity.service.ActCardService;
import com.fushionbaby.cache.DataCache;
import com.fushionbaby.cache.SessionCache;
import com.fushionbaby.common.constants.CommonConstant;
import com.fushionbaby.common.constants.CommonConstant.CommonMessage;
import com.fushionbaby.common.constants.CookieConstant;
import com.fushionbaby.common.constants.SettlementConstant;
import com.fushionbaby.common.dto.UserDto;
import com.fushionbaby.common.dto.order.OrderExtraDto;
import com.fushionbaby.common.util.CookieUtil;
import com.fushionbaby.common.util.jsonp.Jsonp;
import com.fushionbaby.common.util.jsonp.Jsonp_data;
import com.fushionbaby.core.model.SoShoppingCartSkuUser;
import com.fushionbaby.core.service.SoShoppingCartSkuWebUserService;
import com.fushionbaby.facade.biz.common.order.OrderExtraCommonFacade;
import com.fushionbaby.facade.biz.common.order.dto.OrderExtraCommonDto;
import com.fushionbaby.sku.model.SkuBrandRelation;
import com.fushionbaby.sku.model.SkuCategoryRelation;
import com.fushionbaby.sku.model.SkuLabelRelation;
import com.fushionbaby.sku.service.SkuBrandRelationService;
import com.fushionbaby.sku.service.SkuCategoryRelationService;
import com.fushionbaby.sku.service.SkuLabelRelationService;
import com.fushionbaby.config.model.SysmgrSfFreightConfig;
import com.fushionbaby.config.service.SysmgrGlobalConfigService;
import com.fushionbaby.config.service.SysmgrSfFreightConfigService;

/**
 *生成订单前额外的操作
 */
@Controller
@RequestMapping("/order")
public class OrderExtraController {


	/**注入购物车行记录*/
	@Autowired
	private SoShoppingCartSkuWebUserService<SoShoppingCartSkuUser> soShoppingCartSkuWebService;

	/**顺丰快递运费service*/
	@Autowired
	private SysmgrSfFreightConfigService<SysmgrSfFreightConfig> sysmgrSfFreightService;

	/** 优惠券service*/
	@Autowired
	private ActCardService<ActCard> actCardService;
	
	/**全局变量配置service*/
	@Autowired
	private SysmgrGlobalConfigService sysmgrGlobalConfigService;
	
	/**
	 * 商品品牌
	 */
	@Autowired
	private SkuBrandRelationService<SkuBrandRelation> skuBrandRelationService;
	
	/**
	 * 商品分类
	 */
	@Autowired
	private SkuCategoryRelationService<SkuCategoryRelation> skuCategoryRelationService;
	
	/**
	 * 商品Lable Relation
	 */
	@Autowired
	private SkuLabelRelationService<SkuLabelRelation> maSkuLabelRelationService;

	
	/**app，web共用的order*/
	@Autowired
	private OrderExtraCommonFacade orderExtraCommonFacade;
	/** 日志*/
	private static final Log logg=LogFactory.getLog(OrderExtraController.class);
	/**
	 * 计算运费接口
	 * @param sid 
	 * @param payoff_id 结算序列ID
	 * @param area_code 区域code
	 * @return
	 */
	@ResponseBody
	@RequestMapping("get_freight")
	public Object get_freight(String payoff_id,String area_code,HttpServletRequest request){
		try{
			if (StringUtils.isEmpty(payoff_id) || StringUtils.isEmpty(area_code)) {
				return Jsonp.paramError(CommonMessage.PARAM_ERROR_MESSAGE);
			}
			String sid = CookieUtil.getCookieValue(request, CookieConstant.COOKIE_SID);
			// 检查用户是否登陆。
			UserDto user = (UserDto) SessionCache.get(sid);
			if (user == null) {
				return Jsonp.error("未登录");
			}
			else{
				OrderExtraCommonDto freightDto=new OrderExtraCommonDto();
				freightDto.setAreaCode(area_code);
				freightDto.setPayOffId(payoff_id);
				return orderExtraCommonFacade.getFreight(freightDto);
			}	

		}catch(Exception e){
			logg.error("计算运费出错", e);
			return Jsonp.error();
		}
	}
	
	
	/**
	 * 计算积分接口
	 * @param sid 
	 * @param is_use_integral 是否使用(y是n否)
	 * @param area_code 区域code --可以为空
	 * @param is_use_tax 是否使用税费
	 * @return
	 */
	@ResponseBody
	@RequestMapping("get_integral")
	public Object get_integral(String payoff_id,String is_use_integral,String area_code,String is_use_tax,
			String epointNum,	HttpServletRequest request){
		try{
			// 判断sid和is_use是否为空  
			if(StringUtils.isEmpty(payoff_id)) {
				return Jsonp.paramError(CommonMessage.PARAM_ERROR_MESSAGE);
			}
			//判断传的参数是不是y或n
			if (!(StringUtils.equals(is_use_integral, CommonConstant.YES) || StringUtils.equals(is_use_integral, CommonConstant.NO))) {
				return Jsonp.paramError("参数传递有误!");
			}
			//判断传的参数是不是y或n
			if (!(StringUtils.equals(is_use_tax, CommonConstant.YES) || StringUtils.equals(is_use_tax, CommonConstant.NO))) {
				return Jsonp.paramError("参数传递有误!");
			}
			String sid = CookieUtil.getCookieValue(request, CookieConstant.COOKIE_SID);
			// 检查用户是否登陆。
			UserDto user = (UserDto)SessionCache.get(sid);
			if(user == null) {
				return Jsonp.error("未登录");
			}else{
		         OrderExtraCommonDto integralDto=new OrderExtraCommonDto();
		         integralDto.setPayOffId(payoff_id);
		         integralDto.setIsUseIntegral(is_use_integral);
		         integralDto.setAreaCode(area_code);
		         integralDto.setIsUseTax(is_use_tax);
		         integralDto.setEpoints(epointNum);
				return this.orderExtraCommonFacade.getIntegral(integralDto);
			}

		}catch(Exception e){
			logg.error("计算积分出错", e);
			return Jsonp.error();
		}
	}
	
	/**
	 * 代金券获取接口
	 * @param sid 
	 * @param card_no  优惠券卡号
	 * @param is_use_cardno 是否使用(y是n否)
	 * @param area_code 区域code --可以为空
	 * @param is_use_tax 是否使用税费
	 * @return
	 */

	@ResponseBody
	@RequestMapping("getCardno")
	public Object getCardno(@RequestParam("payoffId") String payoffId,@RequestParam("cardNo")String cardNo,
			@RequestParam("isUseCardno") String isUseCardno,@RequestParam("areaCode") String areaCode,
			@RequestParam("isUseTax") String isUseTax,@RequestParam("gotoType") String gotoType,
			HttpServletRequest request){
		OrderExtraCommonDto cardDto=new OrderExtraCommonDto();
		try{
			// 判断是sid是否为空。
			if(StringUtils.isEmpty(cardNo)) {
				return Jsonp.paramError(CommonMessage.PARAM_ERROR_MESSAGE);
			}
			//订单付款标识
			if (StringUtils.isEmpty(payoffId)) {
				return Jsonp.paramError(CommonMessage.PARAM_ERROR_MESSAGE);
			}
			//判断优惠券是否使用传的参数是不是y或n
			if (!(StringUtils.equals(isUseCardno, CommonConstant.YES) || StringUtils.equals(isUseCardno, CommonConstant.NO))) {
				return Jsonp.paramError(CommonMessage.PARAM_ERROR_MESSAGE);
			}
			//判断是否使用发票传的参数是不是y或n
			if (!(StringUtils.equals(isUseTax, CommonConstant.YES) || StringUtils.equals(isUseTax, CommonConstant.NO))) {
				return Jsonp.paramError(CommonMessage.PARAM_ERROR_MESSAGE);
			}
			String sid = CookieUtil.getCookieValue(request, CookieConstant.COOKIE_SID);
			// 检查用户是否登陆。
			UserDto user = (UserDto)SessionCache.get(sid);
			if(ObjectUtils.equals(null, user)) {
				return Jsonp.error("未登录");
			}
			cardDto.setPayOffId(payoffId);
			cardDto.setIsUseCardno(isUseCardno);
			cardDto.setCardNo(cardNo);
			cardDto.setAreaCode(areaCode);
			cardDto.setIsUseTax(isUseTax);
			cardDto.setSid(sid);
			cardDto.setMemberId(user.getMember_id());
			cardDto.setGotoType(gotoType);
		}catch(Exception e){
          logg.error("代金券计算获取出错", e);
			return Jsonp.error();
		}	
		return orderExtraCommonFacade.useCardNo(cardDto);		
	}		


	
	
	
	/**
	 * 税费接口
	 * @param sid   
	 * @param payoff_id 结算序列ID
	 * @param is_use_tax 是否使用(y是n否)
	 * @return
	 */
	@ResponseBody
	@RequestMapping("get_tax")
	public Object get_tax(String payoff_id,String is_use_tax,HttpServletRequest request){
		try{
			// 判断sid和payoff_id是否为空。
			if(StringUtils.isEmpty(payoff_id)) {
				return Jsonp.paramError(CommonMessage.PARAM_ERROR_MESSAGE);
			}
			//判断传的参数是不是y或n
			if (!(StringUtils.equals(is_use_tax, CommonConstant.YES) || StringUtils.equals(is_use_tax, CommonConstant.NO))) {
				return Jsonp.paramError("参数传递有误!");
			}
			String sid = CookieUtil.getCookieValue(request, CookieConstant.COOKIE_SID);
			// 检查用户是否登陆。
			UserDto user = (UserDto)SessionCache.get(sid);
			if(user == null) {
				return Jsonp.error("未登录");
			}
			
			else{
				OrderExtraCommonDto taxDto=new OrderExtraCommonDto();
				taxDto.setPayOffId(payoff_id);
				taxDto.setIsUseTax(is_use_tax);
				taxDto.setSid(sid);
				return  orderExtraCommonFacade.getTax(taxDto);
				
			}

		}catch(Exception e){
		   logg.error("计算税费接口出错",e);
			return Jsonp.error();
		}
	}
	

	
	/***
	 * 此方法只在最后调用
	 * @param payoff_id
	 * @return
	 */
	@ResponseBody
	@RequestMapping("get_total_money")
	public Object final_totalPrice(String payoff_id){
		String payoff_id_value = (String) DataCache.get(payoff_id);//获取payoff_id的值
		if (StringUtils.isBlank(payoff_id_value)) {
			return pay_off_failed();//判断是否失效
		}
		BigDecimal total_actual = (BigDecimal)DataCache.get(payoff_id+SettlementConstant._TOTAL_ACTUAL);//订单总计
		String total_price = total_actual.toString();
		OrderExtraDto orderExtraDto = new OrderExtraDto();
		orderExtraDto.setTotal_actual(total_price);
		return Jsonp_data.success(orderExtraDto);
	}
	
	/***
	 * 订单结算序列失效
	 */
	private Object pay_off_failed(){
		return Jsonp.payOffFailedError("订单结算序列失效!");
	}
}

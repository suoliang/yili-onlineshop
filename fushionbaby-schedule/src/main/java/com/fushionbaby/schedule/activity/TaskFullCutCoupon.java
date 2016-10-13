/**aladingshop.com 上海一里网络科技有限公司
 */
package com.fushionbaby.schedule.activity;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.aladingshop.sku.cms.model.SkuLabel;
import com.aladingshop.sku.cms.model.SkuLabelRelation;
import com.aladingshop.sku.cms.model.SkuPrice;
import com.aladingshop.sku.cms.model.SkuPromotionsInfo;
import com.aladingshop.sku.cms.service.SkuInfoService;
import com.aladingshop.sku.cms.service.SkuLabelRelationService;
import com.aladingshop.sku.cms.service.SkuLabelService;
import com.aladingshop.sku.cms.service.SkuPriceService;
import com.aladingshop.sku.cms.service.SkuPromotionsInfoService;
import com.fushionbaby.act.activity.model.ActCard;
import com.fushionbaby.act.activity.model.ActCardType;
import com.fushionbaby.act.activity.service.ActCardService;
import com.fushionbaby.act.activity.service.ActCardTypeService;
import com.fushionbaby.common.constants.PromotionConstant;
import com.fushionbaby.common.constants.SmsConstant;
import com.fushionbaby.common.constants.SourceConstant;
import com.fushionbaby.common.enums.OrderConfigServerEnum;
import com.fushionbaby.common.enums.OrderFullRebateEnum;
import com.fushionbaby.common.util.RandomNumUtil;
import com.fushionbaby.common.util.date.DateEndUtil;
import com.fushionbaby.member.model.Member;
import com.fushionbaby.member.service.MemberService;
import com.fushionbaby.order.model.OrderBase;
import com.fushionbaby.order.model.OrderLine;
import com.fushionbaby.order.service.OrderBaseService;
import com.fushionbaby.order.service.OrderLineService;
import com.fushionbaby.sms.model.Sms;
import com.fushionbaby.sms.model.SmsTypeConfig;
import com.fushionbaby.sms.service.SmsService;
import com.fushionbaby.sms.service.SmsTypeConfigService;

/**
 * @description 双十一满赠活动送优惠券
 * @author 孟少博
 * @date 2015年10月23日下午3:27:07
 */
@Service
public class TaskFullCutCoupon {
	
	
	private static boolean runFlag = false;
	/***日志*/
	private static final Log LOGGER = LogFactory.getLog(TaskFullCutCoupon.class);
	
	/** 双11活动的标签编号前缀*/
	private static final String ACTIVITY_PREFIX = "ACTIVITY_11_";
	
	/** 双11活动编号*/
	private static final String ACTIVITY_FULLCUT = "FULLCUT";	
	
	@Autowired
	private SkuPromotionsInfoService<SkuPromotionsInfo> pmInfoService;
	
	@Autowired
	private OrderBaseService<OrderBase> orderBaseService;
	
	/**订单行信息查询service*/
	@Autowired
	private OrderLineService<OrderLine> orderLineService;
	
	
	@Autowired
	private SkuLabelService<SkuLabel> skuLabelService;
	
	@Autowired
	private SkuLabelRelationService<SkuLabelRelation> skuLabelRelationService;
	
	@Autowired
	private SkuInfoService skuInfoService;
	@Autowired
	private SkuPriceService<SkuPrice> skuPriceService;
	/**会员*/
	@Autowired
	private MemberService<Member> memberService;
	/**短信服务*/
	@Autowired
	private SmsService<Sms> smsService;
	/** 优惠券 */
	@Autowired
	private ActCardService<ActCard> cardService;
	/** 优惠券类型*/
	@Autowired
	private ActCardTypeService<ActCardType> cardTypeService;
	
	/** 短信类型 */
	@Autowired
	private SmsTypeConfigService<SmsTypeConfig> smsTypeConfigService;
	
	/** 赠送代金券类型，面值为 30 和 10 的 type_id*/
	private static final Long DISCOUNT_30_ID= 100L;
	private static final Long DISCOUNT_10_ID= 101L;
	
	/**要发送的短信内容*/
//	private static String smsContent="【阿拉丁玛特】亲，由于您消费‘阿拉丁玛特’指定优惠商品满[orderMoney]元，故我们赠送您面值为[money]元的代金券，券号为[cardNo]，下单直接抵用[money]元,有效截止时间[endTime]，请注意查收，感谢您的使用。";
	public void excute() {
		if (runFlag) {
			LOGGER.info("TaskFullCutCoupon正在运行");
			return;
		}
		runFlag = true;

		LOGGER.info("TaskFullCutCoupon开始运行");
		run();
		LOGGER.info("TaskFullCutCoupon运行结束");
		runFlag = false;
	}

	private void run(){
 		SkuPromotionsInfo info = pmInfoService.findByPromotionsCode(ACTIVITY_FULLCUT);
		List<OrderBase> list = orderBaseService.findListByOrderStatusAndCurrent(OrderConfigServerEnum.SUCCESS_AUDIT.getCode());
		if (CollectionUtils.isEmpty(list)) {
			return;
		}
		for (OrderBase orderBase : list) {
			if(info.getStartDate().getTime() <=orderBase.getCreateTime().getTime()
					&&  DateEndUtil.getEndDateLong(info.getEndDate()) > orderBase.getCreateTime().getTime()){
				
				Member member=this.memberService.findById(orderBase.getMemberId());
				if(member==null||member.getTelephone()==null)
						return ;
				String phoneNumber=member.getTelephone();
				List<OrderLine> orderLines = orderLineService.findByOrderCode(orderBase.getOrderCode(), null);
				for (OrderLine orderLine : orderLines) {
					List<String> skuCodeList = this.getFullCutSkuCodeList();
					if(CollectionUtils.isEmpty(skuCodeList)){
						continue;
					}
					BigDecimal amount = new BigDecimal(0);
					amount = amount.add( this.handleFullCut(skuCodeList, orderLine));//处理满赠
					try {
						ActCard card=new ActCard();
						String cardNo=RandomNumUtil.getCharacterAndNumber(5);
						card.setCardNo(cardNo);
						card.setUseType(PromotionConstant.CARD_USE_TYPE_ONCE);
						card.setMaxCount(1);
						card.setUseCount(0);
						ActCardType cardType=this.cardTypeService.findById(DISCOUNT_30_ID);
						//String endTime=DateFormat.dateTimeToDateString(cardType.getEndTime());
						String smsContent=this.smsTypeConfigService.findById(SmsConstant.SMS_TYPE_DOUBLE_11_ID).getSmsTemplate();
						
						if(amount.compareTo(new BigDecimal(OrderFullRebateEnum.FULL_ONE_HUNDRED_REBATE.getOrderMoney())) >=0 ){
							card.setCardTypeName(cardType.getName());
							card.setCardTypeId(cardType.getId());
							smsContent=smsContent.replace("[code]",cardNo).replace("[money]", OrderFullRebateEnum.FULL_ONE_HUNDRED_REBATE.getRebateMoney());
							this.cardService.add(card);
							smsService.sendSmsUserDefined(phoneNumber,smsContent,SourceConstant.CMS_CODE);
							return ;
						}else if(amount.compareTo(new BigDecimal(OrderFullRebateEnum.FULL_FIFTY_REBATE.getOrderMoney())) >=0 ){
							cardType=this.cardTypeService.findById(DISCOUNT_10_ID);
							//endTime=DateFormat.dateTimeToDateString(cardType.getEndTime());
							card.setCardTypeId(cardType.getId());
							card.setCardTypeName(cardType.getName());
							smsContent=smsContent.replace("[code]",cardNo).replace("[money]", OrderFullRebateEnum.FULL_FIFTY_REBATE.getRebateMoney());
							this.cardService.add(card);
							smsService.sendSmsUserDefined(phoneNumber,smsContent,SourceConstant.CMS_CODE);
							return ;
						}
						
					} catch (Exception e) {
						LOGGER.error("TaskFullCutCoupon.java  发送短信 优惠券异常", e);
					}
				

				}
				
			}
				
		}
		
	}
	/**
	 * 处理满赠
	 * @param skuCodeList 满赠活动的商品编号集合
	 * @param orderLineSkuCode 订单行商品编号
	 */
	private BigDecimal handleFullCut(List<String> skuCodeList,OrderLine orderLine){
		for (String skuCode : skuCodeList) {
			if(StringUtils.equals(skuCode, orderLine.getSkuCode())){
				return orderLine.getTotalPrice();
			}
		}
		return new BigDecimal(0);
	}
	
	
	/**
	 * 获取满赠活动的所有商品编号
	 * @return
	 */
	private List<String> getFullCutSkuCodeList(){
		List<String> skuCodeList = new ArrayList<String>();
		
		List<SkuLabel> labelList = skuLabelService.getLabelListByCode(ACTIVITY_PREFIX);
		if(CollectionUtils.isEmpty(labelList)){
			return skuCodeList;
		}
		for (SkuLabel skuLabel : labelList) {
			skuCodeList.addAll(skuLabelRelationService.findSkuCodesByLabel(skuLabel.getCode(),StringUtils.EMPTY));
		}
		
		return skuCodeList;
	}
	
	
}

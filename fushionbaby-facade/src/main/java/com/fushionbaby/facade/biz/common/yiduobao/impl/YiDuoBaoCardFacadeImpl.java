/**
 * 
 */
package com.fushionbaby.facade.biz.common.yiduobao.impl;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import util.ImageConstantFacade;

import com.aladingshop.alabao.model.AlabaoAccount;
import com.aladingshop.alabao.service.AlabaoAccountService;
import com.aladingshop.card.dto.req.YiDuoBaoCardBackRepDto;
import com.aladingshop.card.dto.res.YiDuoBaoCardBackResDto;
import com.aladingshop.card.dto.res.YiDuoBaoCardResDto;
import com.aladingshop.card.dto.res.YiDuoBaoConfigResDto;
import com.aladingshop.card.dto.res.YiDuoBaoCreateOrderResDto;
import com.aladingshop.card.enums.CardStatusEnum;
import com.aladingshop.card.model.MemberCard;
import com.aladingshop.card.model.MemberCardBack;
import com.aladingshop.card.model.MemberCardConfig;
import com.aladingshop.card.model.MemberCardOrder;
import com.aladingshop.card.service.MemberCardBackService;
import com.aladingshop.card.service.MemberCardConfigService;
import com.aladingshop.card.service.MemberCardOrderService;
import com.aladingshop.card.service.MemberCardService;
import com.fushionbaby.cache.util.AlabaoSessionCache;
import com.fushionbaby.common.constants.AppConstant;
import com.fushionbaby.common.constants.CommonConstant;
import com.fushionbaby.common.constants.ScheduleConstant;
import com.fushionbaby.common.constants.alabao.AlabaoOrderConstant;
import com.fushionbaby.common.constants.alabao.MemberCardConstant;
import com.fushionbaby.common.constants.store.StoreConstant;
import com.fushionbaby.common.dto.alabao.AlabaoUserDto;
import com.fushionbaby.common.enums.MemberCardYiDuoBaoEnum;
import com.fushionbaby.common.enums.OrderConfigClientEnum;
import com.fushionbaby.common.enums.PaymentTypeEnum;
import com.fushionbaby.common.util.EpointsUtil;
import com.fushionbaby.common.util.NumberFormatUtil;
import com.fushionbaby.common.util.jsonp.Jsonp;
import com.fushionbaby.common.util.jsonp.Jsonp_data;
import com.fushionbaby.config.model.SysmgrGlobalConfig;
import com.fushionbaby.config.service.SysmgrGlobalConfigService;
import com.fushionbaby.core.model.OrderFeeUser;
import com.fushionbaby.core.model.OrderFinanceUser;
import com.fushionbaby.core.service.OrderFeeUserService;
import com.fushionbaby.core.service.OrderFinanceUserService;
import com.fushionbaby.facade.biz.common.yiduobao.YiDuoBaoCardFacade;

/**
 * @description 益多宝门面层接口实现
 * @author 孙涛
 * @date 2015年9月21日上午9:34:29
 */
@Service
public class YiDuoBaoCardFacadeImpl implements YiDuoBaoCardFacade {
	private static final Log LOGGER = LogFactory.getLog(YiDuoBaoCardFacadeImpl.class);

	@Autowired
	private MemberCardConfigService<MemberCardConfig> memberCardConfigService;

	@Autowired
	private MemberCardService<MemberCard> memberCardService;

	/** 财务订单 */
	@Autowired
	private OrderFinanceUserService<OrderFinanceUser> orderFinanceUserService;

	/** 益多宝订单 */
	@Autowired
	private MemberCardOrderService<MemberCardOrder> memberCardOrderService;
	@Autowired
	private MemberCardBackService<MemberCardBack> memberCardBackService;

	
	@Autowired
	private AlabaoAccountService<AlabaoAccount> alabaoAccountService;
	
	@Autowired
	private SysmgrGlobalConfigService sysmgrGlobalConfigService;
	
	
	/**订单金额表*/
	@Autowired
	private OrderFeeUserService<OrderFeeUser> feeUserService;
	/**
	 * 
	 * 加入 sourceCode 如果已经登陆如意宝，则表示用户已经登陆app
	 * 
	 * 购卡创建订单
	 */
	public Object createOrder(String configId, String faceValue,AlabaoUserDto alabaoUserDto,String sourceCode) {
		try {
			if (StringUtils.isBlank(configId) || StringUtils.isBlank(faceValue)) {
				return Jsonp.paramError("请选择阿拉丁卡类型和面额");
			}
//			String orderCode = configId + "ydb" + EpointsUtil.generateOrdrNo();
			String orderCode = configId + AlabaoOrderConstant.ALABAO_ORDER_SIGN + EpointsUtil.generateOrdrNo();
			saveOrderFinance(faceValue, alabaoUserDto.getMemberId(), orderCode,alabaoUserDto.getAccount());
			saveMemberCardOrder(faceValue, sourceCode,alabaoUserDto.getMemberId(),alabaoUserDto.getAccount(),orderCode);
			
			
			saveOrderFee(faceValue, alabaoUserDto, orderCode);
			
			
			YiDuoBaoCreateOrderResDto resDto = new YiDuoBaoCreateOrderResDto();
			resDto.setAlabaoSid(alabaoUserDto.getAlabaoSid());
			resDto.setPayWay(PaymentTypeEnum.PAYMENT_ZFB_WEB_JSDZ.getCode());
			resDto.setOrderCode(orderCode);
			
			OrderFeeUser fee=this.feeUserService.findByMIdAndOrdCode(alabaoUserDto.getMemberId(), orderCode);
			resDto.setActualMoney(NumberFormatUtil.numberFormat(fee==null?BigDecimal.ZERO:fee.getTotalActual()));
			
			return Jsonp_data.success(resDto);
		} catch (Exception e) {
			LOGGER.error("YiDuoBaoCardFacadeImpl.java 购卡 益多宝异常", e);
			return Jsonp.error();
		}
	}

	private void saveOrderFee(String faceValue, AlabaoUserDto alabaoUserDto,String orderCode) {
		OrderFeeUser fee=new OrderFeeUser();
		fee.setActualTransferFee(BigDecimal.ZERO);
		fee.setAlabaoCheapAmount(BigDecimal.ZERO);
		fee.setCardAmount(BigDecimal.ZERO);
		fee.setCardno(StringUtils.EMPTY);
		fee.setCreateTime(new Date());
		fee.setEpointsRedeemMoney(BigDecimal.ZERO);
		fee.setMemberId(alabaoUserDto.getMemberId());
		fee.setOrderCode(orderCode);
		fee.setPayableTransferFee(BigDecimal.ZERO);
		fee.setRedEnvelopeAmount(BigDecimal.ZERO);
		fee.setStoreCode(StoreConstant.STORE_CODE);
		fee.setTotalActual(new BigDecimal(faceValue));
		fee.setTotalAfDiscount(new BigDecimal(faceValue));
		fee.setUseWalletMoney(BigDecimal.ZERO);
		feeUserService.addOrderFeeUser(fee);
	}
	
	private List<String> getPhoneList(){
		SysmgrGlobalConfig mobileList = sysmgrGlobalConfigService.findByCode("MOBILELIST");
		if(mobileList!=null && StringUtils.isNotBlank(mobileList.getValue())){
			return Arrays.asList(StringUtils.trimToEmpty(mobileList.getValue()).split(","));
		}
		return new ArrayList<String>();
		
	}

	private void saveMemberCardOrder(String faceValue, String sourceCode, Long memberId,String account, String orderCode) {
		MemberCardOrder cardOrder = new MemberCardOrder();
		AlabaoAccount accountUser=this.alabaoAccountService.findByAccount(account);
		if(accountUser!=null)
			cardOrder.setMemberName(accountUser.getTrueName());
		cardOrder.setMemberId(memberId);
		cardOrder.setOrderCode(orderCode);
		cardOrder.setOrderStatus(OrderConfigClientEnum.WAITING_PAYMENT.getCode());
		List<String> phoneList = this.getPhoneList();
		if (phoneList.contains(account)) {
			cardOrder.setTotalActual(BigDecimal.valueOf(0.01));
		}else{
			cardOrder.setTotalActual(new BigDecimal(faceValue));
		}
		cardOrder.setFinanceStatus(CommonConstant.NO);
		cardOrder.setSourceCode(sourceCode);
		cardOrder.setCreateTime(new Date());
		memberCardOrderService.add(cardOrder);
	}

	private void saveOrderFinance(String faceValue, Long memberId, String orderCode,String account) {
		OrderFinanceUser orderFinanceUser = new OrderFinanceUser();
		orderFinanceUser.setCreateTime(new Date());
		orderFinanceUser.setMemberId(memberId);
		orderFinanceUser.setOrderCode(orderCode);
		orderFinanceUser.setFinanceStatus(CommonConstant.NO);
		orderFinanceUser.setUpdateTime(new Date());
		this.orderFinanceUserService.addOrderFinanceUser(orderFinanceUser);
	}

	/**
	 * 根据用户获取相关益多宝卡列表
	 */
	public List<YiDuoBaoCardResDto> getCardListByMember(String alabaoSid) {
		List<YiDuoBaoCardResDto> cardList = new ArrayList<YiDuoBaoCardResDto>();
		try {
			AlabaoUserDto alabaoUserDto = (AlabaoUserDto) AlabaoSessionCache.get(alabaoSid);
			List<MemberCard> memberCards = this.memberCardService.findByMemberId(alabaoUserDto.getMemberId());
			for (MemberCard card : memberCards) {
				YiDuoBaoCardResDto resDto = new YiDuoBaoCardResDto();
				//resDto.setId(card.getId());
				resDto.setCardNo(card.getCardNo());
				resDto.setTotalCorpus(NumberFormatUtil.numberFormat(card.getTotalCorpus()));
				resDto.setStatus(card.getStatus());
				resDto.setTotalRebate(NumberFormatUtil.numberFormat(card.getTotalRebate()));
				/** 获取当前卡类型 */
				resDto.setCardName(card.getCardName());
				resDto.setId(MemberCardYiDuoBaoEnum.getTypeCode(card.getCardName()));
				
				resDto.setShowTime(card.getShowTime());
				resDto.setStatus(card.getStatus());
				resDto.setDays("");
				MemberCardConfig cardConfig=this.memberCardConfigService.findById(card.getCardConfigId());
				if(ObjectUtils.equals(cardConfig, null)){
					continue;
				}
				BigDecimal divide = new BigDecimal(100).multiply(new BigDecimal(12));
				BigDecimal perRebate=card.getTotalCorpus().multiply(cardConfig.getRebate().divide(divide,2,RoundingMode.HALF_DOWN));
				resDto.setPerRebate(NumberFormatUtil.numberFormat(perRebate));
				
				 if(CardStatusEnum.Term_IN.getCode().equals(card.getStatus())){
					Integer total_day=cardConfig.getTimeLine()*ScheduleConstant.BACK_YIDUOBAO_TIMER;
					Integer old_day=(int) ((System.currentTimeMillis()-card.getCreateTime().getTime())/ScheduleConstant.ND);
					resDto.setMemo(total_day-old_day+"天后到期");
					resDto.setDays(String.valueOf(total_day-old_day));
				 }else{
					 resDto.setMemo("资金已返还");
				 }
				
				cardList.add(resDto);
			}
		} catch (Exception e) {
			LOGGER.error("YiDuoBaoCardFacadeImpl.java 我的购卡（益多宝）查询异常", e);
		}

		return cardList;
	}


	/**
	 * 获取所有卡配置
	 */
	public List<YiDuoBaoConfigResDto> getAllConfig() {
		List<YiDuoBaoConfigResDto> resDtos = new ArrayList<YiDuoBaoConfigResDto>();
		List<MemberCardConfig> cardConfigs = memberCardConfigService.findAll();
		for (MemberCardConfig config : cardConfigs) {
			YiDuoBaoConfigResDto resDto = new YiDuoBaoConfigResDto();
			resDto.setId(String.valueOf(config.getId()));
			resDto.setImgUrl(StringUtils.isBlank(config.getImgUrl()) ? "" : ImageConstantFacade.IMAGE_SERVER_ROOT_PATH
					+ config.getImgUrl());
			resDto.setDetailImgUrl(StringUtils.isBlank(config.getDetailUrl()) ? ""
					: ImageConstantFacade.IMAGE_SERVER_ROOT_PATH + config.getDetailUrl());
			resDto.setLowestMoney(config.getLowestMoney());
			resDto.setType(config.getType());
			resDto.setTypeName(config.getTypeName());
			resDto.setDetailImgHeight(AppConstant.DETAIL_IMG_HEIGHT);
			resDtos.add(resDto);
		}

		return resDtos;
	}

	/**
	 * 初始化退卡界面
	 */
	public YiDuoBaoCardBackResDto backCardInit(String cardNo, Long memberId) {
		// 获取当前要退的卡信息
		MemberCard card = new MemberCard();
		card.setCardNo(cardNo);
		card.setMemberId(memberId);
		card = memberCardService.findByMemberIdCardNo(card);
		YiDuoBaoCardBackResDto resDto = new YiDuoBaoCardBackResDto();
		resDto.setPhone(card.getAcount()==null?StringUtils.EMPTY:card.getAcount());
		MemberCardConfig cardConfig=memberCardConfigService.findById(card.getCardConfigId());
		// 话述从config表中获取
		String remark = cardConfig.getRemark();
		resDto.setTypeName(cardConfig.getTypeName());
		resDto.setRemark(remark == null ? StringUtils.EMPTY : remark);
		resDto.setTotalMoney(NumberFormatUtil.numberFormat(card.getTotalCorpus()));
		// 计算退卡费用(超过一个月要额外扣除手续费)
		Date createTime = card.getCreateTime();
		Long differTime = System.currentTimeMillis() - createTime.getTime();
		long day = differTime / ScheduleConstant.ND;
		String reMoney = "0.00";
		if (day > ScheduleConstant.BACK_YIDUOBAO_TIMER) {
			reMoney = NumberFormatUtil.numberFormat(card.getTotalCorpus().multiply(new BigDecimal(0.15)));
		}
		resDto.setReduceMoney(reMoney);
		
		return resDto;
	}

	/**
	 * 退卡最终提交
	 */
	public Object backCardCommit(String alabaoSid,YiDuoBaoCardBackRepDto reDto) {
		AlabaoUserDto alabaoUserDto = (AlabaoUserDto) AlabaoSessionCache.get(alabaoSid);
		// 获取当前要退的卡信息
		MemberCard card = new MemberCard();
		card.setCardNo(reDto.getCardNo());
		card.setMemberId(alabaoUserDto.getMemberId());
		card = memberCardService.findByMemberIdCardNo(card);
		// 更新当前卡的状态
		card.setStatus(CardStatusEnum.BACK_CARD.getCode());
		memberCardService.update(card);
		// 向退卡明细表添加数据
		MemberCardBack cardBack = new MemberCardBack();
		cardBack.setCardNo(card.getCardNo());
		cardBack.setAcount(alabaoUserDto.getAccount());
		cardBack.setBackStatus(MemberCardConstant.STATUS_WAIT); // 1待审核2审核通过3审核不通过4完成5到期系统自动转如意宝
		cardBack.setBankCardHolder(reDto.getCardHolder());
		cardBack.setBankCardNo(reDto.getBankCardNo());
		cardBack.setMemberId(card.getMemberId());
		cardBack.setBankName(reDto.getBankName());
		cardBack.setBankBranchName(reDto.getBankBranchName());
		// 计算退卡费用(超过一个月要额外扣除手续费)
		BigDecimal totalMoney = card.getTotalCorpus();
		Date createTime = card.getCreateTime();
		Long differTime = System.currentTimeMillis() - createTime.getTime();
		long day = differTime / ScheduleConstant.ND;
		if (day > ScheduleConstant.BACK_YIDUOBAO_TIMER) {
			totalMoney = card.getTotalCorpus().subtract(card.getTotalCorpus().multiply(new BigDecimal(0.15)));
		}
		cardBack.setMoney(totalMoney);
		memberCardBackService.add(cardBack);
		return Jsonp.success();
	}

	public Object delCard(String cardNo, Long memberId) {
		MemberCard memberCard=new MemberCard();
		memberCard.setCardNo(cardNo);
		memberCard.setMemberId(memberId);
		memberCard=this.memberCardService.findByMemberIdCardNo(memberCard);
		memberCard.setStatus(CardStatusEnum.ALREADY_DEL.getCode());
		memberCardService.update(memberCard);
		return Jsonp.success();
	}
}

package com.fushionbaby.schedule.membercard;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.aladingshop.alabao.model.AlabaoAccount;
import com.aladingshop.alabao.model.AlabaoShiftToRecord;
import com.aladingshop.alabao.service.AlabaoAccountService;
import com.aladingshop.alabao.service.AlabaoShiftToRecordService;
import com.aladingshop.card.enums.CardUserMoneyEnum;
import com.aladingshop.card.model.MemberCard;
import com.aladingshop.card.model.MemberCardConfig;
import com.aladingshop.card.model.MemberCardIncomeRecord;
import com.aladingshop.card.service.MemberCardConfigService;
import com.aladingshop.card.service.MemberCardIncomeRecordService;
import com.aladingshop.card.service.MemberCardService;
import com.fushionbaby.common.constants.AlabaoPayTypeConstant;
import com.fushionbaby.common.constants.CommonConstant;
import com.fushionbaby.common.constants.SmsConstant;
import com.fushionbaby.common.constants.SourceConstant;
import com.fushionbaby.common.util.NumberFormatUtil;
import com.fushionbaby.common.util.date.DateFormat;
import com.fushionbaby.member.model.Member;
import com.fushionbaby.member.service.MemberService;
import com.fushionbaby.sms.model.Sms;
import com.fushionbaby.sms.model.SmsTypeConfig;
import com.fushionbaby.sms.service.SmsService;
import com.fushionbaby.sms.service.SmsTypeConfigService;

/***
 * @description 益多宝(阿拉丁卡)的  月返还赠券的定时任务处理部分
 * @author 徐培峻
 * @date 2015年9月9日上午10:04:29
 */
@Service
public class TaskMemberCardRollingBearing {

	private static boolean runFlag = false;
	private static final Log LOGGER = LogFactory.getLog(TaskMemberCardRollingBearing.class);
	/** 卡的基本 */
	@Autowired
	private MemberCardService<MemberCard> memberCardService;
	/** 配置 */
	@Autowired
	private MemberCardConfigService<MemberCardConfig> memberCardConfigService;
	/** 利息 */
	@Autowired
	private MemberCardIncomeRecordService<MemberCardIncomeRecord> memberCardRateIncomeService;
	/** 会员 */
	@Autowired
	private MemberService<Member> memberService;

	/** 短信 */
	@Autowired
	private SmsService<Sms> smsService;
	/** 短信类型 */
	@Autowired
	private SmsTypeConfigService<SmsTypeConfig> smsTypeConfigService;

	/** 如意宝*/
	@Autowired
	private AlabaoAccountService<AlabaoAccount> alabaoAccountService;
	
	/** 如意宝的转入记录*/
	@Autowired
	private AlabaoShiftToRecordService<AlabaoShiftToRecord> alabaoShiftToRecordService;
	
	public void excute() {
		if (runFlag) {
			LOGGER.info("TaskMemberCardRollingBearing正在运行");
			return;
		}
		runFlag = true;

		LOGGER.info("TaskMemberCardRollingBearing开始运行");
		run();
		LOGGER.info("TaskMemberCardRollingBearing运行结束");
		runFlag = false;
	}

	@Transactional
	private void run() {
		try {
	        /** 可进行返利的卡列表*/
			List<MemberCard> memberCardList = this.memberCardService.findTaskCardList();
			for (MemberCard memberCard : memberCardList) {
				// 遍历 得到每个的配置信息
				MemberCardConfig cardConfig = this.memberCardConfigService.findById(memberCard.getCardConfigId());
				if (cardConfig == null || CommonConstant.YES.equals(cardConfig.getIsDisabled()))
					return;
				BigDecimal addMoneyZengQuan = new BigDecimal(0);
				BigDecimal divide = new BigDecimal(100).multiply(new BigDecimal(12));
				/** 设置利息增加（总利息，不算锁定的利息），赠券增加（面值） */
				if(memberCard.getTotalCorpus()!=null && cardConfig.getRebate()!=null)
				     addMoneyZengQuan = addMoneyZengQuan.add(memberCard.getTotalCorpus().multiply(cardConfig.getRebate().divide(divide,2,RoundingMode.HALF_DOWN)));
				//LOGGER.info("如意消费卡账号为："+"%%%%  计算卡号为" + memberCard.getCardNo() + "的赠券   %%%% 赠券金额为： "+addMoneyZengQuan);
				if(ObjectUtils.equals(memberCard.getTotalRebate(), null))
					memberCard.setTotalRebate(BigDecimal.ZERO);
				memberCard.setTotalRebate(memberCard.getTotalRebate().add(addMoneyZengQuan));
				memberCard.setUpdateTime(new Date());
				this.memberCardService.updateRebate(memberCard);
				/** 通过memberId 查询到用户的如意宝账号*/
//				AlabaoAccount alabao=this.alabaoAccountService.findByMemberId(memberCard.getMemberId());
				AlabaoAccount alabao=this.alabaoAccountService.findByAccount(memberCard.getAcount());
				LOGGER.info("今天："+DateFormat.dateToString(new Date())+".如意消费卡账号为："+alabao.getAccount()+"%%%%  计算卡号为" + memberCard.getCardNo() + "的赠券   %%%% 赠券金额为： "+NumberFormatUtil.numberFormat(addMoneyZengQuan));
				
				if(alabao.getBalance()==null){
					alabao.setBalance(BigDecimal.ZERO);
				}
				String serialNo=DateFormat.dateToSerialNo(new Date());
				/** 添加赠券额返还记录 */
				saveRebateIncomeRecord(memberCard, addMoneyZengQuan, alabao,serialNo);
				/** 加入如意宝转入记录*/
				saveAlabaoShiftToRecord(memberCard, addMoneyZengQuan, alabao,serialNo);
				/** 更新如意宝账户*/
				alabao.setBalance(alabao.getBalance().add(addMoneyZengQuan));
				alabaoAccountService.updateByAccount(alabao);
				LOGGER.info("%%%% 添加卡号为" + memberCard.getCardNo() + "的赠券额记录   %%%%");
				/**
				 * 再次查询当前卡信息校验是否成功更新到数据库
				 */
				MemberCard updateCard = memberCardService.findById(memberCard.getId());
		/** 发送短信 */
		String content = smsTypeConfigService.findById(SmsConstant.SMS_TYPE_YIDUOBAO2_ID).getSmsTemplate();
			   content = content
							.replace(SmsConstant.SMS_TEMPLATE_CODE, updateCard.getCardNo())
							.replace(SmsConstant.SMS_TEMPLATE_REBATE,NumberFormatUtil.numberFormat(addMoneyZengQuan))
							.replace(SmsConstant.SMS_TEMPLATE_REBLANCE, String.valueOf(updateCard.getTotalRebate()));
					smsService.sendSmsYiDuoBaoCode(content,alabao.getMobilePhone(), SourceConstant.WEB_CODE,SmsConstant.SMS_TYPE_YIDUOBAO2_ID);
			LOGGER.info("%%%% 发送短信告知如意宝用户:"+alabao.getAccount()+"卡号为：" + memberCard.getCardNo() + "的赠券收益"+addMoneyZengQuan+"   %%%%");
			}

		} catch (Exception e) {
			e.printStackTrace();
			LOGGER.error("定时任务项目中：TaskMemberCardRollingBearing.java计算用户益多宝收益和赠券出现异常", e);
		}
	}

	private void saveAlabaoShiftToRecord(MemberCard memberCard,BigDecimal addMoneyZengQuan, AlabaoAccount alabao,String serialNo) {
		AlabaoShiftToRecord toRecord = new AlabaoShiftToRecord();
		toRecord.setAccount(alabao.getAccount());
		toRecord.setMemberId(alabao.getMemberId());
		toRecord.setShiftToAccountType(AlabaoPayTypeConstant.ALABAOAPPYDB);
		toRecord.setTransferMoney(addMoneyZengQuan);
		toRecord.setCreateTime(new Date());
		toRecord.setMemo("阿拉丁卡收券"+NumberFormatUtil.numberFormat(addMoneyZengQuan));
		toRecord.setSerialNum(serialNo);
		
		toRecord.setAfterChangeMoney(alabao.getBalance().add(addMoneyZengQuan));
		toRecord.setBeforeChangeMoney(alabao.getBalance());
		this.alabaoShiftToRecordService.add(toRecord);
	}

	private void saveRebateIncomeRecord(MemberCard memberCard,BigDecimal addMoneyZengQuan, AlabaoAccount alabao,String serialNo) {
		MemberCardIncomeRecord cardIncome = new MemberCardIncomeRecord();
		cardIncome.setCreateTime(new Date());
		cardIncome.setIncomeAcount(alabao.getAccount());
		cardIncome.setIncomeCardCode(memberCard.getCardNo());
		cardIncome.setIncomeMemberId(memberCard.getMemberId());
		cardIncome.setSerialNum(serialNo);
		cardIncome.setIncomeMoney(addMoneyZengQuan);
		cardIncome.setIncomeType(CardUserMoneyEnum.COUPON_AMOUNT.getCode());
		this.memberCardRateIncomeService.add(cardIncome);
	}	

}

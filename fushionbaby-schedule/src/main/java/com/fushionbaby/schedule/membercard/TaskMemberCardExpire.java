/**
 * 
 */
package com.fushionbaby.schedule.membercard;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.aladingshop.alabao.model.AlabaoAccount;
import com.aladingshop.alabao.model.AlabaoShiftToRecord;
import com.aladingshop.alabao.service.AlabaoAccountService;
import com.aladingshop.alabao.service.AlabaoShiftToRecordService;
import com.aladingshop.card.enums.CardStatusEnum;
import com.aladingshop.card.enums.CardUserMoneyEnum;
import com.aladingshop.card.model.MemberCard;
import com.aladingshop.card.model.MemberCardBack;
import com.aladingshop.card.model.MemberCardIncomeRecord;
import com.aladingshop.card.service.MemberCardBackService;
import com.aladingshop.card.service.MemberCardIncomeRecordService;
import com.aladingshop.card.service.MemberCardService;
import com.fushionbaby.common.constants.AlabaoPayTypeConstant;
import com.fushionbaby.common.constants.SmsConstant;
import com.fushionbaby.common.constants.SourceConstant;
import com.fushionbaby.common.constants.alabao.MemberCardConstant;
import com.fushionbaby.common.util.date.DateFormat;
import com.fushionbaby.sms.model.Sms;
import com.fushionbaby.sms.model.SmsTypeConfig;
import com.fushionbaby.sms.service.SmsService;
import com.fushionbaby.sms.service.SmsTypeConfigService;

/**
 * @description 益多宝卡到期自动将本金转到
 * @author 孙涛
 * @date 2015年10月12日下午4:34:58
 */
@Service
public class TaskMemberCardExpire {
	private static boolean runFlag = false;
	private static final Log LOGGER = LogFactory.getLog(TaskMemberCardExpire.class);

	/** 卡的基本 */
	@Autowired
	private MemberCardService<MemberCard> memberCardService;
	@Autowired
	private MemberCardBackService<MemberCardBack> memberCardBackService;
	@Autowired
	private AlabaoAccountService<AlabaoAccount> alabaoAccountService;
	@Autowired
	private AlabaoShiftToRecordService<AlabaoShiftToRecord> alabaoShiftToRecordService;
	@Autowired
	private MemberCardIncomeRecordService<MemberCardIncomeRecord> memberCardRateIncomeService;

	/** 短信 */
	@Autowired
	private SmsService<Sms> smsService;
	/** 短信类型 */
	@Autowired
	private SmsTypeConfigService<SmsTypeConfig> smsTypeConfigService;
	
	public void excute() {
		if (runFlag) {
			LOGGER.info("TaskMemberCardExpire正在运行");
			return;
		}
		runFlag = true;

		LOGGER.info("TaskMemberCardExpire开始运行");
		run();
		LOGGER.info("TaskMemberCardExpire运行结束");
		runFlag = false;
	}

	@Transactional
	private void run() {
		try {
			/** 获取到期限的所有卡列表 */
			List<MemberCard> memberCardList = this.memberCardService.findTaskExpire();
			for (MemberCard memberCard : memberCardList) {
				// 更新当前卡的状态
				memberCard.setStatus(CardStatusEnum.Term_OUT.getCode());
				memberCard.setUpdateTime(new Date());
				memberCardService.update(memberCard);
				// 向退卡明细表添加数据
				AlabaoAccount acount = alabaoAccountService.findByAccount(memberCard.getAcount());
				MemberCardBack cardBack = new MemberCardBack();
				// 获取阿拉宝
				//AlabaoAccount acount = alabaoAccountService.findByMemberId(memberCard.getMemberId());
				cardBack.setMemberId(memberCard.getMemberId());
				cardBack.setCardNo(memberCard.getCardNo());
				cardBack.setAcount(acount.getAccount());
				cardBack.setMoney(memberCard.getTotalCorpus());
				cardBack.setBankCardNo(acount.getAccount());
				cardBack.setBankCardHolder(acount.getTrueName());
				cardBack.setBackStatus(MemberCardConstant.STATUS_TURN_TO_ALABAO);// 1待审核2审核通过3审核不通过4完成5到期系统自动转如意宝
				memberCardBackService.add(cardBack);

				if (acount.getBalance()==null) {
					acount.setBalance(BigDecimal.ZERO);
					
				}
				String serialNo=DateFormat.dateToSerialNo(new Date());
				// 向如意宝转入记录表添加数据
				saveShiftToRecord(memberCard, acount,serialNo);
				/** 添加赠券额返还记录 */
				saveRebateIncomeRecord(memberCard, cardBack.getMoney(), acount.getAccount(),serialNo);
				
				// 更新如意宝主表信息
				acount.setBalance(acount.getBalance().add(memberCard.getTotalCorpus()));
				alabaoAccountService.updateByAccount(acount);

				
				/** 发短信告诉用户阿拉丁卡本金部分，自动转入如意宝账号*/
				String content = smsTypeConfigService.findById(SmsConstant.SMS_TYPE_YIDUOBAO4_ID).getSmsTemplate();
				   content = content
								.replace(SmsConstant.SMS_TEMPLATE_CODE, cardBack.getCardNo())
								.replace(SmsConstant.SMS_TEMPLATE_ALABAO_ACCOUNT, acount.getAccount())
								.replace(SmsConstant.SMS_TEMLATE_FACEVALUE,String.valueOf(cardBack.getMoney()));
						smsService.sendSmsYiDuoBaoCode(content,acount.getAccount(), SourceConstant.WEB_CODE,SmsConstant.SMS_TYPE_YIDUOBAO4_ID);
		LOGGER.info("今天是："+DateFormat.dateToString(new Date())+"%%%% 发送短信告知如意宝用户:"+acount.getAccount()+"卡号为：" + memberCard.getCardNo() + "到期自动转入如意消费卡，金额为"+memberCard.getTotalCorpus()+"   %%%%");
			}

		} catch (Exception e) {
			e.printStackTrace();
			LOGGER.error("定时任务项目中：TaskMemberCardExpire.java计算用户益多宝收益和赠券出现异常", e);
		}
	}



	private void saveShiftToRecord(MemberCard memberCard, AlabaoAccount acount,String serialNo) {
		AlabaoShiftToRecord toRecord = new AlabaoShiftToRecord();
		toRecord.setAccount(acount.getAccount());
		toRecord.setMemberId(acount.getMemberId());
		toRecord.setShiftToAccountType(AlabaoPayTypeConstant.ALABAOAPPYDB);
		toRecord.setTransferMoney(memberCard.getTotalCorpus());
		toRecord.setMemo("阿拉丁卡到期转入如意消费卡，金额为："+memberCard.getTotalCorpus());
		toRecord.setSerialNum(serialNo);
		
		toRecord.setAfterChangeMoney(acount.getBalance().add(memberCard.getTotalCorpus()));
		toRecord.setBeforeChangeMoney(acount.getBalance());
		alabaoShiftToRecordService.add(toRecord);
	}
	
	private void saveRebateIncomeRecord(MemberCard memberCard,BigDecimal money, String account, String serialNo) {
		MemberCardIncomeRecord cardIncome = new MemberCardIncomeRecord();
		cardIncome.setCreateTime(new Date());
		cardIncome.setIncomeAcount(account);
		cardIncome.setIncomeCardCode(memberCard.getCardNo());
		cardIncome.setIncomeMemberId(memberCard.getMemberId());
		cardIncome.setSerialNum(serialNo);
		cardIncome.setIncomeMoney(money);
		cardIncome.setIncomeType(CardUserMoneyEnum.CORPUS.getCode());
		this.memberCardRateIncomeService.add(cardIncome);
		
	}
	
}

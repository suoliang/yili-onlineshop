package com.fushionbaby.schedule.alabao;

import java.math.BigDecimal;
import java.util.List;

import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.aladingshop.alabao.model.AlabaoAccount;
import com.aladingshop.alabao.model.AlabaoConfig;
import com.aladingshop.alabao.model.AlabaoDailyRate;
import com.aladingshop.alabao.model.AlabaoIncomeRecord;
import com.aladingshop.alabao.service.AlabaoAccountService;
import com.aladingshop.alabao.service.AlabaoConfigService;
import com.aladingshop.alabao.service.AlabaoDailyRateService;
import com.aladingshop.alabao.service.AlabaoIncomeRecordService;
import com.fushionbaby.common.constants.GlobalConfigConstant;
import com.fushionbaby.common.util.NumberFormatUtil;

/**
 * @description 如意宝计算收益
 * @author 索亮
 * @date 2015年9月11日上午9:10:20
 */
@Service
public class TaskCountIncome {
	private static boolean runFlag = false;
	/***日志*/
	private static final Log LOGGER = LogFactory.getLog(TaskCountIncome.class);
	/**利率配置信息*/
	@Autowired
	private AlabaoConfigService<AlabaoConfig> alabaoConfigService;
	/**主账号信息*/
	@Autowired
	private AlabaoAccountService<AlabaoAccount> alabaoAccountService;
	/**收益记录*/
	@Autowired
	private AlabaoIncomeRecordService<AlabaoIncomeRecord> alabaoIncomeRecordService;
	/**每日利率值*/
	@Autowired
	private AlabaoDailyRateService<AlabaoDailyRate> alabaoDailyRateService;
	
	public void excute() {
		if (runFlag) {
			LOGGER.info("TaskCountIncome正在运行");
			return;
		}
		runFlag = true;

		LOGGER.info("TaskCountIncome开始运行");
		run();
		LOGGER.info("TaskCountIncome运行结束");
		runFlag = false;
	}
	
	private void run(){
		try {
			List<AlabaoAccount> list = alabaoAccountService.findAll();
			if (CollectionUtils.isEmpty(list)) {
				return;
			}
			AlabaoConfig alabaoConfig = alabaoConfigService.findByRateCode(GlobalConfigConstant.RATECODE);
			if (ObjectUtils.equals(alabaoConfig, null)) {
				return;
			}
			
			AlabaoDailyRate alabaoDailyRate = new AlabaoDailyRate();
			alabaoDailyRate.setRate(alabaoConfig.getRate());
			alabaoDailyRateService.add(alabaoDailyRate);
			LOGGER.info("每日利率添加正常");
			for (AlabaoAccount alabaoAccount : list) {
				/**昨日收益*/
				BigDecimal yesterdayIncome = alabaoAccount.getBalance().multiply(alabaoConfig.getRate());
				if (new BigDecimal(NumberFormatUtil.numberFormat(yesterdayIncome)).compareTo(BigDecimal.ZERO) <= 0 ) {
					if (alabaoAccount.getYesterdayIncome().compareTo(BigDecimal.ZERO) != 0) {
						alabaoAccount.setYesterdayIncome(BigDecimal.ZERO);
						alabaoAccountService.updateByAccount(alabaoAccount);
						LOGGER.info("账户"+alabaoAccount.getAccount()+"0收益主表更新正常");
					}
					continue;
				}
				AlabaoIncomeRecord alabaoIncomeRecord = new AlabaoIncomeRecord();
				alabaoIncomeRecord.setAccount(alabaoAccount.getAccount());
				alabaoIncomeRecord.setBfIncomeMoney(alabaoAccount.getBalance());
				alabaoIncomeRecord.setIncomeMoney(yesterdayIncome);
				/**总余额*/
				BigDecimal balance = alabaoAccount.getBalance().add(yesterdayIncome);
				alabaoIncomeRecord.setAfIncomeMoney(balance);
				alabaoIncomeRecord.setMemberId(alabaoAccount.getMemberId());
				alabaoIncomeRecordService.add(alabaoIncomeRecord);
				LOGGER.info("账户"+alabaoAccount.getAccount()+"收益表添加正常");
				/**总收益*/
				BigDecimal totalIncome = alabaoAccount.getTotalIncome().add(yesterdayIncome);
				alabaoAccount.setBalance(balance);
				alabaoAccount.setYesterdayIncome(yesterdayIncome);
				alabaoAccount.setTotalIncome(totalIncome);
				alabaoAccountService.updateByAccount(alabaoAccount);
				LOGGER.info("账号"+alabaoAccount.getAccount()+"主表更新正常");
			}
			LOGGER.info("如意宝收益操作正常");
		} catch (Exception e) {
			e.printStackTrace();
			LOGGER.error("收益计算出错", e);
		}
	}
	
}

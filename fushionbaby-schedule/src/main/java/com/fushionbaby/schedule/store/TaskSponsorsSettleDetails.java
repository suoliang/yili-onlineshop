package com.fushionbaby.schedule.store;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.aladingshop.store.model.StoreSponsorsDailyDetails;
import com.aladingshop.store.model.StoreSponsorsSettlementDetails;
import com.aladingshop.store.service.StoreSponsorsDailyDetailsService;
import com.aladingshop.store.service.StoreSponsorsSettlementDetailsService;
import com.fushionbaby.common.constants.CommonConstant;
import com.fushionbaby.common.constants.config.ImportanceConfigConstant;
import com.fushionbaby.common.dto.store.StoreFeeDto;
import com.fushionbaby.common.enums.PaymentTypeEnum;
import com.fushionbaby.common.enums.settle.SponsorsSettleStatusEnum;
import com.fushionbaby.config.service.SysmgrImportanceConfigService;
import com.fushionbaby.order.model.OrderBase;
import com.fushionbaby.order.model.OrderFinance;
import com.fushionbaby.order.service.OrderBaseService;
import com.fushionbaby.order.service.OrderFinanceService;
import com.fushionbaby.payment.model.PaymentAppUnion;
import com.fushionbaby.payment.model.PaymentAppWx;
import com.fushionbaby.payment.model.PaymentAppZfb;
import com.fushionbaby.payment.model.PaymentWapZfbJsdz;
import com.fushionbaby.payment.model.PaymentWebUnion;
import com.fushionbaby.payment.model.PaymentWebWx;
import com.fushionbaby.payment.model.PaymentWebZfbJsdz;
import com.fushionbaby.payment.service.PaymentAppUnionService;
import com.fushionbaby.payment.service.PaymentAppWxService;
import com.fushionbaby.payment.service.PaymentAppZfbService;
import com.fushionbaby.payment.service.PaymentWapZfbJsdzService;
import com.fushionbaby.payment.service.PaymentWebUnionService;
import com.fushionbaby.payment.service.PaymentWebWxService;
import com.fushionbaby.payment.service.PaymentWebZfbJsdzService;

/***
 * @description 商户结算明细
 * @author 索亮
 * @date 2015年12月21日上午9:54:53
 */
@Service
public class TaskSponsorsSettleDetails {
	
	private static boolean runFlag = false;
	
	/***日志*/
	private static final Log LOGGER = LogFactory.getLog(TaskSponsorsSettleDetails.class);
	
	@Autowired
	private OrderFinanceService<OrderFinance> orderFinanceService;
	@Autowired
	private OrderBaseService<OrderBase> orderBaseService;
	@Autowired
	private PaymentAppZfbService<PaymentAppZfb> appZfbService;
	@Autowired
	private PaymentAppWxService<PaymentAppWx> appWxService;
	@Autowired
	private PaymentAppUnionService<PaymentAppUnion> appUnionService;
	@Autowired
	private PaymentWebZfbJsdzService<PaymentWebZfbJsdz> webZfbJsdzService;
	@Autowired
	private PaymentWebWxService<PaymentWebWx> webWxService;
	@Autowired
	private PaymentWebUnionService<PaymentWebUnion> webUnionService;
	@Autowired
	private PaymentWapZfbJsdzService<PaymentWapZfbJsdz> wapZfbJsdzService;
	
	@Autowired
	private StoreSponsorsSettlementDetailsService<StoreSponsorsSettlementDetails> settlementDetailsService;
	@Autowired
	private SysmgrImportanceConfigService importanceConfigService;
	
	@Autowired
	private StoreSponsorsDailyDetailsService<StoreSponsorsDailyDetails> sponsorsDailyDetailsService;
	
	public void excute() {
		if (runFlag) {
			LOGGER.info("TaskSponsorsSettleDetails正在运行");
			return;
		}
		runFlag = true;

		LOGGER.info("TaskSponsorsSettleDetails开始运行");
		run();
		LOGGER.info("TaskSponsorsSettleDetails运行结束");
		runFlag = false;
	}
	
	private void run(){
		
		List<OrderFinance>	list = orderFinanceService.findListByFinanceStatusAndLastPayTime(CommonConstant.YES,new Date());
		if (CollectionUtils.isEmpty(list)) {
			return;
		}
		Calendar calendar = Calendar.getInstance(); //得到日历
		calendar.setTime(new Date());//把时间赋给日历
		calendar.add(Calendar.DAY_OF_MONTH, -1);  //设置为前一天
		Date dBefore = calendar.getTime();   //得到前一天的时间
		SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd");
		String yesterDay = df.format(dBefore);
		
		Map<String, List<BigDecimal>> map = new HashMap<String, List<BigDecimal>>();
		
		String zfbRate = importanceConfigService.findByCode(ImportanceConfigConstant.ZFBCOMMISSIONRATE).getValue();
		String wxRate = importanceConfigService.findByCode(ImportanceConfigConstant.WXCOMMISSIONRATE).getValue();
		String ylRate = importanceConfigService.findByCode(ImportanceConfigConstant.YLCOMMISSIONRATE).getValue();
		
		for (OrderFinance orderFinance : list) {
			Long memberId = orderFinance.getMemberId();
			String orderCode = orderFinance.getOrderCode();
			OrderBase orderBase = orderBaseService.findByMemberIdAndOrderCode(memberId, orderCode);
			if (ObjectUtils.equals(orderBase, null)) {
				LOGGER.info("商户结算订单数据有误:"+orderCode);
				continue;
			}
			StoreSponsorsSettlementDetails sssd = new StoreSponsorsSettlementDetails();
			sssd.setSourceCode(orderBase.getSourceCode());
			sssd.setStoreCode(orderFinance.getStoreCode());
			sssd.setOrderCode(orderCode);
			sssd.setMemberId(memberId);
			String paymentType = orderFinance.getPaymentType();
			sssd.setPaymentType(paymentType);
			StoreFeeDto storeFeeDto = getStoreFeeDto(orderFinance,zfbRate,wxRate,ylRate);
			
			String key = orderFinance.getStoreCode();
			BigDecimal value1 = storeFeeDto.getSettleAmount();
			BigDecimal value2 = storeFeeDto.getPlatformFee();
			BigDecimal value3 = storeFeeDto.getRealIncomeAmount();
			
			/**交易流水号*/
			sssd.setTradeSerialNumber(storeFeeDto.getTradeSerialNumber());
			sssd.setSettleAmount(value1);
			/**平台费*/
			sssd.setPlatformFee(value2);
			/**实际金额*/
			sssd.setRealIncomeAmount(value3);
			sssd.setPaymentCompleteTime(orderFinance.getPaymentCompleteTime());
			/**每日结算序列-门店号+日期*/
			sssd.setDailyNumber(key+yesterDay);
			settlementDetailsService.add(sssd);
			
			List<BigDecimal> listDecimal = new ArrayList<BigDecimal>();
			if(map.containsKey(key)){
				listDecimal.add(map.get(key).get(0).add(value1));
				listDecimal.add(map.get(key).get(1).add(value2));
				listDecimal.add(map.get(key).get(2).add(value3));
				listDecimal.add(map.get(key).get(3).add(BigDecimal.ONE));
				map.put(key, listDecimal);
			} else {
				listDecimal.add(value1);
				listDecimal.add(value2);
				listDecimal.add(value3);
				listDecimal.add(BigDecimal.ONE);
				map.put(key, listDecimal);
			}
		}
		
		for(Map.Entry<String, List<BigDecimal>> mapList: map.entrySet()){
			StoreSponsorsDailyDetails ssdd = new StoreSponsorsDailyDetails();
			ssdd.setStoreCode(mapList.getKey());
			ssdd.setSettlePeriod(dBefore);
			ssdd.setSettleOrderAmount(mapList.getValue().get(0));
			ssdd.setPlatformFee(mapList.getValue().get(1));
			ssdd.setRealIncomeAmount(mapList.getValue().get(2));
			ssdd.setSettleOrderCount(mapList.getValue().get(3).intValue());
			ssdd.setDailyNumber(mapList.getKey()+yesterDay);
			ssdd.setSettlementStatus(SponsorsSettleStatusEnum.NOT_SETTLED.getCode());
			sponsorsDailyDetailsService.add(ssdd);
		}
	
	}

	private StoreFeeDto getStoreFeeDto(OrderFinance orderFinance,String zfbRate,String wxRate,String ylRate) {
		
		Long memberId = orderFinance.getMemberId();
		String orderCode = orderFinance.getOrderCode();
		String paymentType = orderFinance.getPaymentType();
		BigDecimal settleAmount = orderFinance.getPaymentTotalActual();
		
		String tradeSerialNumber = orderCode;
		if (PaymentTypeEnum.PAYMENT_ZFB_APP.getCode().equals(paymentType)) {
			PaymentAppZfb appZfb = appZfbService.getByMemberIdAndOrderCode(memberId, orderCode);
			tradeSerialNumber = appZfb.getZfbTradeNo();
			return setStoreFeeDto(settleAmount, zfbRate, tradeSerialNumber);
		}
		if (PaymentTypeEnum.PAYMENT_WX_APP.getCode().equals(paymentType)) {
			PaymentAppWx appWx = appWxService.getByMemberIdAndOrderCode(memberId, orderCode);
			tradeSerialNumber = appWx.getWxTransactionId();
			return setStoreFeeDto(settleAmount, wxRate, tradeSerialNumber);
		}
		if (PaymentTypeEnum.PAYMENT_ZXYL_APP.getCode().equals(paymentType)) {
			PaymentAppUnion appUnion = appUnionService.getByMemberIdAndOrderCode(memberId, orderCode);
			tradeSerialNumber = appUnion.getTn();
			return setStoreFeeDto(settleAmount, ylRate, tradeSerialNumber);
		}
		if (PaymentTypeEnum.PAYMENT_ZFB_WEB_JSDZ.getCode().equals(paymentType)) {
			PaymentWebZfbJsdz webZfbJsdz = webZfbJsdzService.queryByMemberIdAndOrderCode(memberId, orderCode);
			tradeSerialNumber = webZfbJsdz.getZfbTradeNo();
			return setStoreFeeDto(settleAmount, zfbRate, tradeSerialNumber);
		}
		if (PaymentTypeEnum.PAYMENT_WX_WEB.getCode().equals(paymentType)) {
			PaymentWebWx webWx = webWxService.getByMemberIdAndOrderCode(memberId, orderCode);
			tradeSerialNumber = webWx.getWxTransactionId();
			return setStoreFeeDto(settleAmount, wxRate, tradeSerialNumber);
		}
		if (PaymentTypeEnum.PAYMENT_ZXYL_WEB.getCode().equals(paymentType)) {
			PaymentWebUnion webUnion = webUnionService.getByMemberIdAndOrderCode(memberId, orderCode);
			tradeSerialNumber = webUnion.getTn();
			return setStoreFeeDto(settleAmount, ylRate, tradeSerialNumber);
		}
		if (PaymentTypeEnum.PAYMENT_ZFB_WAP_JSDZ.getCode().equals(paymentType)) {
			PaymentWapZfbJsdz wapZfbJsdz = wapZfbJsdzService.queryByMemberIdAndOrderCode(memberId, orderCode);
			tradeSerialNumber = wapZfbJsdz.getZfbTradeNo();
			return setStoreFeeDto(settleAmount, zfbRate, tradeSerialNumber);
		}
		return setStoreFeeDto(settleAmount, "0", orderCode);
	}

	private StoreFeeDto setStoreFeeDto(BigDecimal settleAmount, String rate,
			String tradeSerialNumber) {
		StoreFeeDto storeFeeDto = new StoreFeeDto();
		BigDecimal platformFee = settleAmount.multiply(new BigDecimal(rate));
		BigDecimal realIncomeAmount = settleAmount.subtract(platformFee);
		storeFeeDto.setSettleAmount(settleAmount);
		storeFeeDto.setPlatformFee(platformFee);
		storeFeeDto.setRealIncomeAmount(realIncomeAmount);
		storeFeeDto.setTradeSerialNumber(tradeSerialNumber);
		return storeFeeDto;
	}
	
}

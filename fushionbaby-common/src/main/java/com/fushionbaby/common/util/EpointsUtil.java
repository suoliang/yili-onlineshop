package com.fushionbaby.common.util;

import java.math.BigDecimal;
import java.util.Random;

/**
 * @author 张明亮
 * 订单相关计算工具类
 */
public class EpointsUtil {
	
	public static final Double EPOINTS_EXCHANGE_RATE = 100d;// 会员积分兑换汇率,例:100积分==1块钱
	
	/**
	 * 根据传入的积分,换算出积分可以兑换的金额
	 * @param epoints
	 * @return
	 */
	public static BigDecimal epointsToMoney(BigDecimal epoints){
		BigDecimal epointsMoney = new BigDecimal(0);
		if(epoints==null){
			return epointsMoney;
		}
		epointsMoney = epoints.divide(BigDecimal.valueOf(EPOINTS_EXCHANGE_RATE));
		return epointsMoney;
	}
	
	/**
	 * 根据传入的钱数计算出获得的积分
	 * @return
	 */
	public static BigDecimal moneyToEpoints(BigDecimal money){
		BigDecimal epoints = BigDecimal.valueOf(0);
		if(money==null){
			return BigDecimal.valueOf(0);
		}
		epoints = money.multiply( new BigDecimal(EPOINTS_EXCHANGE_RATE));
		return epoints;
	}
	
	/**
	 * 订单号生成
	 * @return
	 */
	public static String generateOrdrNo() {
        StringBuilder sb = new StringBuilder();
        for(int i=0;i<7;i++) {
        	sb.append(new Random().nextInt(9));
        }
        return sb.toString();
	}
}

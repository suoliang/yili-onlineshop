package com.fushionbaby.sysactivity.service.impl;

import java.util.HashSet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fushionbaby.common.constants.LotteryConstant;
import com.fushionbaby.common.util.RandomNumUtil;
import com.fushionbaby.sysactivity.dao.LotteryPrizeDao;
import com.fushionbaby.sysactivity.model.LotteryPrize;
import com.fushionbaby.sysactivity.service.LotteryPrizeService;

/**
 * @author 张明亮
 */
@Service
public class LotteryPrizeServiceImpl implements LotteryPrizeService<LotteryPrize> {

	@Autowired
	private LotteryPrizeDao lotteryPrizeDao;
	
	public LotteryPrize getByLevel(Long level) {
		if(level==null){
			return null;
		}
		return lotteryPrizeDao.getByLevel(level);
	}

	public void updateLotteryRemainPrize(LotteryPrize lotteryPrize) {
		lotteryPrizeDao.updateLotteryRemainPrize(lotteryPrize);
	}
	
	/**
	 * 抽奖业务开始
	 * @param orderStint  系统配置达到已付款总数
	 * @param orderCount  当前系统订单数量
	 * @param lotteryPrize
	 * @return
	 */
	public LotteryPrize getRandomLotteryPrize(int orderStint, int orderCount) {
		LotteryPrize lotteryPrize = null;
		/*
		 * 订单总数量在规定活动范围内,没有达到预期的订单数量,只能得到大众普通奖品
		 */
		if (orderCount > orderStint){
			/*
			 * 在活动期间已付款订单超过了orderStint设定的数量,则允许出现1和2等奖的概率
			 * 1.随机函数调用
			 * 2.查询得到奖品、得不到获取普通奖品
			 */
			HashSet<Integer> oneSet = new HashSet<Integer>();//一等奖,2个
			RandomNumUtil.randomSet(0,LotteryConstant.PROBABILITY,LotteryConstant.ONE_NUM,oneSet);
			int randowNumOne = (int)(Math.random()*LotteryConstant.PROBABILITY);//获取一等奖随机数
			if(oneSet.contains(randowNumOne)){
				//一等奖
				lotteryPrize = this.getByLevel(LotteryConstant.ONE);
			}
			
			//一等奖没有抽到接着看二等奖
			HashSet<Integer> towSet = new HashSet<Integer>();//二等奖,50个
			RandomNumUtil.randomSet(0,LotteryConstant.PROBABILITY,LotteryConstant.TOW_NUM,towSet);
			int randowNumTow = (int)(Math.random()*LotteryConstant.PROBABILITY);//获取二等奖随机数
			if(lotteryPrize==null && towSet.contains(randowNumTow)){
				//二等奖
				lotteryPrize = this.getByLevel(LotteryConstant.TOW);
			}
		}
		
		//如果都没抽到就是大众普通奖品
		if(lotteryPrize==null){
			//查询得到普通奖品
			lotteryPrize = this.getByLevel(LotteryConstant.THREE);
		}else{
			//抽到1或者2等奖品了,更新奖池
			if(lotteryPrize.getRemainPrize()<=0){
				//奖池奖品已经没有了、还是给他普通奖品吧
				lotteryPrize = this.getByLevel(LotteryConstant.THREE);
			}else{
				//设置被减去后的奖池奖品剩余数量
				int remainPrize = lotteryPrize.getRemainPrize()-1;
				lotteryPrize.setRemainPrize(remainPrize);
				this.updateLotteryRemainPrize(lotteryPrize);
			}
		}
		return lotteryPrize;
	}
}

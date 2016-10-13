package com.fushionbaby.sysactivity.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fushionbaby.common.dto.UserDto;
import com.fushionbaby.sysactivity.dao.LotteryInfoDao;
import com.fushionbaby.sysactivity.model.LotteryInfo;
import com.fushionbaby.sysactivity.service.LotteryInfoService;

/**
 * 
 * @author King 索亮
 *
 */
@Service
public class LotteryInfoServiceImpl implements LotteryInfoService<LotteryInfo>{
	@Autowired
	private LotteryInfoDao lotteryInfoDao;
	
	public void add(LotteryInfo lotteryInfo) {
		lotteryInfoDao.add(lotteryInfo);
	}

	public LotteryInfo findByMemberId(Long memberId) {
		return lotteryInfoDao.findByMemberId(memberId);
	}

	public LotteryInfo findByLoginName(String loginName) {
		return lotteryInfoDao.findByLoginName(loginName);
	}
	
	/**
	 * 根据memberId更新会员抽奖已经使用过的抽奖次数
	 * @param lotteryInfo
	 */
	public void updateLotteryInfoNum(LotteryInfo lotteryInfo){
		lotteryInfoDao.updateLotteryInfoNum(lotteryInfo);
	}
	
	/**test*/
	public void deleteById(Long id) {
		lotteryInfoDao.deleteById(id);
	}

	/**
	 * 更新或者新增会员抽奖记录
	 * @param user
	 * @param lotteryInfo
	 */
	public void updateOrSaveLotteryInfo(UserDto user, LotteryInfo lotteryInfo) {
		if (lotteryInfo != null) {//更新
			int useNum = lotteryInfo.getNum() + 1;
			lotteryInfo.setNum(useNum);//记录抽奖已经使用过的次数+1
			this.updateLotteryInfoNum(lotteryInfo);
		} else {//新增
			LotteryInfo lotteryInfoTmp = new LotteryInfo();
			lotteryInfoTmp.setMemberId(user.getMemberId());
			lotteryInfoTmp.setLoginName(user.getLoginName());
			lotteryInfoTmp.setNum(1);//记录抽奖已经使用过的次数
			this.add(lotteryInfoTmp);
		}
	}
}

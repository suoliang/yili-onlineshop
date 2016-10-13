package com.fushionbaby.sysactivity.service.impl;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.fushionbaby.common.dto.UserDto;
import com.fushionbaby.common.util.BasePagination;
import com.fushionbaby.sysactivity.dao.LotteryDetailDao;
import com.fushionbaby.sysactivity.model.LotteryDetail;
import com.fushionbaby.sysactivity.service.LotterDetailService;

/***
 * 抽奖明细
 * @author xupeijun
 */
@Service
public class LotterDetailServiceImpl implements
		LotterDetailService<LotteryDetail> {

	
	
	@Autowired 
	private LotteryDetailDao lotteryDetailDao;
	
	public void add(LotteryDetail entity) throws DataAccessException {
		this.lotteryDetailDao.add(entity);

	}

	public void deleteById(Long id) throws DataAccessException {

		this.lotteryDetailDao.deleteById(id);
	}

	public void update(LotteryDetail entity) throws DataAccessException {

		this.lotteryDetailDao.update(entity);
	}

	public LotteryDetail findById(Long id) throws DataAccessException {
		return this.lotteryDetailDao.findById(id);
	}

	public List<LotteryDetail> findAll() throws DataAccessException {
		return this.lotteryDetailDao.findAll();
	}
	
	/**查询:1.根据member_id查询 返回封装类对象集合*/
	public List<LotteryDetail> findByMemberId(Long memberId){
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("memberId",memberId);
		return this.lotteryDetailDao.findWhereList(map);
	}
	
	/**2.根据login_name查询  返回封装类对象集合*/
	public BasePagination findByLoginName(BasePagination page){
		int total = lotteryDetailDao.getTotal(page.getSearchParamsMap());
		List<LotteryDetail> result = new ArrayList<LotteryDetail>();
		if(total != 0) {
			result = lotteryDetailDao.getPageList(page.getSearchParamsMap());
		}
		page.setTotal(total);
		page.setResult(result);
		return page;
	}
	
	/**
	 * 保存抽奖明细记录
	 * @param user
	 * @param memberId
	 * @param lotteryPrize
	 */
	public void saveLotteryDetail(UserDto user,long lotteryPrizeId) {
		LotteryDetail lotteryDetail = new LotteryDetail();
		lotteryDetail.setLoginName(user.getLoginName());
		lotteryDetail.setMemberId(user.getMemberId());
		lotteryDetail.setPrizeId(lotteryPrizeId);
		this.add(lotteryDetail);
	}
}

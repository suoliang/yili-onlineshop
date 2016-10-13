package com.fushionbaby.act.activity.service;


import java.util.List;

import com.fushionbaby.act.activity.model.ActCard;
import com.fushionbaby.common.dto.CouponCardDto;
import com.fushionbaby.common.service.BaseService;
import com.fushionbaby.common.util.BasePagination;

/**
 * 
 * @author cyla
 * 
 */
public interface ActCardService<T extends ActCard> extends BaseService<T> {

	/**通过代金券号获得对象*/
	ActCard findActCardByCardNo(String cardNo);
	/***
	 * 通过优惠券卡号获得优惠券的的优惠金额，要进行是否有效判断
	 * 
	 * @param cardNo
	 * @param password
	 * @return
	 */
	CouponCardDto getAmountByCardNo(String cardNo);

	BasePagination getListPage(BasePagination page);

	/***
	 * 根据优惠卡号来更新卡信息(加运算)
	 * 
	 * @param cardNo
	 */
	void updateByCardNo(String cardNo);
	/***
	 * 系统定时任务更新卡信息(减运算)
	 * 使用时间不做减运算，有可能其他用户使用了(多次)
	 * @param cardno
	 */
	void updateBySysByCardNo(String cardno);

	void deleteByTypeId(Long id);
	/***
	 * 得到会员用户的优惠券列表
	 * @param memberId
	 * @return
	 */
	List<ActCard> findByMemberId(Long memberId);
	
	
	/***
	 * 通过 卡号和密码查询
	 * @param cardNo
	 * @param password
	 * @return
	 */
	ActCard findByCardNoAndPassword(String cardNo,String password);

}

package com.aladingshop.alabao.service;

import java.util.Map;

import org.springframework.dao.DataAccessException;

import com.aladingshop.alabao.model.AlabaoOrder;
import com.fushionbaby.common.util.BasePagination;

public interface AlabaoOrderService <T extends AlabaoOrder> {
	
	public void add(AlabaoOrder alabaoOrder);
	
	public void deleteById(Long id);
	
	public void updateById(AlabaoOrder alabaoOrder);	
	
	public AlabaoOrder findById(Long id);

	
	/**
	 *            分页
	 */
	BasePagination getListPage(BasePagination page) throws DataAccessException;
	
	/**
	 * 根据memberId和orderCode查询信息
	 * @param memberId
	 * @param orderCode
	 * @return
	 */
	AlabaoOrder findByMemberIdOrderCode(Long memberId , String orderCode);
	
	
	/**
	 * 根据memberId和orderCode修改信息
	 * @return
	 */
	void updateByMemberIdOrderCode(AlabaoOrder alabaoOrder);
	
	String getListPageTotalActual(BasePagination page);
}

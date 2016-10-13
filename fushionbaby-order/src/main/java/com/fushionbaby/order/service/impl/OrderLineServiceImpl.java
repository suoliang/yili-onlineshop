package com.fushionbaby.order.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.fushionbaby.common.util.BasePagination;
import com.fushionbaby.order.dao.OrderLineDao;
import com.fushionbaby.order.model.OrderLine;
import com.fushionbaby.order.model.OrderLine;
import com.fushionbaby.order.service.OrderLineService;

/**
 * @author 张明亮
 *
 */
@Service
public class OrderLineServiceImpl implements OrderLineService<OrderLine> {
    @Autowired
	private OrderLineDao soSoLineDao;
    
	public void add(OrderLine entity) throws DataAccessException {
		soSoLineDao.add(entity);
	}

	public void deleteById(Long id) throws DataAccessException {
		soSoLineDao.deleteById(id);
	}
	
	public OrderLine findById(Long id) {
		return soSoLineDao.findById(id);
	}
	
	public void updateIsComment(Long id,String isComment){
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("id", id);
		map.put("isComment", isComment);
		soSoLineDao.updateIsComment(map);
	}
	/***
	 * 根据订单code和是否评论过该商品进行查询
	 */
	public List<OrderLine> findByOrderCode(String orderCode,String isComment) {
		return soSoLineDao.findByOrderCode(orderCode,isComment);
	}
	
	/**
	 * 根据商品code查订单code集合
	 * 
	 */
	public List<String>  findSoOrderCodeBySkuCode(String skuCode){
		return soSoLineDao.findSoOrderCodeBySkuCode(skuCode);
	}
	
	public BasePagination getListPage(BasePagination pageParams) {
		Map<String, Object> map = pageParams.getSearchParamsMap();
		List<OrderLine> orderLineList = new ArrayList<OrderLine>();
		Integer total = soSoLineDao.getTotal(map);
		pageParams.setCurrentTotal(total);
		if (total > 0) {
			orderLineList = soSoLineDao.findList(map);
		}
		pageParams.setResult(orderLineList);
		return pageParams;
	}
	
	public void deleteByOrderCodeAndMemberId(String orderCode,Long memberId){
		soSoLineDao.deleteByOrderCodeAndMemberId(orderCode, memberId); 
	}

}

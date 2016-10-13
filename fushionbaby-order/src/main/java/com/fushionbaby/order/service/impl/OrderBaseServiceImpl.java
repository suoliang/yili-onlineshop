package com.fushionbaby.order.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fushionbaby.common.util.BasePagination;
import com.fushionbaby.common.util.CheckObjectIsNull;
import com.fushionbaby.order.dao.OrderBaseDao;
import com.fushionbaby.order.dao.OrderFinanceDao;
import com.fushionbaby.order.dao.OrderMemberAddressDao;
import com.fushionbaby.order.dao.OrderTransDao;
import com.fushionbaby.order.model.OrderBase;
import com.fushionbaby.order.model.OrderFinance;
import com.fushionbaby.order.model.OrderMemberAddress;
import com.fushionbaby.order.model.OrderTrans;
import com.fushionbaby.order.service.OrderBaseService;

@Service
@Transactional
public class OrderBaseServiceImpl implements OrderBaseService<OrderBase> {
	@Autowired
	private OrderBaseDao orderBaseDao;

	@Autowired
	private OrderFinanceDao orderFinanceDao;

	@Autowired
	private OrderTransDao orderTransDao;

	@Autowired
	private OrderMemberAddressDao orderMemberAddressDao;

	public void update(OrderBase orderBase) {
		orderBaseDao.update(orderBase);
	}

	public OrderBase findByMemberIdAndOrderCode(Long memberId, String orderCode) {
		return orderBaseDao.findByMemberIdAndOrderCode(memberId, orderCode);
	}

	public List<OrderBase> findListByOrderStatus(String orderStatus) {
		return orderBaseDao.findListByOrderStatus(orderStatus);
	}

	public List<OrderBase> findListByStatusAndIsHandlePoint(String orderStatus,
			String isHandlePoint) {
		return orderBaseDao.findListByStatusAndIsHandlePoint(orderStatus,isHandlePoint);
	}
	
	public OrderBase findByOrderCode(String orderCode) {
		return orderBaseDao.findByOrderCode(orderCode);
	}

	public BasePagination findOrderBaseListByPage(BasePagination pageParams) {
		Map<String, Object> map = pageParams.getSearchParamsMap();
		List<OrderBase> orderBaseList = new ArrayList<OrderBase>();

		Integer total = orderBaseDao.getTotal(map);
		pageParams.setCurrentTotal(total);

		if (total > 0) {
			orderBaseList = orderBaseDao.getListPage(map);
			setOrderBaseInfo(orderBaseList);
		}
		pageParams.setResult(orderBaseList);
		return pageParams;
	}

	/***
	 * 设置订单的详细信息
	 * @param orderBaseList
	 */
	private void setOrderBaseInfo(List<OrderBase> orderBaseList) {
		for (OrderBase orderBase : orderBaseList) {
			String orderCode = orderBase.getOrderCode();
			Long memberId = orderBase.getMemberId();
			OrderFinance orderFinance = orderFinanceDao
					.findByMemberIdAndOrderCode(memberId, orderCode);
			OrderTrans orderTrans = orderTransDao
					.findByMemberIdAndOrderCode(memberId, orderCode);
			
			if (CheckObjectIsNull.isNull(orderFinance,orderTrans)) {
				continue;
			}
			Map<String, Object> addressMap = new HashMap<String, Object>();
			addressMap.put("memberId", memberId);
			addressMap.put("orderCode", orderCode);
			OrderMemberAddress orderMemberAddress = orderMemberAddressDao.findByMemberIdAndOrderCode(addressMap);
			if(orderMemberAddress!=null){
				orderBase.setReceiver(orderMemberAddress.getReceiver()==null?"":orderMemberAddress.getReceiver());
				orderBase.setReceiverMobile(orderMemberAddress.getReceiverMobile()==null?"":orderMemberAddress.getReceiverMobile());
			}
			orderBase.setFinanceStatus(orderFinance.getFinanceStatus());
			orderBase.setPaymentType(orderFinance.getPaymentType());
			orderBase.setPaymentTotalActual(orderFinance.getPaymentTotalActual());
			orderBase.setIsInvoice(orderFinance.getIsInvoice());;
			orderBase.setTransStatus(orderTrans.getTransStatus());
			orderBase.setPaymentCompleteTime(orderFinance.getPaymentCompleteTime());
		}
	}

	public List<OrderBase> getListStatisticsUse(HashMap<String, String> map) {
		return this.orderBaseDao.getListStatisticsUse(map);
	}
	
	public List<OrderBase>  findListByOrderStatusAndCurrent(String orderStatus) {
		
		return orderBaseDao.findListByOrderStatusAndCurrent(orderStatus);
	}

	public BasePagination findListPageStatisticsUse(BasePagination pageParams) {
		Map<String, Object> map = pageParams.getSearchParamsMap();
		List<OrderBase> orderBaseList = new ArrayList<OrderBase>();

		Integer total = orderBaseDao.getTotalStatisticsUse(map);
		pageParams.setCurrentTotal(total);

		if (total > 0) {
			orderBaseList = orderBaseDao.findListPageStatisticsUse(map);
			for (OrderBase orderBase : orderBaseList) {
				String orderCode = orderBase.getOrderCode();
				Long memberId = orderBase.getMemberId();
				OrderFinance orderFinance = orderFinanceDao
						.findByMemberIdAndOrderCode(memberId, orderCode);
				orderBase.setPaymentType(orderFinance.getPaymentType());
			}
		}
		pageParams.setResult(orderBaseList);
		return pageParams;
	}

	public void deleteByOrderCodeAndMemberId(String orderCode,Long memberId){
		orderBaseDao.deleteByOrderCodeAndMemberId(orderCode, memberId);
	}

	public BasePagination findUserOrderBaseListByPage(BasePagination page,List<String> orderCodeList) {
		Map<String, Object> map = page.getSearchParamsMap();
		map.put("orderCodeList", orderCodeList);
		List<OrderBase> orderBaseList = new ArrayList<OrderBase>();

		Integer total = orderBaseDao.getUserTotal(map);
		page.setCurrentTotal(total);
		if (total > 0) {
			orderBaseList = orderBaseDao.getUserListPage(map);
			setOrderBaseInfo(orderBaseList);
		}
		page.setResult(orderBaseList);
		return page;
	}

	public void updateOrderDistributionWithdraw(OrderBase orderBase) {
		this.orderBaseDao.updateOrderDistributionWithdraw(orderBase);
	}

	public BasePagination findStoreOrderPageList(BasePagination page) {
		Map<String, Object> map = page.getSearchParamsMap();
		List<OrderBase> orderBaseList = new ArrayList<OrderBase>();
		Integer total = orderBaseDao.getStoreOrderTotal(map);
		page.setCurrentTotal(total);
		if (total > 0) {
			orderBaseList = orderBaseDao.getStoreOrderPageList(map);
			setOrderBaseInfo(orderBaseList);
		}
		page.setResult(orderBaseList);
		return page;
	}
}

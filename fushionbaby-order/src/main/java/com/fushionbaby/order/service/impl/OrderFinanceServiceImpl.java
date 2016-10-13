package com.fushionbaby.order.service.impl;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.fushionbaby.common.util.BasePagination;
import com.fushionbaby.order.dao.OrderFinanceDao;
import com.fushionbaby.order.model.OrderFinance;
import com.fushionbaby.order.service.OrderFinanceService;

/**
 * @author 张明亮
 * 订单收货地址操作
 */
@Service
public class OrderFinanceServiceImpl implements OrderFinanceService<OrderFinance> {
	
    @Autowired
	private OrderFinanceDao orderFinanceDao;

    public void updateByMemberIdAndOrderCode(OrderFinance orderFinance){
    	orderFinanceDao.updateByMemberIdAndOrderCode(orderFinance);
    }

    public OrderFinance findByOrderCode(String orderCode){
    	return orderFinanceDao.findByOrderCode(orderCode);
    }

    public OrderFinance findByOrderFinanceId(Long id){
    	return orderFinanceDao.findByOrderFinanceId(id);
    }

	public List<OrderFinance> findListByFinanceStatus(String transStatus) {
		return orderFinanceDao.findListByFinanceStatus(transStatus);
	}

	public OrderFinance findByMemberIdAndOrderCode(Long memberId,String orderCode) {
		return orderFinanceDao.findByMemberIdAndOrderCode(memberId,orderCode);
	}
	
	public List<OrderFinance> findListByPaymentType(String paymentType){
		return orderFinanceDao.findListByPaymentType(paymentType);
	}
	
	public OrderFinance getOneDayCountByCreateTime(String createTime) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("createTime", createTime);
		return orderFinanceDao.getOneDayCountByCreateTime(map);
	}

	public Integer getBuyOverOneNumberByCreateTime(String createTime) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("createTime", createTime);
		return orderFinanceDao.getBuyOverOneNumberByCreateTime(map);
	}

	public BasePagination findOrderFinanceListPage(BasePagination pageParams) {
		Map<String, Object> map = pageParams.getSearchParamsMap();
		List<OrderFinance> orderFinanceList = new ArrayList<OrderFinance>();
		Integer total = orderFinanceDao.getTotal(map);
		pageParams.setCurrentTotal(total);
		if (total > 0) {
			orderFinanceList = orderFinanceDao.findList(map);
		}
		pageParams.setResult(orderFinanceList);
		return pageParams;
	}
	
	public void deleteByOrderCodeAndMemberId(String orderCode,Long memberId){
		orderFinanceDao.deleteByOrderCodeAndMemberId(orderCode, memberId);
	}

	public List<OrderFinance> findListByFinanceStatusAndLastPayTime(
			String financeStatus, Date paymentCompleteTime) {
		Map<String, Object> map = new HashMap<String, Object>();
		Calendar calendar = Calendar.getInstance(); //得到日历
		calendar.setTime(paymentCompleteTime);//把时间赋给日历
		calendar.add(Calendar.DAY_OF_MONTH, -1);  //设置为前一天
		Date dBefore = calendar.getTime();   //得到前一天的时间
		map.put("paymentCompleteTime", dBefore);
		map.put("financeStatus", financeStatus);
		return orderFinanceDao.findListByFinanceStatusAndLastPayTime(map);
	}
	
}

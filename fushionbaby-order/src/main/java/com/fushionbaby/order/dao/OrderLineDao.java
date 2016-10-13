package com.fushionbaby.order.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.fushionbaby.order.model.OrderFinance;
import com.fushionbaby.order.model.OrderLine;

/**
 * 
 * @author 张明亮
 * t_order_line
 * 销售订单行，行中记录有当时销售的单价和数量情况，如果和某个促销匹配，还会记录对应的促销信息。
 */
public interface OrderLineDao {
	
    void add(OrderLine record);
    
    /**
     * 删除一个订单行
     * @param id
     */
    void deleteById(Long id);
    
    public OrderLine findById(Long id);
    
    public void updateIsComment(Map<String,Object> map);
    /***
     * 根据订单code和评论状态查询订单明细
     * @param orderCode 评论状态可为空
     * @return
     */
	List<OrderLine> findByOrderCode(@Param("orderCode")String orderCode,@Param("isComment")String isComment);
	
	/**
	 * 根据商品code查订单code集合
	 * 
	 */
	public List<String>  findSoOrderCodeBySkuCode(String skuCode);
	
	List<OrderLine> findList(Map<String, Object> map);
    
    Integer getTotal(Map<String, Object> map);
    
    public void deleteByOrderCodeAndMemberId(@Param("orderCode")String orderCode,@Param("memberId")Long memberId);
}
package com.fushionbaby.common.dto.order;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @author 张明亮
 * 订单列表,用于封装订单列表的、订单简要信息
 */
public class OrderDto implements Serializable {
	private static final long serialVersionUID = 1L;
	/**  订单id*/
	private String id;
	/** 订单号*/
	private String code;
	/** 最终金额*/
	private double total_actual;
	/** 交易状态,订单状态*/
	private String order_status;
	/** 订单状态信息显示*/
	private String order_information;
	/** 订单日期+时间,订单创建时间*/
	private String create_time;
	/** 物流单号*/
	private String trans_code;
	/** 物流公司名称*/
	private String trans_name;
	/** 订单审核没通过原因(问题单)*/
	private String auditFailReason;
	private List<OrderLineDto> items = new ArrayList<OrderLineDto>();
	
	public String getAuditFailReason() {
		return auditFailReason;
	}
	public void setAuditFailReason(String auditFailReason) {
		this.auditFailReason = auditFailReason;
	}
	public String getTrans_code() {
		return trans_code;
	}
	public void setTrans_code(String trans_code) {
		this.trans_code = trans_code;
	}
	public String getTrans_name() {
		return trans_name;
	}
	public void setTrans_name(String trans_name) {
		this.trans_name = trans_name;
	}
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public double getTotal_actual() {
		return total_actual;
	}
	public void setTotal_actual(double total_actual) {
		this.total_actual = total_actual;
	}
	public String getOrder_status() {
		return order_status;
	}
	public void setOrder_status(String order_status) {
		this.order_status = order_status;
	}
	public String getOrder_information() {
		return order_information;
	}
	public void setOrder_information(String order_information) {
		this.order_information = order_information;
	}
	public String getCreate_time() {
		return create_time;
	}
	public void setCreate_time(String create_time) {
		this.create_time = create_time;
	}
	public List<OrderLineDto> getItems() {
		return items;
	}
	public void setItems(List<OrderLineDto> items) {
		this.items = items;
	}

	
}

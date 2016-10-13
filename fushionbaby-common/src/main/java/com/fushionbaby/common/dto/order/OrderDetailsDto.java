/**
 * 
 */
package com.fushionbaby.common.dto.order;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;


/**
 * @author mengshaobo
 *
 */
public class OrderDetailsDto implements Serializable{
	private static final long serialVersionUID = 1L;
	/**  订单id*/
	private Long id;
	/** 订单编号*/
	private String code;
	/** 交易状态,订单状态*/
	private String order_status;
	/** 下单时间带时分秒,订单创建时间*/
	private String create_time;
	
	/** 物流单号*/
	private String trans_code;
	/** 物流公司名称*/
	private String trans_name;
	
	/** 收货人*/
	private String receiver;
	/** 手机号*/
	private String receiver_mobile;
	/** 省*/
	private String province;
	/** 市*/
	private String city;
	/** 区*/
	private String district;
	/** 收货地址*/
	private String address;
	
	/** 发票抬头*/
	private String invoice_title;
	/** 发票类型*/
	private String invoice_type;
	/** 折后总价*/
	private BigDecimal total_af_discount;
	/** 实付运费*/
	private BigDecimal actual_transfer_fee;
	/** 税费*/
	private BigDecimal tax_price;
	/** 最终金额*/
	private BigDecimal total_actual;
	/**订单没通过审核原因*/
	private String audit_fail_reason;
	/** 订单状态信息显示*/
	private String order_information;
	/**订单行信息List<OrderLineDto>*/
	List<OrderLineDto> items = new ArrayList<OrderLineDto>();


	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
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

	public String getReceiver() {
		return receiver;
	}

	public void setReceiver(String receiver) {
		this.receiver = receiver;
	}

	public String getReceiver_mobile() {
		return receiver_mobile;
	}

	public void setReceiver_mobile(String receiver_mobile) {
		this.receiver_mobile = receiver_mobile;
	}

	public String getProvince() {
		return province;
	}

	public void setProvince(String province) {
		this.province = province;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getDistrict() {
		return district;
	}

	public void setDistrict(String district) {
		this.district = district;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getInvoice_title() {
		return invoice_title;
	}

	public void setInvoice_title(String invoice_title) {
		this.invoice_title = invoice_title;
	}

	public String getInvoice_type() {
		return invoice_type;
	}

	public void setInvoice_type(String invoice_type) {
		this.invoice_type = invoice_type;
	}

	public BigDecimal getTotal_af_discount() {
		return total_af_discount;
	}

	public void setTotal_af_discount(BigDecimal total_af_discount) {
		this.total_af_discount = total_af_discount;
	}

	public BigDecimal getActual_transfer_fee() {
		return actual_transfer_fee;
	}

	public void setActual_transfer_fee(BigDecimal actual_transfer_fee) {
		this.actual_transfer_fee = actual_transfer_fee;
	}

	public BigDecimal getTotal_actual() {
		return total_actual;
	}

	public void setTotal_actual(BigDecimal total_actual) {
		this.total_actual = total_actual;
	}

	public List<OrderLineDto> getItems() {
		return items;
	}

	public void setItems(List<OrderLineDto> items) {
		this.items = items;
	}

	public BigDecimal getTax_price() {
		return tax_price;
	}

	public void setTax_price(BigDecimal tax_price) {
		this.tax_price = tax_price;
	}

	public String getAudit_fail_reason() {
		return audit_fail_reason;
	}

	public void setAudit_fail_reason(String audit_fail_reason) {
		this.audit_fail_reason = audit_fail_reason;
	}
	
	
}

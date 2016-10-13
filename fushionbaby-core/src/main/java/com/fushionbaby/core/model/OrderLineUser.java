package com.fushionbaby.core.model;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @author 张明亮 t_order_line 销售订单行，行中记录有当时销售的单价和数量情况，如果和某个促销匹配，还会记录对应的促销信息。
 */
public class OrderLineUser {
	private Long id;
	/** 订单code */
	private String orderCode;
	/** 商城会员id */
	private Long memberId;
	/** 商品编码 */
	private String skuCode;
	/** 商品名称 */
	private String skuName;
	/** 行类型（n主卖行/y赠品行） */
	private String lineType;
	/** 尺码 */
	private String size;
	/** 颜色 */
	private String color;
	/** 商品吊牌价 一般来说 = sku retailPrice */
	private BigDecimal skuRetailPrice;
	/** 销售单价（用户最终享受的销售单价） */
	private BigDecimal unitPrice;
	/** 订购数量 */
	private Integer quantity;
	/** 商品成本 */
	private BigDecimal costPrice;
	/** 实际行小计总金额（如无单行金额折让则=总金额，否则=总金额-金额折让） */
	private BigDecimal totalPrice;
	/** 总金额（=销售单价*数量） */
	private BigDecimal totalActual;
	/** 是否已经评论过n未评论y已评论 */
	private String isComment;
	/** 备注 */
	private String remark;

	private Date createTime;

	private Date updateTime;

	private Date version;
	
	/** 门店系统code，系统生成，不可修改 */
    private String storeCode;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getLineType() {
		return lineType;
	}

	public void setLineType(String lineType) {
		this.lineType = lineType;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark == null ? null : remark.trim();
	}

	public String getSize() {
		return size;
	}

	public void setSize(String size) {
		this.size = size == null ? null : size.trim();
	}

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color == null ? null : color.trim();
	}

	public String getSkuCode() {
		return skuCode;
	}

	public void setSkuCode(String skuCode) {
		this.skuCode = skuCode == null ? null : skuCode.trim();
	}

	public BigDecimal getCostPrice() {
		return costPrice;
	}

	public void setCostPrice(BigDecimal costPrice) {
		this.costPrice = costPrice;
	}

	public String getSkuName() {
		return skuName;
	}

	public void setSkuName(String skuName) {
		this.skuName = skuName == null ? null : skuName.trim();
	}

	public BigDecimal getTotalActual() {
		return totalActual;
	}

	public void setTotalActual(BigDecimal totalActual) {
		this.totalActual = totalActual;
	}

	public BigDecimal getTotalPrice() {
		return totalPrice;
	}

	public void setTotalPrice(BigDecimal totalPrice) {
		this.totalPrice = totalPrice;
	}

	public BigDecimal getSkuRetailPrice() {
		return skuRetailPrice;
	}

	public void setSkuRetailPrice(BigDecimal skuRetailPrice) {
		this.skuRetailPrice = skuRetailPrice;
	}

	public BigDecimal getUnitPrice() {
		return unitPrice;
	}

	public void setUnitPrice(BigDecimal unitPrice) {
		this.unitPrice = unitPrice;
	}

	public Date getVersion() {
		return version;
	}

	public void setVersion(Date version) {
		this.version = version;
	}

	public String getIsComment() {
		return isComment;
	}

	public void setIsComment(String isComment) {
		this.isComment = isComment;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public Integer getQuantity() {
		return quantity;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}

	public String getOrderCode() {
		return orderCode;
	}

	public void setOrderCode(String orderCode) {
		this.orderCode = orderCode;
	}

	public Long getMemberId() {
		return memberId;
	}

	public void setMemberId(Long memberId) {
		this.memberId = memberId;
	}

	public String getStoreCode() {
		return storeCode;
	}

	public void setStoreCode(String storeCode) {
		this.storeCode = storeCode;
	}

}
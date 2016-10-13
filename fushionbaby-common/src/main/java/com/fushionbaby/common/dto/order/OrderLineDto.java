package com.fushionbaby.common.dto.order;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @author 张明亮 订单行信息order Line
 */
public class OrderLineDto implements Serializable {
	private static final long serialVersionUID = 1L;
	/** 订单行id */
	private Long id;
	/** 商品号 */
	private String skuCode;
	/** 商品名称 */
	private String skuName;
	/** 商品图片 */
	private String imgPath;
	/** 数量,订购数量 */
	private Integer quantity;
	/** 单价,销售单价 */
	private BigDecimal unitPrice;
	/** 商品是否被评价n没有被评价,y被评价过了可以追加评论 */
	private String isComment;
	/** 商品尺寸 */
	private String skuSize;
	/** 商品颜色 */
	private String skuColor;
	/** 当前购买行价格总计,优惠前 */
	private BigDecimal rowsPriceTotal;
	/** 评论数 */
	private Integer commentCount;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getSkuCode() {
		return skuCode;
	}

	public void setSkuCode(String skuCode) {
		this.skuCode = skuCode;
	}

	public String getSkuName() {
		return skuName;
	}

	public void setSkuName(String skuName) {
		this.skuName = skuName;
	}

	public String getImgPath() {
		return imgPath;
	}

	public void setImgPath(String imgPath) {
		this.imgPath = imgPath;
	}

	public Integer getQuantity() {
		return quantity;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}

	public BigDecimal getUnitPrice() {
		return unitPrice;
	}

	public void setUnitPrice(BigDecimal unitPrice) {
		this.unitPrice = unitPrice;
	}

	public String getIsComment() {
		return isComment;
	}

	public void setIsComment(String isComment) {
		this.isComment = isComment;
	}

	public String getSkuSize() {
		return skuSize;
	}

	public void setSkuSize(String skuSize) {
		this.skuSize = skuSize;
	}

	public String getSkuColor() {
		return skuColor;
	}

	public void setSkuColor(String skuColor) {
		this.skuColor = skuColor;
	}

	public BigDecimal getRowsPriceTotal() {
		return rowsPriceTotal;
	}

	public void setRowsPriceTotal(BigDecimal rowsPriceTotal) {
		this.rowsPriceTotal = rowsPriceTotal;
	}

	public Integer getCommentCount() {
		return commentCount;
	}

	public void setCommentCount(Integer commentCount) {
		this.commentCount = commentCount;
	}


	
}

package com.fushionbaby.wap.dto;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @author 张明亮
 * 订单行信息order Line
 */
public class OrderLineDto implements Serializable {
	private static final long serialVersionUID = 1L;
	private Long id;//订单行id
	private Long sku_id;//商品id
	private String sku_name;//商品名称
	private String img_path;//商品图片
	private Integer quantity;//数量,订购数量
	private BigDecimal unit_price;//单价,销售单价
	private String is_comment;//商品是否被评价n没有被评价,y被评价过了可以追加评论
	private String sku_size;//商品尺寸
	private String sku_color;//商品颜色
	private BigDecimal rowsPriceTotal;//当前购买行价格总计,优惠前
	
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getSku_id() {
		return sku_id;
	}
	public void setSku_id(Long sku_id) {
		this.sku_id = sku_id;
	}
	public String getSku_name() {
		return sku_name;
	}
	public void setSku_name(String sku_name) {
		this.sku_name = sku_name;
	}
	public String getImg_path() {
		return img_path;
	}
	public void setImg_path(String img_path) {
		this.img_path = img_path;
	}
	public BigDecimal getUnit_price() {
		return unit_price;
	}
	public void setUnit_price(BigDecimal unit_price) {
		this.unit_price = unit_price;
	}
	public String getIs_comment() {
		return is_comment;
	}
	public void setIs_comment(String is_comment) {
		this.is_comment = is_comment;
	}
	public String getSku_size() {
		return sku_size;
	}
	public void setSku_size(String sku_size) {
		this.sku_size = sku_size;
	}
	public String getSku_color() {
		return sku_color;
	}
	public void setSku_color(String sku_color) {
		this.sku_color = sku_color;
	}
	public BigDecimal getRowsPriceTotal() {
		return rowsPriceTotal;
	}
	public void setRowsPriceTotal(BigDecimal rowsPriceTotal) {
		this.rowsPriceTotal = rowsPriceTotal;
	}
	public Integer getQuantity() {
		return quantity;
	}
	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}
	@Override
	public String toString() {
		return "OrderLineDto [id=" + id + ", sku_id=" + sku_id + ", sku_name="
				+ sku_name + ", img_path=" + img_path + ", quantity="
				+ quantity + ", unit_price=" + unit_price + ", is_comment="
				+ is_comment + ", sku_size=" + sku_size + ", sku_color="
				+ sku_color + ", rowsPriceTotal=" + rowsPriceTotal + "]";
	}
}

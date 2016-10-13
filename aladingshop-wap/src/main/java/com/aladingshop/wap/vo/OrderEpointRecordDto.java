package com.aladingshop.wap.vo;

/***
 * @description 积分商城订单积分记录DTO
 * @author 索亮
 * @date 2016年3月1日下午5:32:13
 */
public class OrderEpointRecordDto {
	/** 兑换时间 */
	private String exChangeTime;
	/** 使用积分 */
	private String usePoint;
	/** 图片路径 */
	private String imgPath;
	/** 商品名称 */
	private String skuName;
	/** 兑换数量 */
	private String quantity;

	public String getExChangeTime() {
		return exChangeTime;
	}

	public void setExChangeTime(String exChangeTime) {
		this.exChangeTime = exChangeTime;
	}

	public String getUsePoint() {
		return usePoint;
	}

	public void setUsePoint(String usePoint) {
		this.usePoint = usePoint;
	}

	public String getImgPath() {
		return imgPath;
	}

	public void setImgPath(String imgPath) {
		this.imgPath = imgPath;
	}

	public String getSkuName() {
		return skuName;
	}

	public void setSkuName(String skuName) {
		this.skuName = skuName;
	}

	public String getQuantity() {
		return quantity;
	}

	public void setQuantity(String quantity) {
		this.quantity = quantity;
	}
	
}

package com.fushionbaby.config.model;

import java.util.Date;

/***
 * @description 如意消费卡余额和折扣的重要配置
 * @author 索亮
 * @date 2016年2月26日下午3:34:11
 */
public class SysmgrAlabaoConfig {
	/** 自增id */
	private Long id;
	/** 如意消费卡余额与折扣挂钩的名称 */
	private String name;
	/** code标识 */
	private String code;
	/** 如意消费卡账户余额最小值 */
	private String minValue;
	/** 如意消费卡账户余额最大值 */
	private String maxValue;
	/** 折扣值,打折 */
	private String discountValue;
	/** 创建时间 */
	private Date createTime;
	/** 更新时间 */
	private Date updateTime;
	/** 描述 */
	private String remark;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getMinValue() {
		return minValue;
	}

	public void setMinValue(String minValue) {
		this.minValue = minValue;
	}

	public String getMaxValue() {
		return maxValue;
	}

	public void setMaxValue(String maxValue) {
		this.maxValue = maxValue;
	}

	public String getDiscountValue() {
		return discountValue;
	}

	public void setDiscountValue(String discountValue) {
		this.discountValue = discountValue;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}
	
}

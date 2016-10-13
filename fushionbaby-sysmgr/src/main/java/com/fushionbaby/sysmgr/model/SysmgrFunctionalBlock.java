/**
 * @description 类描述...
 * @author 孟少博
 * @date 2015年9月29日下午2:01:27
 */
package com.fushionbaby.sysmgr.model;

import java.util.Date;

/**
 * @description  系统配置功能栏
 * @author 孟少博
 * @date 2015年9月29日下午2:01:27
 */
public class SysmgrFunctionalBlock {
	
	private Long id;
	
	/** 使用类型（1.商品和门店2.商城3.门店）*/
	private Integer useType;
	
	/** 功能栏编号*/
	private String code;
	
	/** 名称*/
	private String name;
	
	/** 图标*/
	private String icon;
	
	/** 链接地址*/
	private String linkUrl;
	
	/** 显示顺序*/
	private Integer sortOrder;
	
	/** 开始时间*/
	private Date startTime;
	
	/** 结束时间*/
	private Date endTime;
	
	/** 是否显示*/
	private String disable;
	
	/** 创建时间*/
	private Date createTime;
	
	/** 修改时间*/
	private Date updateTime;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	
	public Integer getUseType() {
		return useType;
	}

	public void setUseType(Integer useType) {
		this.useType = useType;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	public String getLinkUrl() {
		return linkUrl;
	}

	public void setLinkUrl(String linkUrl) {
		this.linkUrl = linkUrl;
	}

	public Integer getSortOrder() {
		return sortOrder;
	}

	public void setSortOrder(Integer sortOrder) {
		this.sortOrder = sortOrder;
	}

	public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	public String getDisable() {
		return disable;
	}

	public void setDisable(String disable) {
		this.disable = disable;
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
	
	
	
}

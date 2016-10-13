/**
 * @description aladingshop.com 上海一里网络科技有限公司
 * @author 孟少博
 * @date 2015年11月3日下午2:27:12
 */
package com.fushionbaby.sku.model;

import java.util.Date;

/**
 * @description 标签图片
 * @author 孟少博
 * @date 2015年11月3日下午2:27:12
 */
public class SkuLabelImage {
	
	/** ID*/
	private Long id;
	
	/** 标签编号*/
	private String labelCode;
	
	/** 商品图片类型名称*/
	private String imageTypeName;
	
	/** 商品图片类型编号*/
	private String imageTypeCode;
	
	/** 图片地址*/
	private String imgUrl;
	
	/** 链接地址*/
	private String linkUrl;
	
	/** 显示顺序*/
	private Integer sortOrder;
	
	/** 创建时间*/
	private Date createTime;
	
	/** 修改时间*/
	private Date updateTime;
	

	public String getLinkUrl() {
		return linkUrl;
	}

	public void setLinkUrl(String linkUrl) {
		this.linkUrl = linkUrl;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getLabelCode() {
		return labelCode;
	}

	public void setLabelCode(String labelCode) {
		this.labelCode = labelCode;
	}

	public String getImageTypeName() {
		return imageTypeName;
	}

	public void setImageTypeName(String imageTypeName) {
		this.imageTypeName = imageTypeName;
	}

	public String getImageTypeCode() {
		return imageTypeCode;
	}

	public void setImageTypeCode(String imageTypeCode) {
		this.imageTypeCode = imageTypeCode;
	}

	public String getImgUrl() {
		return imgUrl;
	}

	public void setImgUrl(String imgUrl) {
		this.imgUrl = imgUrl;
	}

	public Integer getSortOrder() {
		return sortOrder;
	}

	public void setSortOrder(Integer sortOrder) {
		this.sortOrder = sortOrder;
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

/**
 * 
 */
package com.aladingshop.sku.cms.model;

import java.util.Date;

/**
 * @description 商品关键字信息
 * @author 孙涛
 * @date 2015年9月6日上午10:25:48
 */
public class SkuKeyWord {
	private Long id;
	private String productCode;
	private String keyWords;
	private Long createId;
	private Date createTime;
	private Long updateId;
	private Date updateTime;

	public SkuKeyWord() {
	}

	public SkuKeyWord(String productCode, String keyWords) {
		this.productCode = productCode;
		this.keyWords = keyWords;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getProductCode() {
		return productCode;
	}

	public void setProductCode(String productCode) {
		this.productCode = productCode;
	}

	public String getKeyWords() {
		return keyWords;
	}

	public void setKeyWords(String keyWords) {
		this.keyWords = keyWords;
	}

	public Long getCreateId() {
		return createId;
	}

	public void setCreateId(Long createId) {
		this.createId = createId;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Long getUpdateId() {
		return updateId;
	}

	public void setUpdateId(Long updateId) {
		this.updateId = updateId;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

}

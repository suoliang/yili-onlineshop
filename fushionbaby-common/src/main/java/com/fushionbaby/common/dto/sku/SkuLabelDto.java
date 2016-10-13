/**
 * 
 */
package com.fushionbaby.common.dto.sku;

/**
 * @author mengshaobo 商品标签
 */
public class SkuLabelDto {
	private Long id;
	/** 标签名 */
	private String name;
	/** 便签编码 */
	private String code;
	/** 类型*/
	private String type;
	/** 是否禁用 */
	private String disabled;
	/**备注*/
	private String memo;

	
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

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

	public String getDisabled() {
		return disabled;
	}

	public void setDisabled(String disabled) {
		this.disabled = disabled;
	}

	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

}

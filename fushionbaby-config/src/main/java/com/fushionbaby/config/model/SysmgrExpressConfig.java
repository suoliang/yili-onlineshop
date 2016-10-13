package com.fushionbaby.config.model;
/***
 * 快递物流信息配置
 * @description 类描述...
 * @author 徐培峻
 * @date 2015年11月3日上午9:49:44
 */
public class SysmgrExpressConfig {
	/** 自增id*/
	private Long id;
    /** 快递公司名称*/
 	private String expressCompanyName;
    /** 快递公司对应的编码*/
	private String expressCompanyCode;
    /** 备注信息*/
	private String memo;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getExpressCompanyName() {
		return expressCompanyName;
	}

	public void setExpressCompanyName(String expressCompanyName) {
		this.expressCompanyName = expressCompanyName;
	}

	public String getExpressCompanyCode() {
		return expressCompanyCode;
	}

	public void setExpressCompanyCode(String expressCompanyCode) {
		this.expressCompanyCode = expressCompanyCode;
	}

	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

	
}
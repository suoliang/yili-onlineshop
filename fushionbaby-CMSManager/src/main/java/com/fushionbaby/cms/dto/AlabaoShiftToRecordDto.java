package com.fushionbaby.cms.dto;

/**
 * 
 * @author 陈应涛
 *
 */

public class AlabaoShiftToRecordDto {
	
	/** 根据账户名称查询 **/
	private String account;
	/** 根据操作流水号查询 **/
	private String batchNum;
	/** 查询时间起点**/
	private String createTimeFrom;
	/** 查询时间截止点 **/
	private String createTimeTo;
	
	public String getAccount() {
		return account;
	}
	public void setAccount(String account) {
		this.account = account;
	}
	public String getBatchNum() {
		return batchNum;
	}
	public void setBatchNum(String batchNum) {
		this.batchNum = batchNum;
	}
	public String getCreateTimeFrom() {
		return createTimeFrom;
	}
	public void setCreateTimeFrom(String createTimeFrom) {
		this.createTimeFrom = createTimeFrom;
	}
	public String getCreateTimeTo() {
		return createTimeTo;
	}
	public void setCreateTimeTo(String createTimeTo) {
		this.createTimeTo = createTimeTo;
	}
	
	
}

package com.fushionbaby.cms.dto;
/***
 * 后台查询的条件  益多宝
 * @description 类描述...
 * @author 徐培峻
 * @date 2015年10月9日上午9:17:53
 */

public class YiDuoBaoOrderDto {
	/**订单号*/
	private String orderCode;
    /**会员名称*/
	private String memberName;
	/**创建时间开始*/
	private String createTimeFrom;
	/** 创建时间截点*/
	private String createTimeTo;
	/** 如意宝账号*/
    private String acount;	
    /** 金融状态，y付过款 ，n 未付款*/
    private String financeStatus;
    
	public String getAcount() {
		return acount;
	}
	public void setAcount(String acount) {
		this.acount = acount;
	}
	public String getOrderCode() {
		return orderCode;
	}
	public void setOrderCode(String orderCode) {
		this.orderCode = orderCode;
	}
	public String getMemberName() {
		return memberName;
	}
	public void setMemberName(String memberName) {
		this.memberName = memberName;
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
	public String getFinanceStatus() {
		return financeStatus;
	}
	public void setFinanceStatus(String financeStatus) {
		this.financeStatus = financeStatus;
	}
	 
	
}

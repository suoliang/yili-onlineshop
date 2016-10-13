/**
 * 
 */
package com.aladingshop.card.dto.res;

/**
 * @description 益多宝卡列表结果dto
 * @author 孙涛
 * @date 2015年9月21日下午3:17:49
 */
public class YiDuoBaoCardResDto {
	/** 储值卡的id */
	private String id;
	/** 储值卡的卡号 */
	private String cardNo;
	/** 本金 */
	private String totalCorpus;
	/** 总的赠券金额 */
	private String totalRebate;
	/** 1、期限内（正常返券时间内） 2、已退卡 3、已删除 4、期限外（转入如意宝） */
	private String status;
	/** 页面显示时间 */
	private String showTime;
	/** 益多宝卡类型名 页面显示 */
	private String cardName;
	
	
	/**每月赠券金额*/
	private String perRebate;
	/**说明信息 还有多久到期等*/
	private String memo;
	/**显示到期时间   app端着重显示*/
	private String days;

	
	
	
	
	
	public String getDays() {
		return days;
	}

	public void setDays(String days) {
		this.days = days;
	}

	public String getPerRebate() {
		return perRebate;
	}

	public void setPerRebate(String perRebate) {
		this.perRebate = perRebate;
	}

	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}



	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getCardNo() {
		return cardNo;
	}

	public void setCardNo(String cardNo) {
		this.cardNo = cardNo;
	}

	public String getTotalCorpus() {
		return totalCorpus;
	}

	public void setTotalCorpus(String totalCorpus) {
		this.totalCorpus = totalCorpus;
	}

	public String getTotalRebate() {
		return totalRebate;
	}

	public void setTotalRebate(String totalRebate) {
		this.totalRebate = totalRebate;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getShowTime() {
		return showTime;
	}

	public void setShowTime(String showTime) {
		this.showTime = showTime;
	}

	public String getCardName() {
		return cardName;
	}

	public void setCardName(String cardName) {
		this.cardName = cardName;
	}

}

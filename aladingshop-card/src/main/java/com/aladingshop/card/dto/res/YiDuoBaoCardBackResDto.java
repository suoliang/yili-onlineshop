/**
 * 
 */
package com.aladingshop.card.dto.res;

/**
 * @description 退卡返回结果dto
 * @author 孙涛
 * @date 2015年10月12日上午10:19:07
 */
public class YiDuoBaoCardBackResDto {
	/** 应退总金额 */
	private String totalMoney;
	/** 扣除手续费 */
	private String reduceMoney;
	/** 提示话述 */
	private String remark;
	/**卡的类型名 */
	private String typeName;
	/**用户的要退卡的手机号*/
	private String phone;
	
	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getTypeName() {
		return typeName;
	}

	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}

	public String getTotalMoney() {
		return totalMoney;
	}

	public void setTotalMoney(String totalMoney) {
		this.totalMoney = totalMoney;
	}

	public String getReduceMoney() {
		return reduceMoney;
	}

	public void setReduceMoney(String reduceMoney) {
		this.reduceMoney = reduceMoney;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

}

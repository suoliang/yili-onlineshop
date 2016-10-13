package com.fushionbaby.sms.dto;

/***
 * 美联软通短信服务   返回参数
 * @description 类描述...
 * @author 徐培峻
 * @date 2015年11月24日下午4:10:23
 */
public class Sms5cResDto {
	/**响应状态  error  success*/
	private  String  status; 
	/**消息描述*/
	private  String  msg;
	/** 余额  可发短信的剩余次数*/
	private String balanceNum;
    /** 总的短信次数*/
	private String totalNum;
	
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}

	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	public String getBalanceNum() {
		return balanceNum;
	}
	public void setBalanceNum(String balanceNum) {
		this.balanceNum = balanceNum;
	}
	public String getTotalNum() {
		return totalNum;
	}
	public void setTotalNum(String totalNum) {
		this.totalNum = totalNum;
	} 

	

}

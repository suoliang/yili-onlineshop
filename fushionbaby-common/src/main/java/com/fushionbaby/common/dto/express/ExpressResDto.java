package com.fushionbaby.common.dto.express;

import java.util.List;

/**
 * 快递100响应结果Dto
 * 
 * @author suntao
 *
 */
public class ExpressResDto {
	public ExpressResDto() {
	}

	public ExpressResDto(String state) {
		this.state = state;
	}

	/** 返回结果 ok表示调用成功 */
	private String message;
	/**
	 * 快递单当前的状态 ：　
	 * 0：在途，即货物处于运输过程中；1：揽件，货物已由快递公司揽收并且产生了第一条跟踪信息；2：疑难，货物寄送过程出了问题；3：
	 * 签收，收件人已签收；4：
	 * 退签，即货物由于用户拒签、超区等原因退回，而且发件人已经签收；5：派件，即快递正在进行同城派件；6：退回，货物正处于退回发件人的途中；
	 */
	private String state;
	/** 查询结果状态：200：查询成功， 其他：接口出现异常， */
	private String status;
	/** 物流公司编号 */
	private String com;
	/** 物流单号 */
	private String nu;
	/** 返回跟踪记录的数组 */
	private List<ExpressResArrayDto> data;

	/**快递公司的汉语名字   自定义使用*/
	private String name;
	
	public String getCom() {
		return com;
	}

	public void setCom(String com) {
		this.com = com;
	}

	public String getNu() {
		return nu;
	}

	public void setNu(String nu) {
		this.nu = nu;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public List<ExpressResArrayDto> getData() {
		return data;
	}

	public void setData(List<ExpressResArrayDto> data) {
		this.data = data;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	
}

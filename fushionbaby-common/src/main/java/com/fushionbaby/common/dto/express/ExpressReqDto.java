package com.fushionbaby.common.dto.express;

/**
 * 快递100查询请求Dto
 * 
 * @author suntao
 *         http://api.kuaidi100.com/api?id=[]&com=[]&nu=[]&valicode=[]&show
 *         =[0|1|2|3]&muti=[0|1]&order=[desc|asc] 当前model字段全为必填字段，不可为空
 */
public class ExpressReqDto {
	/** 要查询的快递公司代码，不支持中文，对应的公司代码见 */
	private String com;
	/** 要查询的快递单号，请勿带特殊符号，不支持中文（大小写不敏感） */
	private String nu;

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
}

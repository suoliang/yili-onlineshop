package com.fushionbaby.common.dto.app;

/***
 * app 和   IOS  请求过来的参数封装 
 * @author xupeijun
 *
 */
public class OtherLoginBackInfoDto {

	/** 用户json数据*/
	private String userjson;
	
	/** 登陆用户标志*/
	private String visitKey;
	/** 门店用户登录标识符*/
	private String storeVisitKey;
	
	/** 请求方式来源 (ios android)*/
	private String source;

	
	/** 来源渠道（qq,sina,weixin）*/
	private String sourceChanel;
	
	
	
	
	
	public String getStoreVisitKey() {
		return storeVisitKey;
	}

	public void setStoreVisitKey(String storeVisitKey) {
		this.storeVisitKey = storeVisitKey;
	}

	public String getSourceChanel() {
		return sourceChanel;
	}

	public void setSourceChanel(String sourceChanel) {
		this.sourceChanel = sourceChanel;
	}

	public String getUserjson() {
		return userjson;
	}

	public void setUserjson(String userjson) {
		this.userjson = userjson;
	}

	public String getVisitKey() {
		return visitKey;
	}

	public void setVisitKey(String visitKey) {
		this.visitKey = visitKey;
	}

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	
}

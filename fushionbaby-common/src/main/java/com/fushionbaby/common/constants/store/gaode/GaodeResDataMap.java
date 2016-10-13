package com.fushionbaby.common.constants.store.gaode;

import java.util.List;

/***
 * 高德响应数据  json 数据
 * @description 类描述...
 * @author 徐培峻
 * @date 2016年2月29日下午4:37:21
 */
public class GaodeResDataMap {

	/**数量*/
	private String count;
	/**信息说明*/
	private String info;
	/**状态*/
	private String status;
	/**返回数据*/
	private List<GaodeResDataModel> datas;
	
	public String getCount() {
		return count;
	}
	public void setCount(String count) {
		this.count = count;
	}
	public String getInfo() {
		return info;
	}
	public void setInfo(String info) {
		this.info = info;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public List<GaodeResDataModel> getDatas() {
		return datas;
	}
	public void setDatas(List<GaodeResDataModel> datas) {
		this.datas = datas;
	}

	
	
}

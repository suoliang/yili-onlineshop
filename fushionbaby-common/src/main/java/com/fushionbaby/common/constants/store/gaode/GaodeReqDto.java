package com.fushionbaby.common.constants.store.gaode;

/***
 * app 请求定位 高德地图传入的数据
 * @description 类描述...
 * @author 徐培峻
 * @date 2016年2月29日下午4:36:20
 */
public class GaodeReqDto {
	/**范围半径*/
	private String R;
	/**经度*/
	private String X;
	/**纬度*/
	private String Y;
	
	public String getR() {
		return R;
	}
	public void setR(String r) {
		R = r;
	}
	public String getX() {
		return X;
	}
	public void setX(String x) {
		X = x;
	}
	public String getY() {
		return Y;
	}
	public void setY(String y) {
		Y = y;
	}
	
	
}

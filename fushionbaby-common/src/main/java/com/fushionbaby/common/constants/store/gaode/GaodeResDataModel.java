package com.fushionbaby.common.constants.store.gaode;

/***
 * 请求 高德地图返回的数据 model
 * @description 类描述...
 * @author 徐培峻
 * @date 2016年2月29日下午4:36:20
 */
public class GaodeResDataModel {
	/**数据id*/
	private String _id;
	/**位置信息  经度纬度  x,y */
	private String _location;
	/**门店名称*/
	private String _name;
	/**地址 到门牌号*/
	private String _address;
	/**自定义的  门店code*/
	private String code;
	/**联系电话*/
	private String telephone;
	/**创建时间*/
	private String _createtime;
	/**更新时间*/
	private String _updatetime;
	/**省份*/
	private String _province;
	/**城市*/
	private String _city;
	/**街道*/
	private String _district;
	/**距离*/
	private String _distance;
	/**图片集合*/
	private String[] _image;
	
	public String get_id() {
		return _id;
	}
	public void set_id(String _id) {
		this._id = _id;
	}
	public String get_location() {
		return _location;
	}
	public void set_location(String _location) {
		this._location = _location;
	}
	public String get_name() {
		return _name;
	}
	public void set_name(String _name) {
		this._name = _name;
	}
	public String get_address() {
		return _address;
	}
	public void set_address(String _address) {
		this._address = _address;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getTelephone() {
		return telephone;
	}
	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}
	public String get_createtime() {
		return _createtime;
	}
	public void set_createtime(String _createtime) {
		this._createtime = _createtime;
	}
	public String get_updatetime() {
		return _updatetime;
	}
	public void set_updatetime(String _updatetime) {
		this._updatetime = _updatetime;
	}
	public String get_province() {
		return _province;
	}
	public void set_province(String _province) {
		this._province = _province;
	}
	public String get_city() {
		return _city;
	}
	public void set_city(String _city) {
		this._city = _city;
	}
	public String get_district() {
		return _district;
	}
	public void set_district(String _district) {
		this._district = _district;
	}
	public String get_distance() {
		return _distance;
	}
	public void set_distance(String _distance) {
		this._distance = _distance;
	}
	public String[] get_image() {
		return _image;
	}
	public void set_image(String[] _image) {
		this._image = _image;
	}
	
	
}

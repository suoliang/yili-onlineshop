package com.fushionbaby.cms.dto;

/***
 * CMS中将权限列表信息转换成json格式使用，ztree角色选择权限数据
 * @description 类描述...
 * @author 徐培峻
 * @date 2015年10月14日下午1:19:41
 */
public class AuthPrivilegeDto {
	/** ID值 */
	private String id;
	/** 父级ID */
	private String pId;
	/** 权限名称 */
	private String name;
	/** 值  */
	private String code;
	/** 设置是否选中*/
	private boolean checked;
	
	public boolean isChecked() {
		return checked;
	}
	public void setChecked(boolean checked) {
		this.checked = checked;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getpId() {
		return pId;
	}
	public void setpId(String pId) {
		this.pId = pId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}

}

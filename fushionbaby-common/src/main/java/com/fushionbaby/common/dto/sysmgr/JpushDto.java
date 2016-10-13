package com.fushionbaby.common.dto.sysmgr;

import java.util.List;
import java.util.Set;

import com.fushionbaby.common.util.StringTools;

/***
 * 极光推送内容的封装类
 * @author xupeijun
 *
 */
public class JpushDto {
	/** 推送的标题*/
	private String title;
	/** 推送的内容*/
	private String content;
	/** 推送的url链接*/
	private String url;
	private String tag;
	/** 推送的别名 alias*/
	private String alias;
   /**消息类型，是通知信息还是推送消息*/
	
	private String type;
	/** 设备  安卓，IOS，全部*/
	private String device;
	
	/** 返回到手机端，访问类型 0代表首页，1代表具体商品，2代表商品分类 ，3代表相关活动*/
	private String accessType;
	/** 返回给手机端的数据*/
	private String data;
	
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getTag() {
		return tag;
	}
	public void setTag(String tag) {
		this.tag = tag;
	}
	public String getAlias() {
		return alias;
	}
	public void setAlias(String alias) {
		this.alias = alias;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getDevice() {
		return device;
	}
	public void setDevice(String device) {
		this.device = device;
	}
	public String getAccessType() {
		return accessType;
	}
	public void setAccessType(String accessType) {
		this.accessType = accessType;
	}
	public String getData() {
		return data;
	}
	public void setData(String data) {
		this.data = data;
	}
	@SuppressWarnings("unchecked")
	public Set<String> getTagList() {
	    List<String> tagList=StringTools.splitStringToList(tag, ",");
		return (Set<String>) tagList;
	}
	@SuppressWarnings("unchecked")
	public Set<String> getAliiasList() {
		 List<String> aliasList=StringTools.splitStringToList(tag, ",");
		return (Set<String>) aliasList;
	}

}

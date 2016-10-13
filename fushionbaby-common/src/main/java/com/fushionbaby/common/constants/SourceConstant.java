package com.fushionbaby.common.constants;

import java.util.HashMap;
import java.util.Map;

/**
 * 系统来源,对应source表
 * @author duxihu
 *
 */
public class SourceConstant {
	/** APP 端*/
	public static final String APP_CODE = "0";
	/** android客户端*/
	public static final String ANDROID_CODE ="1";
	/** IOS客户端*/
	public static final String IOS_CODE="2";	
	/** 商城web端*/
	public static final String WEB_CODE="3";
	/** 运营 后台*/
	public static final String OPERATE_CODE="4";
	/** 定时任务*/
	public static final String TIMING_TASK_CODE="5"; 
	/** CMS管理系统*/
	public static final String CMS_CODE="6"; 
	/** ERP管理系统*/
	public static final String ERP_CODE="7"; 
	/** WAP触屏版*/
	public static final String WAP_CODE ="9";
	
	public static Map<String, String> getSourceArray(){
		Map<String, String> sourceMap = new HashMap<String, String>();
		sourceMap.put(ANDROID_CODE, "android客户端");
		sourceMap.put(IOS_CODE, "IOS客户端");
		sourceMap.put(WEB_CODE, "商城web端");
		sourceMap.put(OPERATE_CODE, "运营后台");
		sourceMap.put(TIMING_TASK_CODE, "定时任务");
		sourceMap.put(CMS_CODE, "CMS管理系统");
		sourceMap.put(ERP_CODE, "ERP管理系统");
		sourceMap.put(WAP_CODE, "WAP触屏版");
		
		return sourceMap;
	}

	
}

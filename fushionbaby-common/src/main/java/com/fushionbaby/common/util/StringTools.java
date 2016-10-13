package com.fushionbaby.common.util;

import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

import org.apache.commons.lang3.StringUtils;

/**
 * 本地系统,字符串特定情况处理工具类
 * @author 张明亮
 */
public class StringTools {
	
	/** 
	 * 把list转换为一个用逗号分隔的字符串,以便于用in+String 查询；
	 * boolean 是true则逗号分隔的字符串需要'单撇号括住,为false时不需要'单撇号括住。
	 * @param List
	 * @param flag
	 */  
	public static String listToString(List<? extends Object> list,boolean flag) {  
	    StringBuilder sb = new StringBuilder();  
	    if (list != null && list.size() > 0) {  
	        for (int i = 0; i < list.size(); i++) {
	        	String strTmp = (String)list.get(i);
	        	if(StringUtils.isBlank(strTmp)){
	        		continue;
	        	}
				String itemStr = flag ? "'" + strTmp + "'" : strTmp;
	            if (i < list.size() - 1) {  
	                sb.append(itemStr + ",");
	            } else {  
	                sb.append(itemStr);  
	            } 
	        }
	    }
	    return sb.toString();  
	}
	
	/**
	 * 将一个字符串按照指定字符、split成一个list<String>集合
	 * @param str
	 * @param splitStr
	 * @return
	 */
	public static List<String> splitStringToList(String str,String splitStr){
		List<String> list = new ArrayList<String>();
		try {
			StringTokenizer strTokenizer = new StringTokenizer(str,splitStr);
			while (strTokenizer.hasMoreTokens()){
				String strTmp = strTokenizer.nextToken();
				if(StringUtils.isBlank(strTmp)){
					continue;
				}
				list.add(strTmp.trim());
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		return list;
	}
	
	/**
	 * 替换字符串中的中文、全角半角的逗号,为英文逗号
	 * @param str
	 * @return
	 */
	public static String replaceAllDot(String str){
		String strTmp="";
		if(StringUtils.isNotBlank(str)){
			//如果字符串中存在中文逗号、替换成英文的逗号
			strTmp = str.replaceAll("，",",");//半角逗号
			strTmp = str.replaceAll("，",",");//全角逗号
		}
		return strTmp;
	}
}

package com.fushionbaby.common.util;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Type;

import org.apache.commons.lang3.StringUtils;
/***
 * 将Object对象前后的空格去除掉
 * @author Leon
 *
 */
public class TrimObjectUtil {
	/***
	 * 去除Object中类型为String的前后空格
	 * @param obj
	 */
	public static void TrimObjectString(Object obj){
		if (obj != null) { 
			Class<? extends Object> classz = obj.getClass(); 
			Field fields[] = classz.getDeclaredFields(); 
			for (Field field : fields) { 
				try { 
					Type t = field.getGenericType(); 
				
					if (!StringUtils.equals(field.getName(), "serialVersionUID")) { 
						Method m = classz.getMethod("get" + change(field.getName())); 
						Object objValue = m.invoke(obj);
						if (!CheckObjectIsNull.isNull(objValue)) {
							String typeStr =t.toString().substring((t.toString().lastIndexOf(".")+1));
							if(StringUtils.equals(typeStr, "String")){
								Method mtd = classz.getMethod("set" + change(field.getName()), new Class[] { String.class });// 取得所需类的方法对象 
								mtd.invoke(obj, new Object[] { objValue.toString().trim() });
							}
						}
					}
				} catch (Exception e) { 
					e.printStackTrace(); 
				} 
			} 
		} 
	}
	
	/** 
	* @param src 
	*            源字符串 
	* @return 字符串，将src的第一个字母转换为大写，src为空时返回null 
	*/ 
	public static String change(String src) { 
		if (src != null) { 
			StringBuffer sb = new StringBuffer(src); 
			sb.setCharAt(0, Character.toUpperCase(sb.charAt(0))); 
			return sb.toString(); 
		} 
		return null; 
	}
	
}

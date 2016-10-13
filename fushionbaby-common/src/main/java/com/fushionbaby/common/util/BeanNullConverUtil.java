/**
 * 
 */
package com.fushionbaby.common.util;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;

/**
 * @author mengshaobo
 *	把实体类中的所有null值转化为空串
 */
public class BeanNullConverUtil {

	public static void nullConver(Object obj) { 
		if (obj != null) { 
			Class<? extends Object> classz = obj.getClass(); 
			Field fields[] = classz.getDeclaredFields(); 
			for (Field field : fields) { 
				try { 
					Type t = field.getGenericType(); 
				
					if (!StringUtils.equals(field.getName(), "serialVersionUID")) { 
						Method m = classz.getMethod("get" + change(field.getName())); 
						Object name = m.invoke(obj);
						if (ObjectUtils.equals(null, name)) { 
							String typeStr =t.toString().substring((t.toString().lastIndexOf(".")+1));
							if(StringUtils.equals(typeStr, "String")){
								Method mtd = classz.getMethod("set" + change(field.getName()), new Class[] { String.class });// 取得所需类的方法对象 
								mtd.invoke(obj, new Object[] { "" });
							}else if(StringUtils.equals(typeStr, "Long") ){
								Method mtd = classz.getMethod("set" + change(field.getName()), new Class[] { Long.class });// 取得所需类的方法对象 
								mtd.invoke(obj,Long.valueOf(0));
							}else if(StringUtils.equals(typeStr, "Integer")){
								Method mtd = classz.getMethod("set" + change(field.getName()), new Class[] { Integer.class });// 取得所需类的方法对象 
								mtd.invoke(obj,Integer.valueOf(0));
							}else if(StringUtils.equals(typeStr, "BigDecimal")){
								Method mtd = classz.getMethod("set" + change(field.getName()), new Class[] { BigDecimal.class });// 取得所需类的方法对象 
								mtd.invoke(obj,  BigDecimal.valueOf(0));
							}else if(StringUtils.contains(t.toString(), "List") ){
								Method mtd = classz.getMethod("set" + change(field.getName()), new Class[] {List.class });// 取得所需类的方法对象
								mtd.invoke(obj,  new ArrayList<Object>());
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
	public static void main(String[] args) {
		String a= "setList";
		System.out.println(StringUtils.contains(a, "List"));
		
	}

}

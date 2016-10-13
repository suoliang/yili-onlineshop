package com.fushionbaby.common.util;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.util.CollectionUtils;

public class BeanCopyUtil {
	 /** 
	   * 复制对象属性 
	   *  
	   * @param from 要复制的 
	   * @param to 复制给 
	   * @param excludsArray 
	   *            排除属性列表 
	   * @throws Exception 
	   */  

	
	public static void copyProperty(Object from,Object to,String excludsAry) throws Exception{
		
		List<String> excludesList = null;
		
		if(StringUtils.isNotBlank(excludsAry)){
			excludesList = Arrays.asList(excludsAry);
		}
		
		Method[] fromMethods = from.getClass().getMethods();
		Method[] toMethods = to.getClass().getMethods();
		Method fromMethod = null;
		Method toMethod = null;
		String fromMethodName = null;
		String toMethodName = null;
		
		for (int i = 0; i < fromMethods.length; i++) {
			fromMethod = fromMethods[i];
			fromMethodName = fromMethod.getName();
			if(!fromMethodName.contains("get")){
				continue;
			}
			if(!CollectionUtils.isEmpty(excludesList) && excludesList.contains(fromMethodName.substring(3).toLowerCase())){
				continue;
			}
			toMethodName = "set" + fromMethodName.substring(3);
			toMethod = findMethodByName(toMethods, toMethodName);
			if(toMethod == null){
				continue;
			}
			Object value = fromMethod.invoke(from, new Object[0]);
			if(value == null){
				continue;
			}
			if(value instanceof Collection<?>){
				Collection<?> newValue = (Collection<?>)value;
				if(newValue.size() <=0){
					continue;
				}
			}
			toMethod.invoke(to, new Object[]{value});
			
		}
	}
	
	 /** 
	     * 对象属性值复制，仅复制指定名称的属性值 
	     *  
	     * @param from 
	    * @param to 
	    * @param includsArray  排除属性列表 
	    * @throws Exception 
	     */  
	  public static void copyPropertiesInclude(Object from, Object to,  
	            String[] includsArray) throws Exception {  
	          
	      List<String> includesList = null;  
	        
	      if (includsArray != null && includsArray.length > 0) {  
	             
	           includesList = Arrays.asList(includsArray);   
	             
	       } else {      
	          return;  
	       }  
	      	Method[] fromMethods = from.getClass().getMethods();  
	      	Method[] toMethods = to.getClass().getMethods();  
	        Method fromMethod = null, toMethod = null;  
	        String fromMethodName = null, toMethodName = null;  
	          
	        for (int i = 0; i < fromMethods.length; i++) {  
	              
	           fromMethod = fromMethods[i];  
	           fromMethodName = fromMethod.getName();  
	              
	           if (!fromMethodName.contains("get"))  {
	              continue;  
	           }
	           String str = fromMethodName.substring(3);  
	            
	           if (!includesList.contains(str.substring(0, 1).toLowerCase()  
	                   + str.substring(1))) {  
	               continue;  
	            }  
             
	           toMethodName = "set" + fromMethodName.substring(3);  
	           toMethod = findMethodByName(toMethods, toMethodName);  
	              
	           if (toMethod == null) {
	        	   continue;  
	           }
             
	           Object value = fromMethod.invoke(from, new Object[0]);  
	             
	           if (value == null)  
	               continue;  
              
	          if (value instanceof Collection) {  
                  
	                Collection<?> newValue = (Collection<?>) value;  
	                  
	               if (newValue.size() <= 0)  
	                  continue;  
	            }  
	              
	           toMethod.invoke(to, new Object[] { value });  
	       }  
	    }  
	/**
	 * 从方法数组中获取指定名称的方法 
	 * @param toMethods
	 * @param toMethodName
	 * @return
	 */
	private static Method findMethodByName(Method[] toMethods,String toMethodName){
		for (int i = 0; i < toMethods.length; i++) {
			if(StringUtils.equals(toMethods[i].getName(), toMethodName)){
				return toMethods[i];
			}
		}
		return null;
	}

}

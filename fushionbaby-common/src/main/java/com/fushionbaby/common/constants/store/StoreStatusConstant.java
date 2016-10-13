/**
 * @description 类描述...
 * @author 孟少博
 * @date 2015年12月14日上午9:38:03
 */
package com.fushionbaby.common.constants.store;

/**
 * @description 类描述...
 * @author 孟少博
 * @date 2015年12月14日上午9:38:03
 */
public class StoreStatusConstant {

	/** 开通*/
	public static final Integer OPEN = 1;
	
	/** 停用*/
	public static final Integer DISABLE = 2;
	
	/** 测试*/
	public static final Integer TEST = 4;
	
	
	
	public abstract class StoreStatusDto{
		
		/** 正常运营*/
		public static final String OPEN = "0";
		
		/** 未营业*/
		public static final String NOT_OPERATED = "1";
		
		/** 打烊了（未在运营时间段）*/
		public static final String NOT_TIME_INTERVAL = "2";
	}
}

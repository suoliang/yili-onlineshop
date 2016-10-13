/**
 * @description 类描述...
 * @author 孟少博
 * @date 2016年1月11日下午5:55:23
 */
package com.fushionbaby.sysmgr.dao;

import com.fushionbaby.sysmgr.model.SysmgrAppHomeConfig;

/**
 * @description 类描述...
 * @author 孟少博
 * @date 2016年1月11日下午5:55:23
 */
public interface SysmgrAppHomeConfigDao {

	
	SysmgrAppHomeConfig findByPlatfrom(Integer platform);
}

/**
 * @description 类描述...
 * @author 孟少博
 * @date 2015年9月29日下午2:05:50
 */
package com.fushionbaby.sysmgr.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.fushionbaby.sysmgr.model.SysmgrFunctionalBlock;

/**
 * @description 类描述...
 * @author 孟少博
 * @date 2015年9月29日下午2:05:50
 */
public interface SysmgrFunctionalBlockDao {

	
	SysmgrFunctionalBlock findByCode(String code);
	
	List<SysmgrFunctionalBlock> findAllByDisable(@Param("disable") String disable);
	
	void add(SysmgrFunctionalBlock sysmgrFunctionalBlock);
	
	void update(SysmgrFunctionalBlock sysmgrFunctionalBlock);
	
	void deleteById(Long id);
	
	
}

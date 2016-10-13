/**
 * @description 类描述...
 * @author 孟少博
 * @date 2015年9月29日下午2:53:45
 */
package com.fushionbaby.sysmgr.service;

import java.util.List;

import com.fushionbaby.sysmgr.model.SysmgrFunctionalBlock;

/**
 * @description 类描述...
 * @author 孟少博
 * @date 2015年9月29日下午2:53:45
 */
public interface SysmgrFunctionalBlockService<T extends SysmgrFunctionalBlock> {

	
	SysmgrFunctionalBlock findByCode(String code);
	
	List<SysmgrFunctionalBlock> findAllByDisable(String disable);
	
	void add(SysmgrFunctionalBlock sysmgrFunctionalBlock);
	
	void update(SysmgrFunctionalBlock sysmgrFunctionalBlock);
	
	void deleteById(Long id);
}

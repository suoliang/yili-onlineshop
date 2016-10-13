/**
 * @description 类描述...
 * @author 孟少博
 * @date 2015年9月29日下午2:54:16
 */
package com.fushionbaby.sysmgr.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fushionbaby.sysmgr.dao.SysmgrFunctionalBlockDao;
import com.fushionbaby.sysmgr.model.SysmgrFunctionalBlock;
import com.fushionbaby.sysmgr.service.SysmgrFunctionalBlockService;

/**
 * @description 类描述...
 * @author 孟少博
 * @date 2015年9月29日下午2:54:16
 */
@Service
public class SysmgrFunctionalBlockServiceImpl implements
		SysmgrFunctionalBlockService<SysmgrFunctionalBlock> {

	@Autowired
	private SysmgrFunctionalBlockDao dao;
	/* (non-Javadoc)
	 * @see com.fushionbaby.sysmgr.service.SysmgrFunctionalBlockService#findByCode(java.lang.String)
	 */
	public SysmgrFunctionalBlock findByCode(String code) {
		return dao.findByCode(code);
	}

	/* (non-Javadoc)
	 * @see com.fushionbaby.sysmgr.service.SysmgrFunctionalBlockService#findAllByDisable(java.lang.String)
	 */
	public List<SysmgrFunctionalBlock> findAllByDisable(String disable) {
		return dao.findAllByDisable(disable);
	}

	/* (non-Javadoc)
	 * @see com.fushionbaby.sysmgr.service.SysmgrFunctionalBlockService#add(com.fushionbaby.sysmgr.model.SysmgrFunctionalBlock)
	 */
	public void add(SysmgrFunctionalBlock sysmgrFunctionalBlock) {
		dao.add(sysmgrFunctionalBlock);
	}

	/* (non-Javadoc)
	 * @see com.fushionbaby.sysmgr.service.SysmgrFunctionalBlockService#update(com.fushionbaby.sysmgr.model.SysmgrFunctionalBlock)
	 */
	public void update(SysmgrFunctionalBlock sysmgrFunctionalBlock) {
		dao.update(sysmgrFunctionalBlock);
	}

	/* (non-Javadoc)
	 * @see com.fushionbaby.sysmgr.service.SysmgrFunctionalBlockService#deleteById(java.lang.Long)
	 */
	public void deleteById(Long id) {
		dao.deleteById(id);
	}

}

/**
 * @description 类描述...
 * @author 孟少博
 * @date 2015年9月9日下午2:07:24
 */
package com.fushionbaby.log.service;

import java.util.List;

import org.springframework.dao.DataAccessException;

import com.fushionbaby.common.condition.log.LogPromotionsRecordQueryConditon;
import com.fushionbaby.common.util.BasePagination;
import com.fushionbaby.log.model.LogPromotionsRecord;

/**
 * @description 类描述...
 * @author 孟少博
 * @date 2015年9月9日下午2:07:24
 */
public interface LogPromotionsRecordService {
	
	List<LogPromotionsRecord> queryByCondition(LogPromotionsRecordQueryConditon queryCondition);
	
	void add(LogPromotionsRecord logPromotionsRecord);
	
	void update(LogPromotionsRecord logPromotionsRecord);
	
	void deleteById(Long id);
	/**
	 * 分页查询
	 * @author suoliang
	 */
	BasePagination getListPage(BasePagination page) throws DataAccessException;
}

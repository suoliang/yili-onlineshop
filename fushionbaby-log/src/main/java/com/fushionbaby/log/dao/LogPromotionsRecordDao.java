/**
 * @description 类描述...
 * @author 孟少博
 * @date 2015年9月9日下午1:25:29
 */
package com.fushionbaby.log.dao;

import java.util.List;
import java.util.Map;

import com.fushionbaby.common.condition.log.LogPromotionsRecordQueryConditon;
import com.fushionbaby.log.model.LogPromotionsRecord;

/**
 * @description 类描述...
 * @author 孟少博
 * @date 2015年9月9日下午1:25:29
 */
public interface LogPromotionsRecordDao {
	
	
	List<LogPromotionsRecord> queryByCondition(LogPromotionsRecordQueryConditon queryCondition);
	
	
	void add(LogPromotionsRecord logPromotionsRecord);
	
	void update(LogPromotionsRecord logPromotionsRecord);
	
	void deleteById(Long id);
	/**
	 * 分页查询
	 * @author suoliang
	 */
	List<LogPromotionsRecord> getList(Map<String, Object> map);

	/**
	 * 分页查询的总记录数
	 * 
	 * @param map
	 * @return 总记录数
	 */
	Integer getTotal(Map<String, Object> map);
}

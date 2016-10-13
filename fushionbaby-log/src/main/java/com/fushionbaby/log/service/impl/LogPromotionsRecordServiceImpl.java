/**
 * @description 类描述...
 * @author 孟少博
 * @date 2015年9月9日下午2:08:11
 */
package com.fushionbaby.log.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.fushionbaby.common.condition.log.LogPromotionsRecordQueryConditon;
import com.fushionbaby.common.util.BasePagination;
import com.fushionbaby.log.dao.LogPromotionsRecordDao;
import com.fushionbaby.log.model.LogPromotionsRecord;
import com.fushionbaby.log.service.LogPromotionsRecordService;

/**
 * @description 类描述...
 * @author 孟少博
 * @date 2015年9月9日下午2:08:11
 */
@Service
public class LogPromotionsRecordServiceImpl implements LogPromotionsRecordService{

	@Autowired
	private LogPromotionsRecordDao dao;
	
	public List<LogPromotionsRecord> queryByCondition(
			LogPromotionsRecordQueryConditon queryCondition) {
		// TODO Auto-generated method stub
		return dao.queryByCondition(queryCondition);
	
	}

	public void add(LogPromotionsRecord logPromotionsRecord) {
		// TODO Auto-generated method stub
		dao.add(logPromotionsRecord);
	}

	public void update(LogPromotionsRecord logPromotionsRecord) {
		// TODO Auto-generated method stub
		dao.update(logPromotionsRecord);
	}

	public void deleteById(Long id){
		dao.deleteById(id);
	}

	/** 分页 */
	public BasePagination getListPage(BasePagination page)
			throws DataAccessException {
		Integer total = dao.getTotal(page.getSearchParamsMap());
		page.setCurrentTotal(total);
		if (total > 0) {
			List<LogPromotionsRecord> list = dao.getList(page
					.getSearchParamsMap());
			page.setResult(list);
		} else {
			page.setResult(new ArrayList<LogPromotionsRecord>());
		}
		return page;
	}
}

package com.fushionbaby.member.service;

import java.util.List;

import org.springframework.dao.DataAccessException;

import com.fushionbaby.common.service.BaseService;
import com.fushionbaby.common.util.BasePagination;
import com.fushionbaby.member.model.VerificationRecord;
/***
 * 
 * @description 类描述...
 * @author 徐培峻
 * @date 2015年12月7日下午3:53:11
 */
public interface VerificationRecordService<T extends VerificationRecord> extends BaseService<T> {

	
      List<VerificationRecord>	findByBankCardNoAndID(String cardNo,String idCard,String trueName);
      
      BasePagination getListPage(BasePagination page) throws DataAccessException;
}


package com.aladingshop.alabao.service; 

import java.util.List;

import org.springframework.dao.DataAccessException;

import com.fushionbaby.common.util.BasePagination;
import com.aladingshop.alabao.model.AlabaoAccountBank;

public interface AlabaoAccountBankService<T extends AlabaoAccountBank> { 
	
	
	void deleteById(Long id);

	void add(AlabaoAccountBank object);

	AlabaoAccountBank findById(Long id);

	List<AlabaoAccountBank> findAll();

	void update(AlabaoAccountBank object);
	
	BasePagination getListPage(BasePagination page) throws DataAccessException;

	List<AlabaoAccountBank> findByMemberId(Long memberId);
	
	List<AlabaoAccountBank> findByAccount(String account);
	/***
	 * 验证姓名和银行卡信息是否正确
	 * @param trueName
	 * @param bankCardNo
	 * @param account 
	 * @param b
	 */
	void updateIsValidate(String trueName, String bankCardNo, String account, boolean b);
	/**
	 * @description 通过银行卡卡号和是否删除查询对象(判断唯一性)
	 * @param cardNo
	 * @param isDelete(y/n,n未删除,y已删除) 是否删除 可传空
	 * @return
	 */
	AlabaoAccountBank findByBankCardNo(String cardNo,String isDelete);
}

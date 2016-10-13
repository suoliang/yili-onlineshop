package com.aladingshop.alabao.dao; 

import java.util.List;
import java.util.Map;

import com.aladingshop.alabao.model.AlabaoAccountBank;

public interface AlabaoAccountBankDao { 


	void deleteById(Long id);

	void add(AlabaoAccountBank object);

	AlabaoAccountBank findById(Long id);

	List<AlabaoAccountBank> findAll();

	void update(AlabaoAccountBank object);
	
	List<AlabaoAccountBank> getListPage(Map<String, Object> map);
	
	Integer getTotal(Map<String, Object> map);
	
	List<AlabaoAccountBank> findByMemberId(Long memberId);
	
	List<AlabaoAccountBank> findByAccount(String account);
	/***
	 * @description 通过银行卡卡号和是否删除查询对象(判断唯一性)
	 * @param cardNo
	 * @param isDelete(y/n) 是否删除 可传空
	 * @return
	 */
	AlabaoAccountBank findByBankCardNo(Map<String, Object> map);

	/***
	 * 更新验证过的信息
	 * @param map
	 */
	void updateIsValidate(Map<String, Object> map);

}

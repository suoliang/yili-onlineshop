package com.aladingshop.alabao.dao;

import java.util.List;
import java.util.Map;

import com.aladingshop.alabao.model.AlabaoLimit;
import com.aladingshop.alabao.model.AlabaoTurnOut;

/***
 * @description 如意宝账户转出限制
 * @author 索亮
 * @date 2015年11月16日下午2:13:22
 */
public interface AlabaoLimitDao {
	/**添加*/
	void add(AlabaoLimit alabaoLimit);
	/**通过如意宝账户查看对象*/
	AlabaoLimit findByAccount(String account);
	
	void update(AlabaoLimit alabaoLimit);
	
	List<AlabaoLimit> getListPage(Map<String, Object> map);
	
	Integer getTotal(Map<String, Object> map);
	
	void deleteById(Long id);
}

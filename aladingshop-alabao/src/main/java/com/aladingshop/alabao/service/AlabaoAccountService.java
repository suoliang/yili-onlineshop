package com.aladingshop.alabao.service;


import java.util.List;
import java.util.Map;

import com.aladingshop.alabao.model.AlabaoAccount;
import com.fushionbaby.common.util.BasePagination;

public interface AlabaoAccountService <T extends AlabaoAccount>{
	
	public void add(AlabaoAccount alabaoAccount);
	
	public void updateByAccount(AlabaoAccount alabaoAccount);
	
	public void deleteById(Long id);
	
	public void deleteByAccount(String account);
	
	public AlabaoAccount findById(Long id);
	
	public AlabaoAccount findByAccount(String account);
	
	BasePagination getListPage(BasePagination pageParams);
	/**登录*/
	public AlabaoAccount loginByAccAndPwd(String account, String loginPassword);
	
	void updateStatusById(Map<String, Object> map);

	//查询所有数据
	List<AlabaoAccount> findAll();
	

	AlabaoAccount findByMemberId(Long memberId);
	
	List<AlabaoAccount> findByLevel(String level);
	
	
	void updateByMemberId(AlabaoAccount alabaoAccount);


	/***
	 * 验证用户是否信息准确
	 * @param trueName
	 * @param id
	 * @param account 
	 * @param b
	 */
	public void updateIsValidate(String trueName, String id, String account, boolean b);
	/**
	 * @description 通过身份证查询记录(判断唯一性)
	 * @param identityCardNo
	 */
	public AlabaoAccount findByIDCard(String identityCardNo);
	
}

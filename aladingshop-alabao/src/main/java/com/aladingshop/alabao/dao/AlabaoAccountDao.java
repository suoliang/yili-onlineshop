package com.aladingshop.alabao.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.aladingshop.alabao.model.AlabaoAccount;

public interface AlabaoAccountDao {
	
	public void add(AlabaoAccount alabaoAccount);
	
	public void updateByAccount(AlabaoAccount alabaoAccount);
	
	public void deleteById(Long id);
	
	public void deleteByAccount(String account);
	
	public AlabaoAccount findById(Long id);
	
	public AlabaoAccount findByAccount(String account);
	
	List<AlabaoAccount> getList(Map<String, Object> map);
	
	Integer getTotal(Map<String, Object> map);
	
	/**登录*/
	public AlabaoAccount loginByAccAndPwd(@Param("account")String account, @Param("loginPassword")String loginPassword);
	
	void updateStatusById(Map<String, Object> map);
	
	//查询所有数据
	List<AlabaoAccount> findAll();
	
	AlabaoAccount findByMemberId(Long memberId);
	
	void updateByMemberId(AlabaoAccount alabaoAccount);
	
	
	List<AlabaoAccount> findByLevel(String level) ;
	/***
	 * 验证用户信息是否正确 
	 * @param map
	 */
	public void updateIsValidate(Map<String, Object> map);

	/**
	 * @description 通过身份证查询记录(判断唯一性)
	 * @param identityCardNo
	 * @return
	 */
	public AlabaoAccount findByIDCard(String identityCardNo);

	
	
}
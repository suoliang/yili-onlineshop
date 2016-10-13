package com.aladingshop.alabao.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.aladingshop.alabao.dao.AlabaoAccountDao;
import com.aladingshop.alabao.model.AlabaoAccount;
import com.aladingshop.alabao.service.AlabaoAccountService;
import com.fushionbaby.common.constants.VerificationConstant;
import com.fushionbaby.common.util.BasePagination;

@Service
@Transactional
public class AlabaoAccountServiceImpl implements AlabaoAccountService<AlabaoAccount> {

	@Autowired
	private AlabaoAccountDao alabaoAccountDao;
	
	public void add(AlabaoAccount alabaoAccount) {
		alabaoAccountDao.add(alabaoAccount);
		
	}


	public void deleteById(Long id) {
		alabaoAccountDao.deleteById(id);
		
	}

	public AlabaoAccount findById(Long id) {
		return alabaoAccountDao.findById(id);
	}


	public void updateByAccount(AlabaoAccount alabaoAccount) {
		alabaoAccountDao.updateByAccount(alabaoAccount);
		
	}



	public void deleteByAccount(String account) {
		alabaoAccountDao.deleteByAccount(account);
	}



	public AlabaoAccount findByAccount(String account) {
		return alabaoAccountDao.findByAccount(account);
	}



	
	public BasePagination getListPage(BasePagination pageParams) {
		Map<String, Object> map = pageParams.getSearchParamsMap();
		List<AlabaoAccount> alabaoAccountList = new ArrayList<AlabaoAccount>();
		Integer total = alabaoAccountDao.getTotal(map);
		pageParams.setCurrentTotal(total);
		if (total > 0) {
			alabaoAccountList = alabaoAccountDao.getList(map);
		}
		pageParams.setResult(alabaoAccountList);
		return pageParams;
	}
	
	public AlabaoAccount loginByAccAndPwd(String account, String loginPassword) {
		return alabaoAccountDao.loginByAccAndPwd(account,loginPassword);
	}

	public void updateStatusById(Map<String, Object> map) {
		alabaoAccountDao.updateStatusById(map);
	}


	public List<AlabaoAccount> findAll() {
		return alabaoAccountDao.findAll();
	}


	public AlabaoAccount findByMemberId(Long memberId) {
		return alabaoAccountDao.findByMemberId(memberId);
	}


	public void updateByMemberId(AlabaoAccount alabaoAccount) {
		alabaoAccountDao.updateByMemberId(alabaoAccount);
	}


	public List<AlabaoAccount> findByLevel(String level) {
		return alabaoAccountDao.findByLevel(level);
	}

	public void updateIsValidate(String trueName, String id,String account,boolean b) {
		Map<String , Object> map=new HashMap<String, Object>();
		map.put("trueName", trueName);
		map.put("identityCardNo", id);
		map.put("account", account);
		if(b)
			map.put("isValidate",VerificationConstant.IS_VALIDATE_STATUS_SUCCESS);
		else
			map.put("isValidate", VerificationConstant.IS_VALIDATE_STATUS_FAIL);
		this.alabaoAccountDao.updateIsValidate(map);
		
	}

	public AlabaoAccount findByIDCard(String identityCardNo) {
		return alabaoAccountDao.findByIDCard(identityCardNo);
	}


}

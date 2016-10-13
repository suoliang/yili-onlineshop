package com.aladingshop.alabao.service.impl; 


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.aladingshop.alabao.model.AlabaoAccountBank;
import com.aladingshop.alabao.dao.AlabaoAccountBankDao;
import com.aladingshop.alabao.service.AlabaoAccountBankService;
import com.fushionbaby.common.constants.VerificationConstant;
import com.fushionbaby.common.util.BasePagination;

@Service
public class AlabaoAccountBankServiceImpl implements AlabaoAccountBankService<AlabaoAccountBank>  { 
	
	@Autowired
	private AlabaoAccountBankDao objectDao;
	
	public void add(AlabaoAccountBank object) {
		objectDao.add(object);
		
	}
	
	public AlabaoAccountBank findById(Long id) {
		return objectDao.findById(id);
	}
	
	public void update(AlabaoAccountBank object) {
		objectDao.update(object);
		
	}
	
	public List<AlabaoAccountBank> findAll() {
		return objectDao.findAll();
		
	}
	
	public void deleteById(Long id) {
		objectDao.deleteById(id);
	}


	public BasePagination getListPage(BasePagination page) {
		Integer total = objectDao.getTotal(page.getSearchParamsMap());
		page.setCurrentTotal(total);
		if (total > 0) {
			List<AlabaoAccountBank> list = objectDao.getListPage(page.getSearchParamsMap());
			page.setResult(list);
		} else {
			page.setResult(new ArrayList<AlabaoAccountBank>());
		}
		return page;
	}

	public List<AlabaoAccountBank> findByMemberId(Long memberId) {
		return objectDao.findByMemberId(memberId);
	}

	public List<AlabaoAccountBank> findByAccount(String account) {
		return objectDao.findByAccount(account);
	}

	public void updateIsValidate(String trueName, String bankCardNo,String account, boolean b) {
	
		
		Map<String , Object> map=new HashMap<String, Object>();
		map.put("cardHolder", trueName);
		map.put("cardNo", bankCardNo);
		map.put("account", account);
		if(b)
			map.put("isValidate", VerificationConstant.IS_VALIDATE_STATUS_SUCCESS);
		else
			map.put("isValidate", VerificationConstant.IS_VALIDATE_STATUS_FAIL);
		this.objectDao.updateIsValidate(map);
	}
	
	public AlabaoAccountBank findByBankCardNo(String cardNo,String isDelete) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("cardNo", cardNo);
		map.put("isDelete", isDelete);
		return objectDao.findByBankCardNo(map);
	}

}

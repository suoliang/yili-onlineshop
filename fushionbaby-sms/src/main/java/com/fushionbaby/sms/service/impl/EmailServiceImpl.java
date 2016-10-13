package com.fushionbaby.sms.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.fushionbaby.sms.dao.EmailDao;
import com.fushionbaby.sms.model.Email;
import com.fushionbaby.sms.service.EmailService;

@Service
public class EmailServiceImpl implements EmailService<Email>{

	@Autowired
	private EmailDao emailDao;
	public void add(Email sysMailInstruction) throws DataAccessException {
		emailDao.add(sysMailInstruction);
	}

	public void deleteById(Long id) throws DataAccessException {
		emailDao.deleteById(id);
	}

	public void update(Email sysMailInstruction) throws DataAccessException {
		emailDao.update(sysMailInstruction);	
	}

	public Email findById(Long id) throws DataAccessException {
		return null;
	}

	public List<Email> findAll() throws DataAccessException {
		return null;
	}

}

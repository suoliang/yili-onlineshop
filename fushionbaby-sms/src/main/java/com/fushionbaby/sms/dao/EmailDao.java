package com.fushionbaby.sms.dao;

import com.fushionbaby.sms.model.Email;

public interface EmailDao {

    void add(Email email);

	void deleteById(Long id);

	void update(Email email);

	
}

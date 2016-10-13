package com.fushionbaby.common.service;

import org.springframework.dao.DataAccessException;


public interface BaseService<T> {
	
	public void add(T entity) throws DataAccessException;
	
	public void deleteById(Long id) throws DataAccessException;
	
	public void update(T entity) throws DataAccessException;
	
	public T findById(Long id) throws DataAccessException;
	
	
}


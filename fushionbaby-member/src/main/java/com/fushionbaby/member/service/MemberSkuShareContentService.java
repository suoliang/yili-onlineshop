/**
 * 
 */
package com.fushionbaby.member.service;

import com.fushionbaby.member.model.MemberSkuShareContent;


/**
 * @author mengshaobo
 * 
 */
public interface MemberSkuShareContentService{
	
	void add(MemberSkuShareContent skuShareContent);

	void deleteById(Long id);
}

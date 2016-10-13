package com.fushionbaby.member.dao;

import java.util.List;

import com.fushionbaby.member.model.MemberSkuView;

/**
 * 
 * @author King suoliang
 *
 */
public interface MemberSkuViewDao {

	    void add(MemberSkuView skuView);

	    void deleteById(Long id);

	    void update(MemberSkuView skuView);

	    MemberSkuView findById(Long id);

	    List<MemberSkuView> findAll();
}


package com.fushionbaby.member.service;

import java.util.List;

import com.fushionbaby.member.model.MemberGradeConfig;
/**
 * 
 * @author cyla
 *
 */
public interface MemberGradeConfigService {
	void add(MemberGradeConfig memberGrade);

	void deleteById(Long id);

	void update(MemberGradeConfig memberGrade);

	MemberGradeConfig findById(Long id);
	
	MemberGradeConfig findByGradeCode(String gradeCode);
	

	List<MemberGradeConfig> findAll();
}

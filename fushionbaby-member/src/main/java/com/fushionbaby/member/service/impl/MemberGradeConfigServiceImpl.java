package com.fushionbaby.member.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fushionbaby.member.dao.MemberGradeConfigDao;
import com.fushionbaby.member.model.MemberGradeConfig;
import com.fushionbaby.member.service.MemberGradeConfigService;

@Service
public class MemberGradeConfigServiceImpl implements MemberGradeConfigService {
	@Autowired
	private MemberGradeConfigDao memberGradeDao;

	public void add(MemberGradeConfig memberGrade) {
		memberGradeDao.add(memberGrade);

	}

	public void deleteById(Long id) {
		memberGradeDao.deleteById(id);

	}

	public void update(MemberGradeConfig memberGrade) {
		memberGradeDao.update(memberGrade);

	}

	public MemberGradeConfig findById(Long id) {
		return memberGradeDao.findById(id);
	}

	public MemberGradeConfig findByGradeCode(String gradeCode) {
		return memberGradeDao.findByGradeCode(gradeCode);
	}

	public List<MemberGradeConfig> findAll() {
		return memberGradeDao.findAll();
	}

}

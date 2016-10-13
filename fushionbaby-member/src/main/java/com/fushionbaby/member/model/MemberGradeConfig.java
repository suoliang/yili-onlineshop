package com.fushionbaby.member.model;

/**
 * 
 * @author cyla
 * 
 */
public class MemberGradeConfig {

	private Long id;

	private String gradeValue;
	
	private String gradeName;
	
	private String gradeCode;
	
	private String gradeIcon;
	
	private String remark;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getGradeValue() {
		return gradeValue;
	}

	public void setGradeValue(String gradeValue) {
		this.gradeValue = gradeValue;
	}

	public String getGradeName() {
		return gradeName;
	}

	public void setGradeName(String gradeName) {
		this.gradeName = gradeName;
	}

	public String getGradeCode() {
		return gradeCode;
	}

	public void setGradeCode(String gradeCode) {
		this.gradeCode = gradeCode;
	}

	public String getGradeIcon() {
		return gradeIcon;
	}

	public void setGradeIcon(String gradeIcon) {
		this.gradeIcon = gradeIcon;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

}

package com.fushionbaby.member.model;

import java.util.Date;

public class MemberExtraInfo {
    private Long id;

    private Long memberId;

    private String nickname;

    private Integer babyFm;

    private Integer babyGender;

    private Date babyBirthday;
    
    private Date createTime;
    
    private Date updateTime;
    
    private String weixinNo;
    /**用户累计的登陆天数*/
	private int loginDays;
	/**等级编码*/
	private String gradeCode;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getMemberId() {
        return memberId;
    }

    public void setMemberId(Long memberId) {
        this.memberId = memberId;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname == null ? null : nickname.trim();
    }

    public Integer getBabyFm() {
        return babyFm;
    }

    public void setBabyFm(Integer babyFm) {
        this.babyFm = babyFm;
    }

    public Integer getBabyGender() {
        return babyGender;
    }

    public void setBabyGender(Integer babyGender) {
        this.babyGender = babyGender;
    }

    public Date getBabyBirthday() {
        return babyBirthday;
    }

    public void setBabyBirthday(Date babyBirthday) {
        this.babyBirthday = babyBirthday;
    }

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public String getWeixinNo() {
		return weixinNo;
	}

	public void setWeixinNo(String weixinNo) {
		this.weixinNo = weixinNo;
	}

	public int getLoginDays() {
		return loginDays;
	}

	public void setLoginDays(int loginDays) {
		this.loginDays = loginDays;
	}

	public String getGradeCode() {
		return gradeCode;
	}

	public void setGradeCode(String gradeCode) {
		this.gradeCode = gradeCode;
	}

	@Override
	public String toString() {
		return "MaMemberInfo [id=" + id + ", memberId=" + memberId
				+ ", nickname=" + nickname 
				+ ", babyFm=" + babyFm + ", babyGender=" + babyGender
				+ ", babyBirthday=" + babyBirthday + "]";
	}
}
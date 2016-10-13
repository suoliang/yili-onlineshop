package com.aladingshop.alabao.dto;

import java.math.BigDecimal;
import java.util.Date;
/**
 * @description 阿拉宝收益记录表
 * @author 索亮
 * @date 2015年9月8日上午9:41:00
 */
public class AlabaoIncomeRecordDto {
    /**收益记录id*/
	private Long id;
	/**会员用户id*/
    private Long memberId;
    /**收益账户*/
    private String account;
    /**收益前总金额*/
    private BigDecimal bfIncomeMoney;
    /**收益金额，每日收益*/
    private BigDecimal incomeMoney;
    /**收益后总金额*/
    private BigDecimal afIncomeMoney;
    /**创建时间*/
    private Date createTime;

    private String memberName;
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

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account == null ? null : account.trim();
    }

    public BigDecimal getIncomeMoney() {
        return incomeMoney;
    }

    public void setIncomeMoney(BigDecimal incomeMoney) {
        this.incomeMoney = incomeMoney;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

	public String getMemberName() {
		return memberName;
	}

	public void setMemberName(String memberName) {
		this.memberName = memberName;
	}

	public BigDecimal getBfIncomeMoney() {
		return bfIncomeMoney;
	}

	public void setBfIncomeMoney(BigDecimal bfIncomeMoney) {
		this.bfIncomeMoney = bfIncomeMoney;
	}

	public BigDecimal getAfIncomeMoney() {
		return afIncomeMoney;
	}

	public void setAfIncomeMoney(BigDecimal afIncomeMoney) {
		this.afIncomeMoney = afIncomeMoney;
	}
    
	
}
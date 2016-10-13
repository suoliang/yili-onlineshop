package com.aladingshop.alabao.model;

import java.math.BigDecimal;
import java.util.Date;
/**
 * @description 阿拉宝转入金额记录表
 * @author 索亮
 * @date 2015年9月8日上午10:02:21
 */
public class AlabaoShiftToRecord {
	/***/
    private Long id;

    private Long memberId;

    private String account;

    private String shiftToAccountType;

    private BigDecimal transferMoney;

    private Date createTime;
    
    private String serialNum;
    
    private String batchNum;
    
    private String memo;
    
	
	/**本次操作前的金额*/
	private BigDecimal beforeChangeMoney;
	/**本次操作后的金额*/
	private BigDecimal afterChangeMoney;
	
	
	
	
	public BigDecimal getBeforeChangeMoney() {
		return beforeChangeMoney;
	}
	public void setBeforeChangeMoney(BigDecimal beforeChangeMoney) {
		this.beforeChangeMoney = beforeChangeMoney;
	}
	public BigDecimal getAfterChangeMoney() {
		return afterChangeMoney;
	}
	public void setAfterChangeMoney(BigDecimal afterChangeMoney) {
		this.afterChangeMoney = afterChangeMoney;
	}
    

    public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

	public String getBatchNum() {
		return batchNum;
	}

	public void setBatchNum(String batchNum) {
		this.batchNum = batchNum;
	}

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

    public String getShiftToAccountType() {
        return shiftToAccountType;
    }

    public void setShiftToAccountType(String shiftToAccountType) {
        this.shiftToAccountType = shiftToAccountType == null ? null : shiftToAccountType.trim();
    }

    public BigDecimal getTransferMoney() {
        return transferMoney;
    }

    public void setTransferMoney(BigDecimal transferMoney) {
        this.transferMoney = transferMoney;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

	public String getSerialNum() {
		return serialNum;
	}

	public void setSerialNum(String serialNum) {
		this.serialNum = serialNum;
	}
}
package com.fushionbaby.member.model;

import java.math.BigDecimal;
import java.util.Date;
/**
 * 
 * @author King
 *
 */
public class Member {
    private Long id;

    private String loginName;

    private String password;
    
    private String imgPath;

    private Date createTime;

    private String telephone;

    private String email;

    private String bankcodelast;

    private String paymenttypelast;

    private BigDecimal epoints;

    private Date version;

    private String channelCode;

    private String disable;
    
    private Date updateTime;
    
    private String sourceCode;

	private String openId;

	/** 只在页面显示，来源名称 */
	private String sourceName;
	
	/** 用户注册的ip地址*/
	private String ipAddress;
	
	/** 钱包总金额*/
	private BigDecimal walletMoney;
	
	/**可用金额(比如用户下单后没有支付，下单使用的金额就会被冻结一段时间)*/
	private BigDecimal availableMoney;
	
	/** 接收人省 */
	private String province;
	/** 接收人市 */
	private String city;
	/** 接收人县区 */
	private String district;
	/** 接收人 详细地址*/
	private String address;
	/** 接收人姓名 */
	private String contactor;
	/** 接收人 电话*/
	private String mobile;
	
	private Long defaultAddressId;
	
	/** 注册类型（手机、邮箱、、）*/
	private String registerType;
	
	private String memberType;
	
	/**是否绑定如意消费卡  y 代表绑定过如意消费卡，n 代表未开通如意消费卡*/
	private String isBind;
	
	/**会员注册的设备标志*/
	private String signId;
	
	
	public String getSignId() {
		return signId;
	}

	public void setSignId(String signId) {
		this.signId = signId;
	}

	public String getIsBind() {
		return isBind;
	}

	public void setIsBind(String isBind) {
		this.isBind = isBind;
	}

	public String getSourceName() {
		return sourceName;
	}

	public void setSourceName(String sourceName) {
		this.sourceName = sourceName;
	}

	public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLoginName() {
        return loginName;
    }

    public void setLoginName(String loginName) {
        this.loginName = loginName == null ? null : loginName.trim();
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password == null ? null : password.trim();
    }

	public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone == null ? null : telephone.trim();
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email == null ? null : email.trim();
    }

    public String getBankcodelast() {
        return bankcodelast;
    }

    public void setBankcodelast(String bankcodelast) {
        this.bankcodelast = bankcodelast == null ? null : bankcodelast.trim();
    }

    public String getPaymenttypelast() {
        return paymenttypelast;
    }

    public void setPaymenttypelast(String paymenttypelast) {
        this.paymenttypelast = paymenttypelast == null ? null : paymenttypelast.trim();
    }

    public BigDecimal getEpoints() {
        return epoints == null ? BigDecimal.ZERO : epoints;
    }

    public void setEpoints(BigDecimal epoints) {
        this.epoints = epoints;
    }

    public Date getVersion() {
        return version;
    }

    public void setVersion(Date version) {
        this.version = version;
    }

    public String getChannelCode() {
        return channelCode;
    }

    public void setChannelCode(String channelCode) {
        this.channelCode = channelCode;
    }

    public String getDisable() {
        return disable;
    }

    public void setDisable(String disable) {
        this.disable = disable;
    }

	public String getImgPath() {
		return imgPath;
	}

	public void setImgPath(String imgPath) {
		this.imgPath = imgPath;
	}
	
	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public String getSourceCode() {
		return sourceCode;
	}

	public void setSourceCode(String sourceCode) {
		this.sourceCode = sourceCode;
	}

	public String getIpAddress() {
		return ipAddress;
	}

	public void setIpAddress(String ipAddress) {
		this.ipAddress = ipAddress;
	}

	public String getOpenId() {
		return openId;
	}

	public void setOpenId(String openId) {
		this.openId = openId;
	}

	public BigDecimal getWalletMoney() {
		return walletMoney;
	}

	public void setWalletMoney(BigDecimal walletMoney) {
		this.walletMoney = walletMoney;
	}

	public BigDecimal getAvailableMoney() {
		return availableMoney;
	}

	public void setAvailableMoney(BigDecimal availableMoney) {
		this.availableMoney = availableMoney;
	}

	public String getProvince() {
		return province;
	}

	public void setProvince(String province) {
		this.province = province;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getDistrict() {
		return district;
	}

	public void setDistrict(String district) {
		this.district = district;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getContactor() {
		return contactor;
	}

	public void setContactor(String contactor) {
		this.contactor = contactor;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public Long getDefaultAddressId() {
		return defaultAddressId;
	}

	public void setDefaultAddressId(Long defaultAddressId) {
		this.defaultAddressId = defaultAddressId;
	}

	public String getRegisterType() {
		return registerType;
	}

	public void setRegisterType(String registerType) {
		this.registerType = registerType;
	}

	public String getMemberType() {
		return memberType;
	}

	public void setMemberType(String memberType) {
		this.memberType = memberType;
	}

	

}
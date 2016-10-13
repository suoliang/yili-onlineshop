package com.fushionbaby.cms.dto;

import java.util.Date;
/**
 * 
 * @author King suoliang
 *
 */
public class LogCmsLoginDto {
    private Long id;

    private String loginName;

    private String loginStatus;

    private Date loginTime;
    
    private String ipAddress;
    
    private String storeCode;
    
    private String storeName;
    
    private String loginTimeFrom;
    
    private String loginTimeTo;
    

    
	public String getLoginTimeFrom() {
		return loginTimeFrom;
	}

	public void setLoginTimeFrom(String loginTimeFrom) {
		this.loginTimeFrom = loginTimeFrom;
	}

	public String getLoginTimeTo() {
		return loginTimeTo;
	}

	public void setLoginTimeTo(String loginTimeTo) {
		this.loginTimeTo = loginTimeTo;
	}

	public String getIpAddress() {
		return ipAddress;
	}

	public void setIpAddress(String ipAddress) {
		this.ipAddress = ipAddress;
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

    public String getLoginStatus() {
        return loginStatus;
    }

    public void setLoginStatus(String loginStatus) {
        this.loginStatus = loginStatus == null ? null : loginStatus.trim();
    }

    public Date getLoginTime() {
        return loginTime;
    }

    public void setLoginTime(Date loginTime) {
        this.loginTime = loginTime;
    }

	public String getStoreCode() {
		return storeCode;
	}

	public void setStoreCode(String storeCode) {
		this.storeCode = storeCode;
	}

	public String getStoreName() {
		return storeName;
	}

	public void setStoreName(String storeName) {
		this.storeName = storeName;
	}
    
    
}
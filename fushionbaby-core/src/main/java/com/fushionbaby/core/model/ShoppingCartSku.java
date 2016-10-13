package com.fushionbaby.core.model;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @author 张明亮
 * 购物车 行记录 购物车购物明细表
 */
public class ShoppingCartSku {
	
    private Long id;
    
    /** 会员id*/
    private Long memberId;
    
    /** 门店编号*/
    private String storeCode;
    
    /** 商品编码*/
    private String skuCode;
    
    /** 商品当前价格*/
    private BigDecimal currentPrice;
    
    /** 吊牌价*/
    //private BigDecimal retailPrice;
    
    /** 数量*/
    private Integer quantity;
    
    /** 商品名称*/
    private String skuName;
    
    /** 颜色*/
    private String color;
    
    /** 尺寸*/
    private String size;
    
    /** 品牌编码*/
    private String brandCode;
    
    /** 分类*/
    private String categoryCode;
    
    /** 是否赠品n否、y是*/
    private String isGift;
    
    /** 记录是否被选中购物车行y/n*/
    private String isSelected;
    
    /** 创建时间*/
    private Date createTime;
    
    /** 修改时间*/
    private Date updateTime;
    
    private Date version;
    
    /**行小计总价格*/
    private BigDecimal lineTotalPrice;
    
	
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    


	public String getBrandCode() {
        return brandCode;
    }

    public void setBrandCode(String brandCode) {
        this.brandCode = brandCode == null ? null : brandCode.trim();
    }

    public String getIsGift() {
        return isGift;
    }

    public void setIsGift(String isGift) {
        this.isGift = isGift;
    }


    public Integer getQuantity() {
		return (quantity == null ? 0 : quantity);
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public String getSkuCode() {
        return skuCode;
    }

    public void setSkuCode(String skuCode) {
        this.skuCode = skuCode == null ? null : skuCode.trim();
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color == null ? null : color.trim();
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size == null ? null : size.trim();
    }

    public Date getVersion() {
        return version;
    }

    public void setVersion(Date version) {
        this.version = version;
    }

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getCategoryCode() {
		return categoryCode;
	}

	public void setCategoryCode(String categoryCode) {
		this.categoryCode = categoryCode;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public String getIsSelected() {
		return isSelected;
	}

	public void setIsSelected(String isSelected) {
		this.isSelected = isSelected;
	}

	public String getSkuName() {
		return skuName;
	}

	public void setSkuName(String skuName) {
		this.skuName = skuName;
	}

	public BigDecimal getCurrentPrice() {
		return (currentPrice == null ? new BigDecimal(0) : currentPrice);
	}

	public void setCurrentPrice(BigDecimal currentPrice) {
		this.currentPrice = currentPrice;
	}

	
	
//	public BigDecimal getRetailPrice() {
//		return retailPrice;
//	}
//
//	public void setRetailPrice(BigDecimal retailPrice) {
//		this.retailPrice = retailPrice;
//	}

	public Long getMemberId() {
		return memberId;
	}

	public void setMemberId(Long memberId) {
		this.memberId = memberId;
	}
	
	public BigDecimal getLineTotalPrice() {
		return lineTotalPrice;
	}

	public void setLineTotalPrice(BigDecimal lineTotalPrice) {
		this.lineTotalPrice = lineTotalPrice;
	}
	public String getStoreCode() {
		return storeCode;
	}

	public void setStoreCode(String storeCode) {
		this.storeCode = storeCode;
	}

	@Override
	public String toString() {
		return "SoShoppingCartSku [id=" + id + ", createTime=" + createTime
				+ ", brandCode=" + brandCode + ", categoryCode=" + categoryCode
				+ ", isGift=" + isGift + 
				 ", quantity=" + quantity + ", skuCode=" + skuCode
				+ ", color=" + color + ", size=" + size + ", version="
				+ version + ", updateTime=" + updateTime + ", isSelected="
				+ isSelected + ", skuName=" + skuName
				+ ", currentPrice=" + currentPrice + ", memberId=" + memberId
				+ ",lineTotalPrice=" + lineTotalPrice + "]";
	}


}
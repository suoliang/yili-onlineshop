package com.fushionbaby.sysactivity.model;

public class LotteryPrize {
    private Long id;

    private Long level;

    private String name;

    private Integer remainPrize;

    private String imgPath;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getLevel() {
        return level;
    }

    public void setLevel(Long level) {
        this.level = level;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public Integer getRemainPrize() {
        return remainPrize;
    }

    public void setRemainPrize(Integer remainPrize) {
        this.remainPrize = remainPrize;
    }

    public String getImgPath() {
        return imgPath;
    }

    public void setImgPath(String imgPath) {
        this.imgPath = imgPath == null ? null : imgPath.trim();
    }
}
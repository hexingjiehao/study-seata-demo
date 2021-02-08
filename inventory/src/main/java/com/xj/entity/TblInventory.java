package com.xj.entity;

import java.io.Serializable;
import java.util.Date;

public class TblInventory implements Serializable {
    private Integer goodId;

    private Integer num;

    private Date updateTime;

    private static final long serialVersionUID = 1L;

    public Integer getGoodId() {
        return goodId;
    }

    public void setGoodId(Integer goodId) {
        this.goodId = goodId;
    }

    public Integer getNum() {
        return num;
    }

    public void setNum(Integer num) {
        this.num = num;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }
}
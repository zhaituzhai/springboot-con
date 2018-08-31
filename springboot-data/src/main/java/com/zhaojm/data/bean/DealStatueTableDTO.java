package com.zhaojm.data.bean;

import java.io.Serializable;

/**
 * @author 
 */
public class DealStatueTableDTO implements Serializable {
    private Integer id;

    private Integer type;

    private String dealStatues;

    private String dealName;

    private String deal;

    private static final long serialVersionUID = 1L;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getDealStatues() {
        return dealStatues;
    }

    public void setDealStatues(String dealStatues) {
        this.dealStatues = dealStatues;
    }

    public String getDealName() {
        return dealName;
    }

    public void setDealName(String dealName) {
        this.dealName = dealName;
    }

    public String getDeal() {
        return deal;
    }

    public void setDeal(String deal) {
        this.deal = deal;
    }
}
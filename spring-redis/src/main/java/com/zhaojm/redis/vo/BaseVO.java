package com.zhaojm.redis.vo;

import io.swagger.annotations.ApiModelProperty;

import java.util.LinkedHashMap;

public class BaseVO {
    @ApiModelProperty(value = "排序字段", example = "user_name")
    private String orderField;
    @ApiModelProperty(value = "排序方式", example = "desc")
    private String orderType;
    @ApiModelProperty(value = "排序方式", example = "{\"user_id\":\"desc\"}")
    private LinkedHashMap<String, String> orderMap;

    public String getOrderField() {
        return orderField;
    }

    public void setOrderField(String orderField) {
        this.orderField = orderField;
    }

    public String getOrderType() {
        return orderType;
    }

    public void setOrderType(String orderType) {
        this.orderType = orderType;
    }

    public LinkedHashMap<String, String> getOrderMap() {
        return orderMap;
    }

    public void setOrderMap(LinkedHashMap<String, String> orderMap) {
        this.orderMap = orderMap;
    }
}

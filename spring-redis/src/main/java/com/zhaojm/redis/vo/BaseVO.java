package com.zhaojm.redis.vo;

import io.swagger.annotations.ApiModelProperty;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

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

    public String getOrderMap() {
        StringBuilder sb = new StringBuilder();
        if(null != orderMap) {
            Set<Map.Entry<String, String>> entry = orderMap.entrySet();
            entry.forEach(k -> sb.append(k.getKey() + " " + k.getValue() + ","));
            return sb.substring(0,sb.length()-1);
        }else {
            return sb.toString();
        }
    }

    public void setOrderMap(LinkedHashMap<String, String> orderMap) {
        this.orderMap = orderMap;
    }
}

package com.zhaojm.redis.vo;

import io.swagger.annotations.ApiModelProperty;

public class UserQueryVO extends BaseVO {
    @ApiModelProperty(name = "姓名", example = "test")
    private String userName;
    @ApiModelProperty(name = "电话", example = "")
    private String userPhone;
    @ApiModelProperty(name = "类型", example = "3")
    private String userType;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserPhone() {
        return userPhone;
    }

    public void setUserPhone(String userPhone) {
        this.userPhone = userPhone;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }
}

package com.zhaojm.redis.dao;

import java.io.Serializable;
import java.util.Date;

/**
 * @Author
 */
public class UserAccountDTO implements Serializable {
    //@ApiModelProperty("用户主键")
    private Integer userId;

    //@ApiModelProperty("用户名称（可做登陆）")
    private String userName;

    //@ApiModelProperty("用户手机号（登陆）")
    private String userPhone;

    //@ApiModelProperty("密码")
    private String password;

    //@ApiModelProperty("用户类型（1：管理员 2:医生 3：病人 4：普通用户")
    private String userType;

    //@ApiModelProperty("账户类型（开启/关闭）")
    private Integer accountType;

    //@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date creatTime;

    private static final long serialVersionUID = 1L;

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    public Integer getAccountType() {
        return accountType;
    }

    public void setAccountType(Integer accountType) {
        this.accountType = accountType;
    }

    public Date getCreatTime() {
        return creatTime;
    }

    public void setCreatTime(Date creatTime) {
        this.creatTime = creatTime;
    }

    @Override
    public String toString() {
        return String.format("名字：%s;電話：%s;密碼：%s",this.userName,this.userPhone,this.password);
    }
}
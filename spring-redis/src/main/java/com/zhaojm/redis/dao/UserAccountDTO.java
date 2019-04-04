package com.zhaojm.redis.dao;

import cn.afterturn.easypoi.excel.annotation.Excel;
import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.metadata.BaseRowModel;
import com.zhaojm.redis.annotation.ExcelField;
import com.zhaojm.redis.annotation.ExcelSheet;

import java.io.Serializable;
import java.util.Date;

/**
 * @Author
 */
@ExcelSheet(name = "用户信息")
public class UserAccountDTO  extends BaseRowModel implements Serializable {
    //@ApiModelProperty("用户主键")
    @ExcelProperty(value="主键",index=0)
    @Excel(name = "主键", orderNum = "0")
    @ExcelField(index = 1, name = "主键")
    private Integer userId;

    //@ApiModelProperty("用户名称（可做登陆）")
    @Excel(name = "姓名", orderNum = "1")
    @ExcelProperty(value="姓名",index=1)
    @ExcelField(index = 2, name = "姓名")
    private String userName;

    //@ApiModelProperty("用户手机号（登陆）")
    @Excel(name = "手机号", orderNum = "2")
    @ExcelProperty(value="手机号",index=2)
    @ExcelField(index = 3, name = "手机号")
    private String userPhone;

    //@ApiModelProperty("密码")
    @Excel(name = "密码", orderNum = "3")
    @ExcelProperty(value="密码",index=3)
    @ExcelField(index = 4, name = "密码")
    private String password;

    //@ApiModelProperty("用户类型（1：管理员 2:医生 3：病人 4：普通用户")
    @Excel(name = "用户类型", orderNum = "4")
    @ExcelProperty(value="用户类型",index=4)
    @ExcelField(index = 5, name = "用户类型")
    private String userType;

    //@ApiModelProperty("账户类型（开启/关闭）")
    @Excel(name = "账户类型", orderNum = "5")
    @ExcelProperty(value="账户类型",index=5)
    @ExcelField(index = 6, name = "账户类型")
    private Integer accountType;

    //@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @Excel(name = "創建時間", orderNum = "6" ,format = "yyyy-MM-dd")
    @ExcelProperty(value="創建時間",index=6,format = "yyyy-MM-dd")
    @ExcelField(index = 7, name = "創建時間",format = "yyyy-MM-dd")
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
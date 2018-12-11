package com.hy.model;

import java.util.Date;

/**
 * @Auther: 钱敏杰
 * @Date: 2018/12/3 9:26
 * @Description:BO账号表
 */
public class ReportAccount {

    //主键
    private Integer id;
    //账号id
    private String accountid;
    //账号类别
    private String type;
    //账号密码，不支持取出，只能做保存
    private String password;
    //描述
    private String description;
    //创建人
    private String creater;
    //创建时间
    private Date created;
    //修改人
    private String modifier;
    //修改时间
    private Date modified;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getAccountid() {
        return accountid;
    }

    public void setAccountid(String accountid) {
        this.accountid = accountid;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getCreater() {
        return creater;
    }

    public void setCreater(String creater) {
        this.creater = creater;
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    public String getModifier() {
        return modifier;
    }

    public void setModifier(String modifier) {
        this.modifier = modifier;
    }

    public Date getModified() {
        return modified;
    }

    public void setModified(Date modified) {
        this.modified = modified;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}

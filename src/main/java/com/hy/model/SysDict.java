package com.hy.model;

import java.util.Date;

/**
 * @Auther: 沈超宇
 * @Date: 2018/12/27 14:09
 * @Description:基础信息数据字典表
 */
public class SysDict {
    private Integer id;
    private String code;
    private String name;
    private Integer pid;
    private String pidname;
    private String description;
    private Integer deleteFlag;
    private String creater;
    private Date created;
    private String modifier;
    private Date modified;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getPid() {
        return pid;
    }

    public void setPid(Integer pid) {
        this.pid = pid;
    }

    public Integer getDeleteFlag() {
        return deleteFlag;
    }

    public void setDeleteFlag(Integer deleteFlag) {
        this.deleteFlag = deleteFlag;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPidname() {
        return pidname;
    }

    public void setPidname(String pidname) {
        this.pidname = pidname;
    }
}

package com.hy.dto;

import java.util.List;

/**
 * @Auther: 沈超宇
 * @Date: 2018/12/27 14:09
 * @Description:基础信息数据字典表Dto
 */
public class SysDictDto {
    private Integer id;
    private String code;
    private String name;
    private Integer pid;
    private String pidname;
    private String description;

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

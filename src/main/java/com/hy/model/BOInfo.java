package com.hy.model;

import java.util.Date;

/**
 * @Auther: 钱敏杰
 * @Date: 2018/12/3 9:32
 * @Description:报表信息表
 */
public class BOInfo {

    //主键
    private Integer id;
    //报表名称
    private String name;
    //报表id
    private String reportid;
    //目录id
    private Integer directoryid;
    //类别
    private String type;
    //描述
    private String description;
    //url链接地址（目前未使用）
    private String url;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getReportid() {
        return reportid;
    }

    public void setReportid(String reportid) {
        this.reportid = reportid;
    }

    public Integer getDirectoryid() {
        return directoryid;
    }

    public void setDirectoryid(Integer directoryid) {
        this.directoryid = directoryid;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
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

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}

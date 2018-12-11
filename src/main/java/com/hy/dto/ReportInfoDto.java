package com.hy.dto;

import java.util.Date;

/**
 * @Auther: 钱敏杰
 * @Date: 2018/12/3 9:32
 * @Description:报表信息表
 */
public class ReportInfoDto {

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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}

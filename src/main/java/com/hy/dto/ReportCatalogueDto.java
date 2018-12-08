package com.hy.dto;

import java.util.Date;
import java.util.List;

/**
 * @Auther: 钱敏杰
 * @Date: 2018/12/7 13:35
 * @Description:报表目录表
 */
public class ReportCatalogueDto {

    //主键
    private Integer id;
    //目录名称
    private String name;
    //父id
    private Integer pid;
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
    //存放子目录
    private List<ReportCatalogueDto> dtos;
    //存放目录下报表信息
    private List<ReportInfoDto> infos;

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

    public List<ReportCatalogueDto> getDtos() {
        return dtos;
    }

    public void setDtos(List<ReportCatalogueDto> dtos) {
        this.dtos = dtos;
    }

    public List<ReportInfoDto> getInfos() {
        return infos;
    }

    public void setInfos(List<ReportInfoDto> infos) {
        this.infos = infos;
    }
}

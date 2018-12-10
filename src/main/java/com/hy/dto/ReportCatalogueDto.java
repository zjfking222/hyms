package com.hy.dto;

import java.util.List;

/**
 * @Auther: 钱敏杰
 * @Date: 2018/12/7 13:35
 * @Description:报表目录表和报表信息表整合成一个目录表
 */
public class ReportCatalogueDto {

    //主键
    private Integer id;
    //目录名称
    private String name;
    //ReportCatalogue表的pid，也是ReportInfo表的directoryid
    private Integer pid;
    //描述
    private String description;
    //报表id
    private String reportid;
    //报表类别
    private String type;
    //存放子目录
    private List<ReportCatalogueDto> items;
    //是否选中：true 选中，false 不选中
    private boolean checked;
    //是否展开当前目录，默认打开：true 打开，false 不打开
    private boolean expanded = true;

    public ReportCatalogueDto(){}

    public ReportCatalogueDto(Integer id, String name, Integer pid, String description, String reportid, String type, boolean checked, boolean expanded){
        this.id = id;
        this.name = name;
        this.pid = pid;
        this.description = description;
        this.reportid = reportid;
        this.type = type;
        this.checked = checked;
        this.expanded = expanded;
    }

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

    public String getReportid() {
        return reportid;
    }

    public void setReportid(String reportid) {
        this.reportid = reportid;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public List<ReportCatalogueDto> getItems() {
        return items;
    }

    public void setItems(List<ReportCatalogueDto> items) {
        this.items = items;
    }

    public boolean isChecked() {
        return checked;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
    }

    public boolean isExpanded() {
        return expanded;
    }

    public void setExpanded(boolean expanded) {
        this.expanded = expanded;
    }
}

package com.hy.dto;

import java.util.List;

public class SysRolePermissionTreeDto {

    private int id;
    private String text;
    private boolean checked;
    private int pid;
    private boolean expanded;
    private List<SysRolePermissionTreeDto> items;


    public SysRolePermissionTreeDto(int id, String text, boolean checked, int pid, boolean expanded, List<SysRolePermissionTreeDto> items) {
        this.id = id;
        this.text = text;
        this.checked = checked;
        this.pid = pid;
        this.expanded = expanded;
        this.items = items;
    }

    public SysRolePermissionTreeDto() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public boolean isExpanded() {
        return expanded;
    }

    public void setExpanded(boolean expanded) {
        this.expanded = expanded;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public boolean isChecked() {
        return checked;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
    }

    public int getPid() {
        return pid;
    }

    public void setPid(int pid) {
        this.pid = pid;
    }

    public List<SysRolePermissionTreeDto> getItems() {
        return items;
    }

    public void setItems(List<SysRolePermissionTreeDto> items) {
        this.items = items;
    }
}

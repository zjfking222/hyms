package com.hy.dto;

import java.util.List;

public class SysRolePermissionTreeDto {

    private int id;
    private String text;
    private boolean checked;
    private List<SysRolePermissionDto> items;


    public SysRolePermissionTreeDto(int id, String text, boolean checked, List<SysRolePermissionDto> items) {
        this.id = id;
        this.text = text;
        this.checked = checked;
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

    public List<SysRolePermissionDto> getItems() {
        return items;
    }

    public void setItems(List<SysRolePermissionDto> items) {
        this.items = items;
    }
}

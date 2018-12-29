package com.hy.dto;

/**
 * @Author 沈超宇
 * @Description 报表角色表DTO
 * @Date 2018/12/13 14:00
 **/
public class BORoleDto {
    private int id;
    private String name;
    private String description;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}

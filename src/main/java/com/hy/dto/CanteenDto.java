package com.hy.dto;

public class CanteenDto {
    private Integer id;
    private String name;
    private String type;
    private String state;

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}

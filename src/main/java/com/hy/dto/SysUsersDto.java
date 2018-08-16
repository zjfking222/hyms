package com.hy.dto;

public class SysUsersDto {
    private Integer id;

    private String name;

    private Boolean enable;

    private String oaloginid;

    private Integer oauserid;

    public SysUsersDto(int id, String oaloginid, String name) {
        this.id = id;
        this.oaloginid = oaloginid;
        this.name = name;
    }

    public SysUsersDto() {

    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }


    public Boolean getEnable() {
        return enable;
    }

    public void setEnable(Boolean enable) {
        this.enable = enable;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getOaloginid() {
        return oaloginid;
    }

    public void setOaloginid(String oaloginid) {
        this.oaloginid = oaloginid;
    }

    public Integer getOauserid() {
        return oauserid;
    }

    public void setOauserid(Integer oauserid) {
        this.oauserid = oauserid;
    }
}




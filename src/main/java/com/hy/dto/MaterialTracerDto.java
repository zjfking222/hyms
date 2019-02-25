package com.hy.dto;

/**
 * @Auther: 沈超宇
 * @Date: 2019/1/22 10:25
 * @Description:
 */
public class MaterialTracerDto {
    private Integer id;
    private Integer sid;
    private String tracernum;
    private String tracername;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getSid() {
        return sid;
    }

    public void setSid(Integer sid) {
        this.sid = sid;
    }

    public String getTracernum() {
        return tracernum;
    }

    public void setTracernum(String tracernum) {
        this.tracernum = tracernum;
    }

    public String getTracername() {
        return tracername;
    }

    public void setTracername(String tracername) {
        this.tracername = tracername;
    }
}

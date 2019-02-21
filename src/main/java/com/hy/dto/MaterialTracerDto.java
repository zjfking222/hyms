package com.hy.dto;

/**
 * @Auther: 沈超宇
 * @Date: 2019/1/22 10:25
 * @Description:
 */
public class MaterialTracerDto {
    private int id;
    private int sid;
    private String tracernum;
    private String tracername;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getSid() {
        return sid;
    }

    public void setSid(int sid) {
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

package com.hy.dto;

import java.util.Date;

public class MmReceiptAgendaViewDto {

    private String name;
    private Date date;
    private boolean attend;
    private int mid;
    private int rid;
    private int aid;
    private int id;
    private boolean state;

    public MmReceiptAgendaViewDto(String name, Date date, boolean attend, int mid, int rid, int aid, int id, boolean state) {
        this.name = name;
        this.date = date;
        this.attend = attend;
        this.mid = mid;
        this.rid = rid;
        this.aid = aid;
        this.id = id;
        this.state = state;
    }

    public MmReceiptAgendaViewDto() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public boolean getAttend() {
        return attend;
    }

    public void setAttend(boolean attend) {
        this.attend = attend;
    }

    public int getMid() {
        return mid;
    }

    public void setMid(int mid) {
        this.mid = mid;
    }

    public int getRid() {
        return rid;
    }

    public void setRid(int rid) {
        this.rid = rid;
    }

    public int getAid() {
        return aid;
    }

    public void setAid(int aid) {
        this.aid = aid;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }


    public boolean getState() {
        return state;
    }

    public void setState(boolean state) {
        this.state = state;
    }
}

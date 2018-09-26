package com.hy.dto;

public class MmReceiptStayDto {
    private int id;
    private int rid;
    private String date;
    private int single;

    public MmReceiptStayDto(int rid, String date, int single) {
        this.rid = rid;
        this.date = date;
        this.single = single;
    }

    public MmReceiptStayDto() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getRid() {
        return rid;
    }

    public void setRid(int rid) {
        this.rid = rid;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getSingle() {
        return single;
    }

    public void setSingle(int single) {
        this.single = single;
    }
}

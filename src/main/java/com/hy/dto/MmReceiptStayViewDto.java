package com.hy.dto;


public class MmReceiptStayViewDto {

    private int id;
    private int rid;
    private String name;
    private String date;
    private int single;
    private int standard;
    private int suite;

    public MmReceiptStayViewDto() {
    }

    public MmReceiptStayViewDto(int rid, String name, String date, int single, int standard, int suite) {
        this.rid = rid;
        this.name = name;
        this.date = date;
        this.single = single;
        this.standard = standard;
        this.suite = suite;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public int getStandard() {
        return standard;
    }

    public void setStandard(int standard) {
        this.standard = standard;
    }

    public int getSuite() {
        return suite;
    }

    public void setSuite(int suite) {
        this.suite = suite;
    }
}

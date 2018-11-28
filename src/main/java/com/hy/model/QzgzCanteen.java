package com.hy.model;

public class QzgzCanteen {

    private Integer id;
    private String date;
    private String name;
    private String type;
    private Integer meal;
    private Float price;
    private int zan;
    private String creater;
    private String created;
    private String modifier;
    private String modified;
//    private String state;

    public QzgzCanteen(){
    }

    public QzgzCanteen(String date, String name, String type, Integer meal, Float price, String creater, String modifier) {
        this.date = date;
        this.name = name;
        this.type = type;
        this.meal = meal;
        this.price = price;
        this.creater = creater;
        this.modifier = modifier;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
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

    public Integer getMeal() {
        return meal;
    }

    public void setMeal(Integer meal) {
        this.meal = meal;
    }

    public Float getPrice() {
        return price;
    }

    public void setPrice(Float price) {
        this.price = price;
    }

    public int getZan() {
        return zan;
    }

    public void setZan(int zan) {
        this.zan = zan;
    }

    public String getCreater() {
        return creater;
    }

    public void setCreater(String creater) {
        this.creater = creater;
    }

    public String getCreated() {
        return created;
    }

    public void setCreated(String created) {
        this.created = created;
    }

    public String getModifier() {
        return modifier;
    }

    public void setModifier(String modifier) {
        this.modifier = modifier;
    }

    public String getModified() {
        return modified;
    }

    public void setModified(String modified) {
        this.modified = modified;
    }

//    public String getState() {
//        return state;
//    }
//
//    public void setState(String state) {
//        this.state = state;
//    }
}

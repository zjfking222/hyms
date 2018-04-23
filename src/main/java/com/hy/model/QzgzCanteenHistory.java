package com.hy.model;

import java.sql.Date;

public class QzgzCanteenHistory {

    private int id;
    private Date date;
    private int canteen_id;
    private int creater;
    private Date created;
    private int modifier;
    private Date modified;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public int getCanteen_id() {
        return canteen_id;
    }

    public void setCanteen_id(int canteen_id) {
        this.canteen_id = canteen_id;
    }

    public int getCreater() {
        return creater;
    }

    public void setCreater(int creater) {
        this.creater = creater;
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    public int getModifier() {
        return modifier;
    }

    public void setModifier(int modifier) {
        this.modifier = modifier;
    }

    public Date getModified() {
        return modified;
    }

    public void setModified(Date modified) {
        this.modified = modified;
    }
}

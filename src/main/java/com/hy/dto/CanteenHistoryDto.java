package com.hy.dto;

public class CanteenHistoryDto {

    private int id;
    private int canteen_id;
    private CanteenDto canteen;
    private int meal;

    public int getCanteen_id() {
        return canteen_id;
    }

    public void setCanteen_id(int canteen_id) {
        this.canteen_id = canteen_id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public CanteenDto getCanteen() {
        return canteen;
    }

    public void setCanteen(CanteenDto canteen) {
        this.canteen = canteen;
    }

    public int getMeal() {
        return meal;
    }

    public void setMeal(int meal) {
        this.meal = meal;
    }
}

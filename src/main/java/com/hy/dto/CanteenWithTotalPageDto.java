package com.hy.dto;

import java.util.List;

public class CanteenWithTotalPageDto {
    private List<CanteenDto> canteens;
    private Integer TotalPage;

    public CanteenWithTotalPageDto(List<CanteenDto> canteens, Integer totalPage) {
        this.canteens = canteens;
        TotalPage = totalPage;
    }

    public List<CanteenDto> getCanteens() {
        return canteens;
    }

    public void setCanteens(List<CanteenDto> canteens) {
        this.canteens = canteens;
    }

    public Integer getTotalPage() {
        return TotalPage;
    }

    public void setTotalPage(Integer totalPage) {
        TotalPage = totalPage;
    }
}

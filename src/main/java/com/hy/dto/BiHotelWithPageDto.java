package com.hy.dto;

import java.util.List;

public class BiHotelWithPageDto {
    private List<BiHotelDto> hotels;
    private int countPage;

    public BiHotelWithPageDto(List<BiHotelDto> hotels, int countPage) {
        this.hotels = hotels;
        this.countPage = countPage;
    }

    public List<BiHotelDto> getHotels() {
        return hotels;
    }

    public void setHotels(List<BiHotelDto> hotels) {
        this.hotels = hotels;
    }

    public void setCountPage(int countPage) {
        this.countPage = countPage;
    }

    public int getCountPage() {

        return countPage;
    }
}

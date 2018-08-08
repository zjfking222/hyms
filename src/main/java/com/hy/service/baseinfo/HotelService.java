package com.hy.service.baseinfo;

import com.hy.dto.BiHotelDto;
import com.hy.dto.BiHotelWithPageDto;

import java.util.List;

public interface HotelService {
    List<BiHotelDto> getHotel();
    int addHotel(BiHotelDto biHotelDto);
    BiHotelWithPageDto getListHotel(int startLine,int pageSize);
    boolean updateHotel(BiHotelDto biHotelDto);
    boolean deleteHotel(BiHotelDto biHotelDto);
    List<BiHotelDto> getAllHotel(int pageNum, int pageSize, String value, String sort, String dir);
    int getTotalHotel(String value);
}

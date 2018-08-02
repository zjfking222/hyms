package com.hy.mapper.ms;

import com.hy.model.BiHotel;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BiHotelMapper {
    List<BiHotel> selectBiHotel();
}

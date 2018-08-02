package com.hy.service.baseinfo;

import com.hy.dto.BiHotelDto;
import com.hy.mapper.ms.BiHotelMapper;
import com.hy.utils.DTOUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HotelServiceImpl implements HotelService {
    @Autowired
    private BiHotelMapper biHotelMapper;
    @Override
    public List<BiHotelDto> getHotel() {
        return DTOUtil.populateList(biHotelMapper.selectBiHotel(), BiHotelDto.class);
    }
}

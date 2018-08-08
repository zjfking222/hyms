package com.hy.service.baseinfo;

import com.github.pagehelper.PageHelper;
import com.hy.common.SecurityHelp;
import com.hy.dto.BiHotelDto;
import com.hy.dto.BiHotelWithPageDto;
import com.hy.mapper.ms.BiHotelMapper;
import com.hy.model.BiHotel;
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

    @Override
    public int addHotel(BiHotelDto biHotelDto) {
        BiHotel hotel = DTOUtil.populate(biHotelDto, BiHotel.class);
        hotel.setCreater(SecurityHelp.getUserId());//创建人
        hotel.setModifier(SecurityHelp.getUserId());//最后修改人
        biHotelMapper.insertBiHotel(hotel);
        return hotel.getId();
    }

    @Override
    public BiHotelWithPageDto getListHotel(int page, int pageSize) {
        int startLine=(page-1)*pageSize;
        List<BiHotelDto> biHotelDto =  DTOUtil.populateList(biHotelMapper.selectListBiHotel(startLine,pageSize), BiHotelDto.class);
        return new BiHotelWithPageDto(biHotelDto, biHotelMapper.selectCountBiHotel());
    }

    @Override
    public boolean updateHotel(BiHotelDto biHotelDto){
        BiHotel update=DTOUtil.populate(biHotelDto,BiHotel.class);
        update.setModifier(SecurityHelp.getUserId());
        return biHotelMapper.updateBiHotel(update)==1;
    }

    @Override
    public boolean deleteHotel(BiHotelDto biHotelDto){
        BiHotel delete=DTOUtil.populate(biHotelDto,BiHotel.class);
        delete.setModifier(SecurityHelp.getUserId());
        return biHotelMapper.deleteBiHotel(delete)==1;
    }

    @Override
    public List<BiHotelDto> getAllHotel(int pageNum, int pageSize, String value, String sort, String dir) {
        PageHelper.startPage(pageNum, pageSize);
        List<BiHotel> hotels=biHotelMapper.selectAllBiHotel(value, sort, dir);
        return DTOUtil.populateList(hotels,BiHotelDto.class);
    }

    @Override
    public int getTotalHotel(String value) {
        return biHotelMapper.selectTotalBiHotel(value);
    }
}

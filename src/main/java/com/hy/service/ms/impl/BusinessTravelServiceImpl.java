package com.hy.service.ms.impl;

import com.hy.dto.BusinessTravelDto;
import com.hy.mapper.ms.QzgzBusinessTravelMapper;
import com.hy.model.QzgzBusinessTravel;
import com.hy.service.ms.BusinessTravelService;
import com.hy.utils.DTOUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class BusinessTravelServiceImpl implements BusinessTravelService{
    @Autowired
    private QzgzBusinessTravelMapper businessTravelMapper;
    @Override
    public List<BusinessTravelDto> getBusinessTravel() {
        return DTOUtil.populateList(businessTravelMapper.selectBusinessTravel(),
                        new ArrayList<BusinessTravelDto>(), BusinessTravelDto.class);
        }

    @Override
    public boolean addBusinessTravel(String title, String img) {
        QzgzBusinessTravel businessTravel = new QzgzBusinessTravel();
        businessTravel.setTitle(title);
        businessTravel.setImg(img);
        return businessTravelMapper.insertBusinessTravel(businessTravel) == 1;
    }

    @Override
    public boolean delBusinessTravel(int id) {
        return businessTravelMapper.deleteBusinessTravel(id) == 1;
    }
}

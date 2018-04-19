package com.hy.service.ms.impl;

import com.hy.dto.BusinessTravelDto;
import com.hy.mapper.ms.BusinessTravelMapper;
import com.hy.service.ms.BusinessTravelService;
import com.hy.utils.DTOUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class BusinessTravelServiceImpl implements BusinessTravelService{
    @Autowired
    private BusinessTravelMapper businessTravelMapper;
    @Override
    public List<BusinessTravelDto> getBusinessTravel() {
        return DTOUtil.populateList(businessTravelMapper.selectBusinessTravel(),
                        new ArrayList<BusinessTravelDto>(), BusinessTravelDto.class);
        }
}

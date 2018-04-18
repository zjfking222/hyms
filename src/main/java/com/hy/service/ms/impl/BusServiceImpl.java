package com.hy.service.ms.impl;

import com.hy.dto.BusDto;
import com.hy.mapper.ms.BusMapper;
import com.hy.model.Bus;
import com.hy.service.ms.BusService;
import com.hy.utils.DTOUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class BusServiceImpl implements BusService{

    @Autowired
    private BusMapper busMapper;
    @Override
    public List<BusDto> getBusInfo() {
        List<Bus> buses= busMapper.selectBus();
        return DTOUtil.populateList(buses, new ArrayList<BusDto>(), BusDto.class);
    }
}

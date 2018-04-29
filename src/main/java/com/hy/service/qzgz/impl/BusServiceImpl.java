package com.hy.service.qzgz.impl;

import com.hy.dto.BusDto;
import com.hy.mapper.ms.QzgzBusMapper;
import com.hy.model.QzgzBus;
import com.hy.service.qzgz.BusService;
import com.hy.utils.DTOUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class BusServiceImpl implements BusService{

    @Autowired
    private QzgzBusMapper busMapper;
    @Override
    public List<BusDto> getBusInfo() {
        List<QzgzBus> buses= busMapper.selectBus();
        return DTOUtil.populateList(buses, new ArrayList<BusDto>(), BusDto.class);
    }
}

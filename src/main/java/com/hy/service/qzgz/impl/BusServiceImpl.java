package com.hy.service.qzgz.impl;

import com.hy.common.SecurityUtil;
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

    @Override
    public boolean setBusInfo(BusDto busDto) {
        QzgzBus bus = DTOUtil.populate(busDto,QzgzBus.class);
        bus.setModifier(SecurityUtil.getLoginid());
        return busMapper.updateBus(bus) == 1;
    }

    @Override
    public boolean addBusInfo(BusDto busDto) {
        QzgzBus bus = DTOUtil.populate(busDto,QzgzBus.class);
        bus.setCreater(SecurityUtil.getLoginid());
        bus.setModifier(SecurityUtil.getLoginid());
        return busMapper.insertBus(bus) == 1;
    }

    @Override
    public boolean delBusInfo(int id) {
        QzgzBus bus = new QzgzBus();
        bus.setId(id);
        return busMapper.deleteBus(bus) == 1;
    }
}

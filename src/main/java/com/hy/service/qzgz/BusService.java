package com.hy.service.qzgz;

import com.hy.dto.BusDto;

import java.util.List;

public interface BusService {
    /**
     * 获取班车信息
     * @return List<BusDto>
     */
    List<BusDto> getBusInfo();
    boolean setBusInfo(BusDto busDto);
    boolean addBusInfo(BusDto busDto);
    boolean delBusInfo(int id);
}

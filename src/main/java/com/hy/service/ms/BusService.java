package com.hy.service.ms;

import com.hy.dto.BusDto;

import java.util.List;

public interface BusService {
    /**
     * 获取班车信息
     * @return List<BusDto>
     */
    List<BusDto> getBusInfo();
}

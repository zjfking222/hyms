package com.hy.service.mm;

import com.hy.dto.MmBusDto;
import com.hy.dto.MmBusInfoDto;

import java.util.List;

public interface MeetingBusService {
    List<MmBusDto> getBus();
    int addBus(MmBusDto mmBusDto);
    boolean updateBus(MmBusDto mmBusDto);
    boolean deleteBus(MmBusDto mmBusDto);
    List<MmBusDto> getAllBus(String filters, int pageNum, int pageSize, String value, String sort, String dir,int mid);
    int getCountBus(String filters, String value,int mid);
    List<MmBusInfoDto> getInfoBus(int mid);

}

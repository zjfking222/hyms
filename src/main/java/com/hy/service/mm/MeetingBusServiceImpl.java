package com.hy.service.mm;

import com.github.pagehelper.PageHelper;
import com.hy.common.SecurityUtil;
import com.hy.dto.MmBusDto;
import com.hy.dto.MmBusInfoDto;
import com.hy.mapper.ms.MmBusMapper;
import com.hy.model.MmBus;
import com.hy.utils.DTOUtil;
import com.hy.utils.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.IntStream;

@Service
public class MeetingBusServiceImpl implements MeetingBusService {
    @Autowired
    private MmBusMapper mmBusMapper;

    @Override
    public List<MmBusDto> getBus() {
        return DTOUtil.populateList(mmBusMapper.selectMmBus(), MmBusDto.class);
    }

    @Override
    public int addBus(MmBusDto mmBusDto) {
        MmBus mmBus = DTOUtil.populate(mmBusDto, MmBus.class);
        mmBus.setFirsttime(DateUtil.translate(mmBusDto.getFirsttime()));
        mmBus.setEndtime(DateUtil.translate(mmBusDto.getEndtime()));
        mmBus.setCreater(SecurityUtil.getLoginid());
        mmBus.setModifier(SecurityUtil.getLoginid());
        mmBusMapper.insertMmBus(mmBus);
        return mmBus.getId();
    }

    @Override
    public boolean updateBus(MmBusDto mmBusDto) {
        MmBus update = DTOUtil.populate(mmBusDto, MmBus.class);
        update.setFirsttime(DateUtil.translate(mmBusDto.getFirsttime()));
        update.setEndtime(DateUtil.translate(mmBusDto.getEndtime()));
        update.setModifier(SecurityUtil.getLoginid());
        return mmBusMapper.updateMmBus(update) == 1;
    }

    @Override
    public boolean deleteBus(MmBusDto mmBusDto) {
        MmBus delete = DTOUtil.populate(mmBusDto, MmBus.class);
        delete.setModifier(SecurityUtil.getLoginid());
        return mmBusMapper.deleteMmBus(delete) == 1;
    }

    @Override
    public List<MmBusDto> getAllBus(String filters, int pageNum, int pageSize, String value, String sort, String dir, int mid) {
        PageHelper.startPage(pageNum, pageSize);
        List<MmBus> mmBuses = mmBusMapper.selectAllMmBus(filters, value, sort, dir, mid);
        List<MmBusDto> mmBusDtos = DTOUtil.populateList(mmBuses, MmBusDto.class);

        IntStream.range(0, mmBuses.size()).forEach(i -> {
            MmBusDto md = mmBusDtos.get(i);
            MmBus mm = mmBuses.get(i);
            if (mm.getFirsttime() == null) {

            } else {
                md.setFirsttime(DateUtil.translate(mm.getFirsttime()));
            }
            if (mm.getEndtime() == null) {

            } else {
                md.setEndtime(DateUtil.translate(mm.getEndtime()));
            }
        });
        return mmBusDtos;
    }

    @Override
    public int getCountBus(String filters, String value, int mid) {
        return mmBusMapper.selectCountMmBus(filters, value, mid);
    }

    @Override
    public List<MmBusInfoDto> getInfoBus(int mid){
        List<MmBus> mmBuses = mmBusMapper.selectInfoMmBus(mid);
        return DTOUtil.populateList(mmBuses,MmBusInfoDto.class);
    }
}

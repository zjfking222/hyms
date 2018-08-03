package com.hy.service.mm;

import com.hy.common.SecurityHelp;
import com.hy.dto.MmReceiptDineFetchDto;
import com.hy.dto.MmReceiptDinesDto;
import com.hy.mapper.ms.MmReceiptDinesMapper;
import com.hy.model.MmMeeting;
import com.hy.model.MmReceiptDines;
import com.hy.utils.DTOUtil;
import com.hy.utils.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

@Service
public class ReceiptDinesServiceImpl implements ReceiptDinesService {

    @Autowired
    private MmReceiptDinesMapper mmReceiptDinesMapper;
    @Autowired
    private MeetingService meetingService;

    @Override
    public List<MmReceiptDinesDto> getReceiptDines(int rid) {
        MmMeeting mmMeeting = meetingService.getMeetingByRid(rid);

        List<MmReceiptDines> rds = mmReceiptDinesMapper.selectReceiptDines(rid);
        if(rds.size() != 0){
            List<MmReceiptDinesDto> rdds = DTOUtil.populateList(rds, MmReceiptDinesDto.class);
            for (int i = 0 ; i < rds.size(); i++){
                rdds.get(i).setDate(DateUtil.breviary(rds.get(i).getDate()));
            }
            return rdds;
        }
        else {
            List<String> days = DateUtil.getDays(mmMeeting.getBegindate(),mmMeeting.getEnddate());
            List<MmReceiptDinesDto> dinesDtos = new ArrayList<>();
            if(days != null){
                for (String day: days){
                    dinesDtos.add(new MmReceiptDinesDto(rid, day,0,0,0,0));
                }
            }
            return dinesDtos;
        }
    }

    @Override
    public boolean setReceiptDines(List<MmReceiptDineFetchDto> mmReceiptDinesDtos) {
        List<MmReceiptDines> mmReceiptDines = DTOUtil.populateList(mmReceiptDinesDtos, MmReceiptDines.class);
        IntStream.range(0, mmReceiptDines.size()).forEach(i -> {
            mmReceiptDines.get(i).setModifier(SecurityHelp.getUserId());
            mmReceiptDines.get(i).setDate(DateUtil.translate(mmReceiptDinesDtos.get(i).getDate()));
        });
        return mmReceiptDinesMapper.updateReceiptDines(mmReceiptDines) == mmReceiptDines.size();
    }

    @Override
    public boolean addReceiptDines(List<MmReceiptDineFetchDto> mmReceiptDineFetchDtos) {

        List<MmReceiptDines> mmReceiptDines = DTOUtil.populateList(mmReceiptDineFetchDtos, MmReceiptDines.class);

        IntStream.range(0, mmReceiptDines.size()).forEach(i -> {
            mmReceiptDines.get(i).setModifier(SecurityHelp.getUserId());
            mmReceiptDines.get(i).setCreater(SecurityHelp.getUserId());
            mmReceiptDines.get(i).setDomain(SecurityHelp.getDepartmentId());
            mmReceiptDines.get(i).setDate(DateUtil.breviary(mmReceiptDineFetchDtos.get(i).getDate()));
        });

        return mmReceiptDinesMapper.insertReceiptDines(mmReceiptDines) == mmReceiptDines.size();
    }
}

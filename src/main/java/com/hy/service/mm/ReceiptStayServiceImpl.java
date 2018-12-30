package com.hy.service.mm;

import com.hy.common.SecurityUtil;
import com.hy.dto.MmReceiptStayFetchDto;
import com.hy.dto.MmReceiptStayViewDto;
import com.hy.mapper.ms.MmReceiptStayMapper;
import com.hy.model.MmMeeting;
import com.hy.model.MmReceiptStay;
import com.hy.model.VMmReceiptStay;
import com.hy.utils.DTOUtil;
import com.hy.utils.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

@Service
public class ReceiptStayServiceImpl implements ReceiptStayService {
    @Autowired
    private MmReceiptStayMapper mmReceiptStayMapper;

    @Autowired
    private MeetingService meetingService;

    @Override
    public List<MmReceiptStayViewDto> getReceiptStayView(int rid) {
        MmMeeting mmMeeting = meetingService.getMeetingByRid(rid);

        List<VMmReceiptStay> vrs = mmReceiptStayMapper.selectReceiptStayView(rid);

        if(vrs.size() != 0) {
            List<MmReceiptStayViewDto> rsdto = DTOUtil.populateList(vrs, MmReceiptStayViewDto.class);
            IntStream.range(0, vrs.size()).forEach(i -> rsdto.get(i).setDate(DateUtil.breviary(vrs.get(i).getDate())));
            return rsdto;
        }
        else {
            List<String> days = DateUtil.getDays(mmMeeting.getBegindate(),mmMeeting.getEnddate());
            List<MmReceiptStayViewDto> stayViewDtos = new ArrayList<>();
            if(days != null){
                for (String day: days){
                    stayViewDtos.add(new MmReceiptStayViewDto(rid, "", day, 0, 0, 0));
                }
            }
            return stayViewDtos;
        }
    }

    @Override
    public boolean setReceiptStay(List<MmReceiptStayFetchDto> mmReceiptStayFetchDtos) {

        List<MmReceiptStay> mmReceiptStays = DTOUtil.populateList(mmReceiptStayFetchDtos, MmReceiptStay.class);

        IntStream.range(0, mmReceiptStays.size()).forEach(i -> {
            mmReceiptStays.get(i).setModifier(SecurityUtil.getLoginid());
            mmReceiptStays.get(i).setDate(DateUtil.breviary(mmReceiptStayFetchDtos.get(i).getDate()));
        });

        return mmReceiptStayMapper.updateReceiptStay(mmReceiptStays) == mmReceiptStays.size();
    }

    @Override
    public boolean addReceiptStay(List<MmReceiptStayFetchDto> mmReceiptStayFetchDtos) {

        List<MmReceiptStay> mmReceiptStays = DTOUtil.populateList(mmReceiptStayFetchDtos, MmReceiptStay.class);

        IntStream.range(0, mmReceiptStays.size()).forEach(i -> {
            mmReceiptStays.get(i).setDate(DateUtil.breviary(mmReceiptStayFetchDtos.get(i).getDate()));
            mmReceiptStays.get(i).setModifier(SecurityUtil.getLoginid());
            mmReceiptStays.get(i).setCreater(SecurityUtil.getLoginid());
            mmReceiptStays.get(i).setDomain(SecurityUtil.getDepartmentId());
        });

        return mmReceiptStayMapper.insertReceiptStay(mmReceiptStays) == mmReceiptStays.size();
    }
}

package com.hy.service.mm;

import com.hy.common.SecurityHelp;
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
            for (int i = 0; i < vrs.size(); i++) {
                rsdto.get(i).setDate(DateUtil.breviary(vrs.get(i).getDate()));
            }
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
        for(int i = 0 ; i < mmReceiptStays.size(); i++){
            mmReceiptStays.get(i).setModifier(SecurityHelp.getUserId());
            mmReceiptStays.get(i).setDate(DateUtil.translate(mmReceiptStayFetchDtos.get(i).getDate()));
        }
        return mmReceiptStayMapper.updateReceiptStay(mmReceiptStays) == mmReceiptStays.size();
    }
}

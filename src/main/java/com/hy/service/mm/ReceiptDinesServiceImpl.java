package com.hy.service.mm;

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
}

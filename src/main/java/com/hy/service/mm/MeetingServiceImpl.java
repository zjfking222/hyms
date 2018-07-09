package com.hy.service.mm;

import com.github.pagehelper.PageHelper;
import com.hy.dto.MmMeetingDto;
import com.hy.mapper.ms.MmMeetingMapper;
import com.hy.model.MmMeeting;
import com.hy.utils.DTOUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

@Service
public class MeetingServiceImpl implements MeetingService{

    @Autowired
    private MmMeetingMapper mmMeetingMapper;

    @Override
    public boolean addMeeting(MmMeetingDto mmMeetingDto) {
        return mmMeetingMapper.insertMmMeeting(DTOUtil.populate(mmMeetingDto, MmMeeting.class)) == 1;
    }

    @Override
    public boolean setMeeting(MmMeetingDto mmMeetingDto) {
        return mmMeetingMapper.updateMmMeeting(DTOUtil.populate(mmMeetingDto, MmMeeting.class)) == 1;
    }

    @Override
    public boolean delMeeting(int id) {
        return mmMeetingMapper.deleteMmMeeting(id) == 1;
    }

    @Override
    public List<MmMeetingDto> getMeeting(int pageNum, int pageSize, String value, String sort, String dir) {
        PageHelper.startPage(pageNum, pageSize);
        //判断时间，设定会议的状态
        final String state1 = "未开始";
        final String state2 = "进行中";
        final String state3 = "已结束";
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        sdf.setTimeZone(TimeZone.getTimeZone("GMT"));

        List<MmMeeting> meetings = mmMeetingMapper.selectMmMeeting(value, sort, dir);
        List<MmMeetingDto> meetingDtos = DTOUtil.populateList(meetings, MmMeetingDto.class);
        Date now = new Date();

        for(int i = 0 ; i < meetings.size() ; i++){
            MmMeetingDto md = meetingDtos.get(i);
            MmMeeting mm = meetings.get(i);
            if(now.before(mm.getBegindate())){
                md.setState(state1);
            }
            else if(now.after(mm.getEnddate())){
                md.setState(state3);
            }
            else {
                md.setState(state2);
            }
            md.setBegindate(sdf.format(mm.getBegindate()));
            md.setEnddate(sdf.format(mm.getEnddate()));
        }
        return meetingDtos;
    }

    @Override
    public int getMeetingTotal(String value) {
        return mmMeetingMapper.selectMmMeetingTotal(value);
    }
}

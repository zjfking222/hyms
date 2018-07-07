package com.hy.service.mm;

import com.github.pagehelper.PageHelper;
import com.hy.dto.MmMeetingDto;
import com.hy.mapper.ms.MmMeetingMapper;
import com.hy.model.MmMeeting;
import com.hy.utils.DTOUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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
        return DTOUtil.populateList(mmMeetingMapper.selectMmMeeting(value, sort, dir), MmMeetingDto.class);
    }

    @Override
    public int getMeetingTotal(String value) {
        return mmMeetingMapper.selectMmMeetingTotal(value);
    }
}

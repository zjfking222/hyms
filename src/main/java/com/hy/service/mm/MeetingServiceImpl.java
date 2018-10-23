package com.hy.service.mm;

import com.github.pagehelper.PageHelper;
import com.hy.common.SecurityHelp;
import com.hy.dto.MmMeetingDto;
import com.hy.mapper.ms.MmMeetingMapper;
import com.hy.model.MmMeeting;
import com.hy.utils.DTOUtil;
import com.hy.utils.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.stream.IntStream;

@Service
public class MeetingServiceImpl implements MeetingService{

    @Autowired
    private MmMeetingMapper mmMeetingMapper;

    private MmMeeting meeting;

    @Override
    public MmMeetingDto addMeeting(MmMeetingDto mmMeetingDto) {


        meeting = DTOUtil.populate(mmMeetingDto, MmMeeting.class);
        meeting.setBegindate(DateUtil.translate(mmMeetingDto.getBegindate()));
        meeting.setEnddate(DateUtil.translate(mmMeetingDto.getEnddate()));
        meeting.setDeadline(DateUtil.translate(mmMeetingDto.getDeadline()));

        meeting.setCreater(SecurityHelp.getUserId());
        meeting.setModifier(SecurityHelp.getUserId());
        meeting.setDomain(SecurityHelp.getDepartmentId());

        mmMeetingMapper.insertMmMeeting(meeting);
        mmMeetingDto.setId(meeting.getId());
        mmMeetingDto.setState(DateUtil.getState(DateUtil.translate(mmMeetingDto.getBegindate()),
                DateUtil.translate(mmMeetingDto.getEnddate())));
        return mmMeetingDto;
    }

    @Override
    public boolean setMeeting(MmMeetingDto mmMeetingDto) {

        meeting = DTOUtil.populate(mmMeetingDto, MmMeeting.class);
        meeting.setBegindate(DateUtil.translate(mmMeetingDto.getBegindate()));
        meeting.setEnddate(DateUtil.translate(mmMeetingDto.getEnddate()));
        meeting.setDeadline(DateUtil.translate(mmMeetingDto.getDeadline()));

        meeting.setModifier(SecurityHelp.getUserId());

        return mmMeetingMapper.updateMmMeeting(meeting) == 1;
    }

    @Override
    public boolean delMeeting(int id) {
        return mmMeetingMapper.deleteMmMeeting(id) == 1;
    }

    @Override
    public List<MmMeetingDto> getMeeting(int pageNum, int pageSize, String value, String sort, String dir) {
        PageHelper.startPage(pageNum, pageSize);

        List<MmMeeting> meetings = mmMeetingMapper.selectMmMeeting(value, sort, dir);
        List<MmMeetingDto> meetingDtos = DTOUtil.populateList(meetings, MmMeetingDto.class);

        //过滤空值
        IntStream.range(0, meetings.size()).forEach(i -> {
            MmMeetingDto md = meetingDtos.get(i);
            MmMeeting mm = meetings.get(i);
            md.setState(DateUtil.getState(mm.getBegindate(), mm.getEnddate()));
            if (mm.getBegindate() == null) {

            } else {
                md.setBegindate(DateUtil.translate(mm.getBegindate()));
            }
            if (mm.getEnddate() != null) {
                md.setEnddate(DateUtil.translate(mm.getEnddate()));
            }
            if (mm.getDeadline() != null) {

                md.setDeadline(DateUtil.translate(mm.getDeadline()));
            }
        });
        return meetingDtos;
    }

    @Override
    public int getMeetingTotal(String value) {
        return mmMeetingMapper.selectMmMeetingTotal(value);
    }

    @Override
    public MmMeeting getMeetingByRid(int rid) {
        return mmMeetingMapper.selectMeetingByRid(rid);
    }

    //返回状态，给会议回执使用,当返回1时不显示提交按钮
    @Override
    public Integer getState(int id) {
        MmMeeting mmMeeting = mmMeetingMapper.selectMeetingById(id);
        if( new Date().after(mmMeeting.getDeadline()) ){
            return 1;
        }else {
            return 0;
        }
    }
}

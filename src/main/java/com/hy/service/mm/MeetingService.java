package com.hy.service.mm;

import com.hy.dto.MmMeetingDto;
import com.hy.model.MmMeeting;

import java.util.List;

public interface MeetingService {
    MmMeetingDto addMeeting(MmMeetingDto mmMeetingDto);
    boolean setMeeting(MmMeetingDto mmMeetingDto);
    boolean delMeeting(int id);
    List<MmMeetingDto> getMeeting(String filters, int pageNum, int pageSize, String value, String sort, String dir);
    int getMeetingTotal(String filters, String value);
    MmMeeting getMeetingByRid(int rid);
    Integer getState(int id);
}

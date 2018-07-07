package com.hy.service.mm;

import com.hy.dto.MmMeetingDto;
import com.hy.model.MmMeeting;

import java.util.List;

public interface MeetingService {
    boolean addMeeting(MmMeetingDto mmMeetingDto);
    boolean setMeeting(MmMeetingDto mmMeetingDto);
    boolean delMeeting(int id);
    List<MmMeetingDto> getMeeting(int pageNum, int pageSize, String value, String sort, String dir);
    int getMeetingTotal(String value);
}

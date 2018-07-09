package com.hy.controller.mm;

import com.hy.common.ResultObj;
import com.hy.dto.MmMeetingDto;
import com.hy.enums.ResultCode;
import com.hy.service.mm.MeetingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;

@RestController
@RequestMapping("/mm")
public class MmMeetingController {

    @Autowired
    private MeetingService meetingService;

    @PostMapping("/meeting/add")
    public ResultObj addMeeting(MmMeetingDto mmMeetingDto){
        return meetingService.addMeeting(mmMeetingDto)?
                ResultObj.success():
                ResultObj.error(ResultCode.ERROR_ADD_FAILED);
    }

    @PostMapping("/meeting/set")
    public ResultObj setMeeting(MmMeetingDto mmMeetingDto){
        return meetingService.setMeeting(mmMeetingDto)?
                ResultObj.success():
                ResultObj.error(ResultCode.ERROR_UPDATE_FAILED);
    }

    @PostMapping("/meeting/del")
    public ResultObj delMeeting(int id){
        return meetingService.delMeeting(id)?
                ResultObj.success():
                ResultObj.error(ResultCode.ERROR_DELETE_FAILED);
    }

    @PostMapping("/meeting/get")
    public ResultObj getMeeting(int page, int pageSize, @RequestParam(required = false) String value,
                                @RequestParam(required = false) String sort,
                                @RequestParam(required = false) String dir){
        HashMap<String, Object> map = new HashMap<>();
        map.put("data",meetingService.getMeeting(page, pageSize, value, sort, dir));
        map.put("total",meetingService.getMeetingTotal(value));
        return ResultObj.success(map);
    }
}

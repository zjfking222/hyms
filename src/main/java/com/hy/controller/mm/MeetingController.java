package com.hy.controller.mm;

import com.hy.common.ResultObj;
import com.hy.dto.MmMeetingDto;
import com.hy.enums.ResultCode;
import com.hy.service.mm.MeetingService;
import com.hy.utils.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.HashMap;

@RestController
@RequestMapping("/mm")
public class MeetingController {

    @Autowired
    private MeetingService meetingService;

    @PostMapping("/meeting/add")
    public ResultObj addMeeting(MmMeetingDto mmMeetingDto){
        try{
            MmMeetingDto meetingDto = meetingService.addMeeting(mmMeetingDto);
            return ResultObj.success(meetingDto);
        }catch (Exception e){
            return ResultObj.error(ResultCode.ERROR_ADD_FAILED);
        }
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
    public ResultObj getMeeting(@RequestParam(required = false) String filters, int page, int pageSize, @RequestParam(required = false) String value,
                                @RequestParam(required = false) String sort,
                                @RequestParam(required = false) String dir){
        HashMap<String, Object> map = new HashMap<>();
        map.put("data",meetingService.getMeeting(filters, page, pageSize, value, sort, dir));
        map.put("total",meetingService.getMeetingTotal(filters, value));
        return ResultObj.success(map);
    }
    @PostMapping("/meeting/getState")
    public ResultObj getMeetingState(int id){
        return ResultObj.success(meetingService.getState(id));
    }
}

package com.hy.controller.mm;

import com.hy.common.ResultObj;
import com.hy.service.mm.ReceiptService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;

@RestController
@RequestMapping("/mm")
public class MmReceiptController {

    @Autowired
    private ReceiptService receiptService;

    @PostMapping("/receipt/getMeeting")
    public ResultObj getReceiptMeetingView(int page, int pageSize, @RequestParam(required = false) String value,
                                           @RequestParam(required = false) String sort,
                                           @RequestParam(required = false) String dir){
        HashMap<String, Object> map = new HashMap<>();
        map.put("data", receiptService.getMeetingView(page, pageSize, value, sort, dir));
        map.put("total", receiptService.getMeetingViewTotal(value));
        return ResultObj.success(map);
    }
}

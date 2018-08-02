package com.hy.controller.mm;

import com.hy.common.ResultObj;
import com.hy.dto.MmReceiptFetchDto;
import com.hy.enums.ResultCode;
import com.hy.service.mm.ReceiptService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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

    @PostMapping("/receipt/getReceiptInfo")
    public ResultObj getReceiptInfoView(int page, int pageSize, @RequestParam(required = false) String value,
                                        @RequestParam(required = false) String sort,
                                        @RequestParam(required = false) String dir,
                                        int mid){
        HashMap<String, Object> map = new HashMap<>();
        map.put("data", receiptService.getReceiptView(page, pageSize, value, sort, dir, mid));
        map.put("total", receiptService.getReceiptViewTotal(value , mid));
        return ResultObj.success(map);
    }

    @PostMapping("/receipt/getReceiptDetail")
    public ResultObj getReceiptDetail(int rid){
        return ResultObj.success(receiptService.getReceiptDetail(rid));
    }

    @PostMapping("/receipt/del")
    public ResultObj delReceipt(int id){
        return receiptService.delReceipt(id)?
                ResultObj.success():
                ResultObj.error(ResultCode.ERROR_DELETE_FOREIGN);
    }

    @PostMapping("/receipt/set")
    public ResultObj setReceipt(@RequestBody MmReceiptFetchDto mmReceiptFetchDto){
        return ResultObj.success(receiptService.setReceipt(mmReceiptFetchDto));
    }
}

package com.hy.controller.mm;

import com.hy.common.ResultObj;
import com.hy.dto.MmReceiptDto;
import com.hy.dto.MmReceiptFetchDto;
import com.hy.dto.MmReceiptNewDto;
import com.hy.enums.ResultCode;
import com.hy.service.mm.ReceiptService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("/mm")
public class ReceiptController {

    @Autowired
    private ReceiptService receiptService;

    @PostMapping("/receipt/getMeeting")
    public ResultObj getReceiptMeetingView(int page, int pageSize, @RequestParam(required = false) String value,
                                           @RequestParam(required = false) String sort,
                                           @RequestParam(required = false) String dir,
                                           @RequestParam(required = false) String state){
        HashMap<String, Object> map = new HashMap<>();
        map.put("data", receiptService.getMeetingView(page, pageSize, value, sort, dir, state));
        map.put("total", receiptService.getMeetingViewTotal(value, state));
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

    @PostMapping("/receipt/getReceiptInfoInBtid")
    public ResultObj getReceiptInfoViewInBtid(int page, int pageSize, @RequestParam(required = false) String value,
                                        @RequestParam(required = false) String sort,
                                        @RequestParam(required = false) String dir,
                                        int mid){
        HashMap<String, Object> map = new HashMap<>();
        map.put("data", receiptService.getReceiptViewInBtid(page, pageSize, value, sort, dir, mid));
        map.put("total", receiptService.getReceiptViewInBtidTotal(value , mid));
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
        try {
            return ResultObj.success(receiptService.setReceipt(mmReceiptFetchDto));
        }catch (Exception e){
            e.printStackTrace();
            return ResultObj.error(ResultCode.ERROR_UPDATE_FAILED);
        }
    }
    @PostMapping("/receipt/add")
    public ResultObj addReceipt(@RequestBody List<MmReceiptNewDto> mmReceiptNewDtos){
        try {
            return ResultObj.success(receiptService.addReceipt(mmReceiptNewDtos));
        }catch (Exception e){
            e.printStackTrace();
            return ResultObj.error(ResultCode.ERROR_ADD_FAILED);
        }
    }
    @PostMapping("/receipt/setState")
    public ResultObj setReceiptState(@RequestBody List<MmReceiptDto> mmReceiptDtos){
        try {
            return ResultObj.success(receiptService.setReceiptState(mmReceiptDtos));
        }catch (Exception e){
            e.printStackTrace();
            return ResultObj.error(ResultCode.ERROR_UPDATE_FAILED);
        }
    }
}

package com.hy.controller.qzgz;

import com.hy.common.ResultObj;
import com.hy.enums.ResultCode;
import com.hy.service.qzgz.CanteenHistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CanteenHistoryController {
    @Autowired
    private CanteenHistoryService canteenHistoryService;

    @PostMapping("/getTodaysCanteen")
    public ResultObj getTodaysCanteen()
    {
        return ResultObj.success(canteenHistoryService.getTodaysCanteen());
    }
    @PostMapping("/addTodaysCanteen")
    public ResultObj addTodaysCanteen(int id)
    {
        return canteenHistoryService.addTodaysCanteen(id)?
                ResultObj.success():
                ResultObj.error(ResultCode.ERROR_INVALID_PARAMETER);
    }
    @PostMapping("/removeTodaysCanteen")
    public ResultObj removeTodaysCanteen(int id)
    {
        return canteenHistoryService.removeTodaysCanteen(id)?
                ResultObj.success():
                ResultObj.error(ResultCode.ERROR_INVALID_PARAMETER);
    }
}

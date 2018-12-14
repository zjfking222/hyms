package com.hy.controller.qzgz;

import com.hy.common.ResultObj;
import com.hy.enums.ResultCode;
import com.hy.service.qzgz.CanteenHistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/qzgz")
public class CanteenHistoryController {
//    @Autowired
//    private CanteenHistoryService canteenHistoryService;
//
//    @PostMapping("/web/getTodaysCanteen")
//    public ResultObj getTodaysCanteen(int plusDay)
//    {
//        return ResultObj.success(canteenHistoryService.getTodaysCanteen(plusDay));
//    }
//    @PostMapping("/admin/addTodaysCanteen")
//    public ResultObj addTodaysCanteen(int id, int meal, int plusDay)
//    {
//        return canteenHistoryService.addTodaysCanteen(id, meal, plusDay)?
//                ResultObj.success():
//                ResultObj.error(ResultCode.ERROR_INVALID_PARAMETER);
//    }
//    @PostMapping("/admin/removeTodaysCanteen")
//    public ResultObj removeTodaysCanteen(int id, int meal, int plusDay)
//    {
//        return canteenHistoryService.removeTodaysCanteen(id, meal, plusDay)?
//                ResultObj.success():
//                ResultObj.error(ResultCode.ERROR_INVALID_PARAMETER);
//    }
//    //未使用接口
//    @PostMapping("/admin/getTodaysCanteenView")
//    public ResultObj getTodaysCanteenView()
//    {
//        return ResultObj.success(canteenHistoryService.getTodaysCanteenView());
//    }
//
//    @PostMapping("/web/getCanteenHistoryByDay")
//    public ResultObj getCanteenHistoryByDay(int plusDay)
//    {
//        return ResultObj.success(canteenHistoryService.getCanteenHistoryByDay(plusDay));
//    }
}

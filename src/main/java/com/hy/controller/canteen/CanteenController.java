package com.hy.controller.canteen;

import com.hy.common.ResultObj;
import com.hy.service.ms.CanteenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CanteenController {

    @Autowired
    private CanteenService canteenService;

    @PostMapping("/getCanteen")
    public ResultObj getCanteen()
    {
        return ResultObj.success(canteenService.getCanteen());
    }
}

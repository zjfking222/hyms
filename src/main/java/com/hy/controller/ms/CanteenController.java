package com.hy.controller.ms;

import com.hy.common.ResultObj;
import com.hy.dto.CanteenWithTotalPageDto;
import com.hy.enums.ResultCode;
import com.hy.service.ms.CanteenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CanteenController {

    @Autowired
    private CanteenService canteenService;

    @PostMapping("/getCanteen")
    public ResultObj getCanteen(int page, int number)
    {
        CanteenWithTotalPageDto canteenWithTotalPageDto =
                canteenService.getCanteen(page,number);

        return canteenWithTotalPageDto == null ?
                ResultObj.error(ResultCode.ERROR_NO_RESOURCE):
                ResultObj.success(canteenWithTotalPageDto);
    }
}

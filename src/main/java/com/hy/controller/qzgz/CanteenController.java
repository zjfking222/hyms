package com.hy.controller.qzgz;

import com.hy.common.ResultObj;
import com.hy.dto.CanteenWithTotalPageDto;
import com.hy.enums.ResultCode;
import com.hy.service.qzgz.CanteenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CanteenController {

    @Autowired
    private CanteenService canteenService;

    @PostMapping("/getCanteen")
    public ResultObj getCanteen(int page, int number, @RequestParam(required = false) String state)
    {
        CanteenWithTotalPageDto canteenWithTotalPageDto =
                canteenService.getCanteen(page, number, state);

        return canteenWithTotalPageDto == null ?
                ResultObj.error(ResultCode.ERROR_NO_RESOURCE):
                ResultObj.success(canteenWithTotalPageDto);
    }
    @PostMapping("/addCanteen")
    public ResultObj addCanteen(String name, double price)
    {
        return canteenService.addCanteen(name, price) ?
                ResultObj.success():
                ResultObj.error(ResultCode.ERROR_INVALID_PARAMETER);
    }
    @PostMapping("/updateCanteen")
    public ResultObj updateCanteen(String name, double price, int id)
    {
        return canteenService.updateCanteen(name, price, id) ?
                ResultObj.success():
                ResultObj.error(ResultCode.ERROR_INVALID_PARAMETER);
    }
    @PostMapping("/updateCanteenState")
    public ResultObj updateCanteenState(String state, int id)
    {
        return canteenService.updateCanteenState(state, id) ?
                ResultObj.success():
                ResultObj.error(ResultCode.ERROR_INVALID_PARAMETER);
    }
    @PostMapping("/getCanteenByName")
    public ResultObj getCanteenByName(String name)
    {
        return ResultObj.success(canteenService.getCanteenBySearchName(name));
    }
}

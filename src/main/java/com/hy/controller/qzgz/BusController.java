package com.hy.controller.qzgz;

import com.hy.common.ResultObj;
import com.hy.dto.BusDto;
import com.hy.enums.ResultCode;
import com.hy.service.qzgz.BusService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class BusController {

    @Autowired
    private BusService busService;

    @PostMapping("/getBus")
    public ResultObj getBus()
    {
        List<BusDto> buses = busService.getBusInfo();
        return ResultObj.success(buses);
    }
    @PostMapping("/setBus")
    public ResultObj setBus(BusDto busDto)
    {
        return busService.setBusInfo(busDto)?
                ResultObj.success():
                ResultObj.error(ResultCode.ERROR_INVALID_PARAMETER);
    }
    @PostMapping("/addBus")
    public ResultObj addBus(BusDto busDto)
    {
        return busService.addBusInfo(busDto)?
                ResultObj.success():
                ResultObj.error(ResultCode.ERROR_INVALID_PARAMETER);
    }
    @PostMapping("/delBus")
    public ResultObj delBus(int id)
    {
        return busService.delBusInfo(id)?
                ResultObj.success():
                ResultObj.error(ResultCode.ERROR_INVALID_PARAMETER);
    }
}

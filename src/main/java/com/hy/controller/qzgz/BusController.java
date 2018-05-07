package com.hy.controller.qzgz;

import com.hy.common.ResultObj;
import com.hy.dto.BusDto;
import com.hy.enums.ResultCode;
import com.hy.service.qzgz.BusService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/qzgz")
public class BusController {

    @Autowired
    private BusService busService;

    @PostMapping("/web/getBus")
    public ResultObj getBus()
    {
        List<BusDto> buses = busService.getBusInfo();
        return ResultObj.success(buses);
    }
    @PostMapping("/admin/setBus")
    public ResultObj setBus(BusDto busDto)
    {
        return busService.setBusInfo(busDto)?
                ResultObj.success():
                ResultObj.error(ResultCode.ERROR_INVALID_PARAMETER);
    }
    @PostMapping("/admin/addBus")
    public ResultObj addBus(BusDto busDto)
    {
        return busService.addBusInfo(busDto)?
                ResultObj.success():
                ResultObj.error(ResultCode.ERROR_INVALID_PARAMETER);
    }
    @PostMapping("/admin/delBus")
    public ResultObj delBus(int id)
    {
        return busService.delBusInfo(id)?
                ResultObj.success():
                ResultObj.error(ResultCode.ERROR_INVALID_PARAMETER);
    }
}

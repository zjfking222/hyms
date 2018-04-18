package com.hy.controller.ms;

import com.hy.common.ResultObj;
import com.hy.dto.BusDto;
import com.hy.enums.ResultCode;
import com.hy.service.ms.BusService;
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
}

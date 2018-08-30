package com.hy.controller.mm;

import com.hy.common.ResultObj;
import com.hy.dto.MmBusDto;
import com.hy.enums.ResultCode;
import com.hy.service.mm.MeetingBusService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;

@RestController
@RequestMapping("/mm")
public class MmBusController {
    @Autowired
    private MeetingBusService busService;

    @PostMapping("/bus/get")
    public ResultObj getBus() {
        return ResultObj.success(busService.getBus());
    }

    @PostMapping("/bus/add")
    public ResultObj addBus(MmBusDto mmBusDto) {
        return ResultObj.success(busService.addBus(mmBusDto));
    }

    @PostMapping("/bus/update")
    public ResultObj updateBus(MmBusDto mmBusDto) {
        return busService.updateBus(mmBusDto) ?
                ResultObj.success() :
                ResultObj.error(ResultCode.ERROR_UPDATE_FAILED);
    }

    @PostMapping("/bus/delete")
    public ResultObj deleteBus(MmBusDto mmBusDto) {
        return busService.deleteBus(mmBusDto) ?
                ResultObj.success() :
                ResultObj.error(ResultCode.ERROR_UPDATE_FAILED);
    }

    @PostMapping("/bus/getAll")
    public ResultObj getAll(int page, int pageSize, @RequestParam(required = false) String value,
                            @RequestParam(required = false) String sort,
                            @RequestParam(required = false) String dir, int mid) {
        HashMap<String, Object> map = new HashMap<>();
        map.put("data", busService.getAllBus(page, pageSize, value, sort, dir, mid));
        map.put("total", busService.getCountBus(value, mid));
        return ResultObj.success(map);
    }

    @PostMapping("bus/getInfo")
    public ResultObj getInfo(int mid){
        try {
            return ResultObj.success(busService.getInfoBus(mid));
        }catch (Exception e){
            e.printStackTrace();
            return  ResultObj.error(ResultCode.ERROR_UNKNOWN);
        }
    }
}

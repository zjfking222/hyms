package com.hy.controller.baseinfo;

import com.hy.common.ResultObj;
import com.hy.service.baseinfo.HotelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/bi")
public class HotelController {

    @Autowired
    private HotelService hotelService;

    @PostMapping("/hotel/get")
    public ResultObj getHotel(){
        return ResultObj.success(hotelService.getHotel());
    }
}

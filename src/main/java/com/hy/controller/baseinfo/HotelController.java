package com.hy.controller.baseinfo;

import com.hy.common.ResultObj;
import com.hy.dto.BiHotelDto;
import com.hy.enums.ResultCode;
import com.hy.service.baseinfo.HotelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;

@RestController
@RequestMapping("/bi")
public class HotelController {

    @Autowired
    private HotelService hotelService;

    @PostMapping("/hotel/get")
    public ResultObj getHotel() {
        return ResultObj.success(hotelService.getHotel());
    }

    @PostMapping("/hotel/add")
    public ResultObj addHotel(BiHotelDto biHotelDto) {
        return ResultObj.success(hotelService.addHotel(biHotelDto));
    }

    @PostMapping("/hotel/getList")//接收页码和每页显示个数
    public ResultObj getListHotel(int page,int pageSize) {
        return ResultObj.success(hotelService.getListHotel(page,pageSize));
    }

    @PostMapping("/hotel/update")//接收ID、修改信息(Dto包含修改的信息和ID)
    public ResultObj updateHotel(BiHotelDto biHotelDto){
        return hotelService.updateHotel(biHotelDto)?
                ResultObj.success() :
                ResultObj.error(ResultCode.ERROR_UPDATE_FAILED);
    }

    @PostMapping("/hotel/delete")//同update
    public ResultObj deleteHotel(BiHotelDto biHotelDto){
        return hotelService.deleteHotel(biHotelDto)?
                ResultObj.success() :
                ResultObj.error(ResultCode.ERROR_DELETE_FAILED);
    }

    @PostMapping("/hotel/getAll")
    public ResultObj getAllHotel(int page, int pageSize, @RequestParam(required = false) String value,
                                 @RequestParam(required = false) String sort,
                                 @RequestParam(required = false) String dir){
        HashMap<String,Object> map=new HashMap<>();
        map.put("data",hotelService.getAllHotel(page,pageSize,value,sort,dir));
        map.put("total",hotelService.getTotalHotel(value));
        return ResultObj.success(map);
    }


}

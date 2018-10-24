package com.hy.controller.ad;

import com.alibaba.fastjson.JSONObject;
import com.hy.common.ResultObj;
import com.hy.dto.AdDepartmentDto;
import com.hy.enums.ResultCode;
import com.hy.service.ad.AdDepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/ad")
public class AdDepartmentController {
    @Autowired
    private AdDepartmentService adDepartmentService;

    @PostMapping("/department/add")
    public ResultObj addAdDepartment(@RequestBody JSONObject adDepartment){
        try{
            return ResultObj.success(adDepartmentService.addAdDepartment(adDepartment));
        }catch (Exception e){
            e.printStackTrace();
            return ResultObj.error(ResultCode.ERROR_UPDATE_FAILED);
        }
    }
    @PostMapping("/department/select")
    public ResultObj selectAdDepartment(String date,String time){
        try{
            return ResultObj.success(adDepartmentService.selectAdDepartment(date,time));
        }catch (Exception e){
            e.printStackTrace();
            return ResultObj.error(ResultCode.ERROR_NO_RESOURCE);
        }
    }
    @PostMapping("/department/getTime")
    public ResultObj getTime(String date){
        try{
            return ResultObj.success(adDepartmentService.getTime(date));
        }catch (Exception e){
            e.printStackTrace();
            return ResultObj.error(ResultCode.ERROR_NO_RESOURCE);
        }
    }
    @PostMapping("/department/getChangeDep")
    public ResultObj getChangeDep(String date,String time){
        try{
            return ResultObj.success(adDepartmentService.getChangeDep(date,time));
        }catch (Exception e){
            e.printStackTrace();
            return ResultObj.error(ResultCode.ERROR_NO_RESOURCE);
        }
    }
}

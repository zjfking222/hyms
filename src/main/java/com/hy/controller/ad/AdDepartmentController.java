package com.hy.controller.ad;

import com.alibaba.fastjson.JSONObject;
import com.hy.common.ResultObj;
import com.hy.enums.ResultCode;
import com.hy.service.ad.AdDepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}

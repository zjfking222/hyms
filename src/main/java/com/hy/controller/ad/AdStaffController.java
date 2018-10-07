package com.hy.controller.ad;

import com.hy.common.ResultObj;

import com.hy.enums.ResultCode;
import com.hy.service.ad.AdStaffService;
import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/ad")
public class AdStaffController {

    @Autowired
    private AdStaffService adStaffService;

    @PostMapping("/staff/add")
    public ResultObj addAdStaff(@RequestBody JSONObject adStaff) {
        try {
            return ResultObj.success(adStaffService.addAdStaff(adStaff));
        } catch (Exception e) {
            e.printStackTrace();
            return ResultObj.error(ResultCode.ERROR_UPDATE_FAILED);
        }
    }
}

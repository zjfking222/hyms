package com.hy.controller.ad;

import com.hy.common.ResultObj;

import com.hy.config.ldap.LdapConfig;
import com.hy.config.ldap.LdapUtil;
import com.hy.enums.ResultCode;
import com.hy.service.ad.AdStaffService;
import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    @PostMapping("/staff/select")
    public Map<String, Object> selectAdStaff(int page, int limit, String date, String time) {
        Map<String, Object> map = new HashMap<>();
        try {
            List list = adStaffService.selectAdStaff(page,limit,date,time);
            int count=adStaffService.getCount(date,time);
            map.put("data", list);
            map.put("count", count);
            map.put("msg","");
            map.put("code",0);
            return map;
        } catch (Exception e) {
            e.printStackTrace();
            map.put("code",1);
            return map;
        }
    }
    @PostMapping("/staff/search")
    @CrossOrigin("*")
    public ResultObj searchAdStaff(String name){
        try{
            return ResultObj.success(LdapUtil.searchStaffByName(name));
        }catch (Exception e){
            return ResultObj.error(ResultCode.ERROR_UNKNOWN);
        }
    }
}

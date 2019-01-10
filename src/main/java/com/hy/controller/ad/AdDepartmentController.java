package com.hy.controller.ad;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.hy.common.ResultObj;
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
    public String selectAdDepartment(){
        try{
            JSONObject jo = new JSONObject();
            jo.put("data", adDepartmentService.selectAdDepartment());
            jo.put("code", "0");
            jo.put("msg","");
            String jsonstr = JSON.toJSONString(jo);
            return jsonstr.replaceAll("\\\\","")
                    .replace("\"[","[").replace("]\"","]");

        }catch (Exception e){
            e.printStackTrace();
            return null;
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
    @PostMapping("/department/updateOperator")
    public ResultObj updateOperator(String id){
        try {
            return ResultObj.success(adDepartmentService.updateOperator(id));
        }catch (SecurityException e){
            return ResultObj.error(ResultCode.ERROR_UNKNOWN);
        }
    }
}

package com.hy.controller.m;

import com.hy.common.ResultObj;
import com.hy.common.SecurityUtil;
import com.hy.enums.ResultCode;
import com.hy.model.HrmResource;
import com.hy.service.m.SelfHelpService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletResponse;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

/**
 * @Auther: 钱敏杰
 * @Date: 2018/11/1 15:21
 * @Description:个人自助模块controller
 */
@RestController
@RequestMapping("/app/selfhelp")
public class SelfHelpController {

    @Autowired
    private SelfHelpService selfHelpService;

    /**
     * @Author 钱敏杰
     * @Description 获取验证码
     * @Date 2018/11/5 18:26
     * @Param [request, response]
     * @return com.hy.common.ResultObj
     **/
    @GetMapping("/getVerificationCode")
    public ResultObj getVerificationCode(HttpServletResponse response){
        selfHelpService.getVerificationCode(response);
        return ResultObj.success();
    }

    /**
     * @Author 钱敏杰
     * @Description 验证用户身份，包括密码与验证码
     * @Date 2018/11/5 18:27
     * @Param [request, data]
     * @return com.hy.common.ResultObj
     **/
    @PostMapping("/authentication")
    public ResultObj authentication(@RequestBody Map<String, String> data){
        String password = data.get("password");
        String code = data.get("code");
        HrmResource hrmResource = SecurityUtil.getUserInfo();
        if(!selfHelpService.authentication(code) ){//验证验证码
            return ResultObj.error(ResultCode.ERROR_USER_VERIFICATIONCODE);
        }else{
            Map<String, String> map = new HashMap<>();
            Calendar c = Calendar.getInstance();
            String year = c.get(Calendar.YEAR) + "";
            String month = "9";//c.get(Calendar.MONTH) + 1 + "";

            //添加假数据
            hrmResource.setLoginid("100496");
            hrmResource.setLastname("100496@huayou.com");
            password = "Pzy314504";
            //查询薪资数据，若返回失败，则返回验证密码失败，否则将数据传到下一个页面
            Map<String, Object> results = selfHelpService.getSalaryData(hrmResource.getLoginid(), hrmResource.getLastname(), password, year, month);
            //根据结果标记判断
            String flag = (String)results.get("flag");
            if("true".equals(flag)){
                results.put("year", year);
                results.put("month", month);
                return ResultObj.success(results);
            }else{//若调用失败，则表示验证未通过，返回错误消息
                return ResultObj.error(ResultCode.ERROR_USER_UNMATCH, (String)results.get("message"));
            }
        }
    }

    @PostMapping("/getRecord")
    public ResultObj getRecord(){
        selfHelpService.getRecord();
        /*String flag = (String)results.get("flag");
        if("true".equals(flag)){
            SalaryDto dto = (SalaryDto)results.get("data");
            return ResultObj.success(dto);
        }else{
            return ResultObj.error(ResultCode.ERROR_USER_UNMATCH, (String)results.get("message"));
        }*/
        return ResultObj.success("");
    }
}

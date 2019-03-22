package com.hy.controller.m;

import com.hy.common.ResultObj;
import com.hy.common.SecurityUtil;
import com.hy.config.shiro.ShiroUserInfo;
import com.hy.enums.ResultCode;
import com.hy.service.m.IdentityService;
import com.hy.service.m.SelfHelpService;
import com.hy.utils.VerificationCodeUtil;
import com.sap.conn.jco.JCoFunction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * @Auther: 钱敏杰
 * @Date: 2018/11/17 16:17
 * @Description:身份相关操作controller
 */
@RestController
@RequestMapping("/app/identity")
public class IdentityController {

    @Autowired
    private IdentityService identityService;
    @Autowired
    private SelfHelpService selfHelpService;

    /**
     * @Author 钱敏杰
     * @Description 从session中获取当前登陆用户信息
     * @Date 2018/11/20 16:14
     * @Param []
     * @return com.hy.common.ResultObj
     **/
    @PostMapping("/getPersonInfo")
    public ResultObj getPersonInfo(){
        ShiroUserInfo info = SecurityUtil.getUserInfo();
        if(info == null){
            return ResultObj.error(ResultCode.ERROR_NO_RESOURCE);
        }else{
            return ResultObj.success(info);
        }
    }

    /**
     * @Author 钱敏杰
     * @Description 手机号维护模块向手机发送短信验证码并保存验证码
     * @Date 2018/11/20 16:51
     * @Param [data]
     * @return com.hy.common.ResultObj
     **/
    @PostMapping("/sendMdefVerCode")
    public ResultObj sendMdefVerCode(@RequestBody Map<String, String> data){
        String phone = data.get("phone");
        //生成6位随机数字字符串
        String code = VerificationCodeUtil.getRandomNumberCode(6);
        identityService.sendVerCode(code, phone);
        return ResultObj.success();
    }

    @CrossOrigin
    @PostMapping("/checkSendMdefVerCode")
    public ResultObj checkSendMdefVerCode(@RequestBody Map<String, String> data){
        String uid = data.get("uid");
        String phone = data.get("phone");
        JCoFunction function = null;
        try{
            function = selfHelpService.getEmployeeFunction(uid);
        }catch(RuntimeException e){
            return ResultObj.error(ResultCode.ERROR_USER_UNEXISTS);
        }
        if(!identityService.checkIdPhoneBySAP(function, phone)){//身份验证
            return ResultObj.error(ResultCode.ERROR_USER_AND_PHONE);
        }else{
            //生成6位随机数字字符串
            String code = VerificationCodeUtil.getRandomNumberCode(6);
            identityService.sendVerCode(code, phone);
            return ResultObj.success();
        }
    }

    /**
     * @Author 钱敏杰
     * @Description 身份验证并修改ad域中当前人员的手机号
     * @Date 2018/11/19 9:55
     * @Param [data]
     * @return com.hy.common.ResultObj
     **/
    @PostMapping("/savePhone")
    public ResultObj savePhone(@RequestBody Map<String, String> data){
        String code = data.get("code");
        String password = data.get("password");
        //新手机号
        String phone = data.get("phone");
        if(!identityService.checkVerCode(code, phone)){//检查短信验证码是否正确
            return ResultObj.error(ResultCode.ERROR_USER_VERIFICATIONCODE);
        }else if(!identityService.checkPassword(password)){//检查密码
            return ResultObj.error(ResultCode.ERROR_USER_UNMATCH);
        }else{
            //保存新手机号到ad域
            identityService.savePhone(phone);
            return ResultObj.success();
        }
    }

    /**
     * @Author 钱敏杰
     * @Description 修改AD域中的密码
     * @Date 2018/11/22 14:36
     * @Param [data]
     * @return com.hy.common.ResultObj
     **/
    @PostMapping("/resetPassword")
    public ResultObj resetPassword(@RequestBody Map<String, String> data){
        String oldPassword = data.get("oldPassword");
        String newPassword = data.get("newPassword");
        String newPassword2 = data.get("newPassword2");
        if(!identityService.checkPassword(oldPassword)){//检查密码
            return ResultObj.error(ResultCode.ERROR_USER_UNMATCH);
        } else if(!identityService.checkPasswords(newPassword, newPassword2)){//新密码验证
            return ResultObj.error(ResultCode.ERROR_USER_PASSWORD_AGREE);
        }else{
            //更改ad域当前登陆用户的密码
            String uid = SecurityUtil.getLoginid();
            identityService.resetPassword(uid, newPassword);
            return ResultObj.success();
        }
    }

    /**
     * @Author 钱敏杰
     * @Description 忘记密码，使用手机号重置密码
     * @Date 2018/11/23 15:04
     * @Param [data]
     * @return com.hy.common.ResultObj
     **/
    @CrossOrigin
    @PostMapping("/resetForgotPassword")
    public ResultObj resetForgotPassword(@RequestBody Map<String, String> data){
        String uid = data.get("uid");
        String phone = data.get("phone");
        String code = data.get("code");
        String newPassword = data.get("newPassword");
        String newPassword2 = data.get("newPassword2");
        JCoFunction function = null;
        try{
            function = selfHelpService.getEmployeeFunction(uid);
        }catch(RuntimeException e){
            return ResultObj.error(ResultCode.ERROR_USER_UNEXISTS);
        }
        if(!identityService.checkIdPhoneBySAP(function, phone)){//身份验证
            return ResultObj.error(ResultCode.ERROR_USER_AND_PHONE);
        }else if(!identityService.checkVerCode(code, phone)){//检查短信验证码是否正确
            return ResultObj.error(ResultCode.ERROR_USER_VERIFICATIONCODE);
        }else if(!identityService.checkPasswords(newPassword, newPassword2)){//新密码输入验证
            return ResultObj.error(ResultCode.ERROR_USER_PASSWORD_AGREE);
        }else{
            //更改此用户的ad域密码
            identityService.resetPassword(uid, newPassword);
            return ResultObj.success();
        }
    }
}

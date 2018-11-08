package com.hy.controller.m;

import com.hy.common.ResultObj;
import com.hy.common.SecurityUtil;
import com.hy.config.shiro.RedisCacheManager;
import com.hy.config.shiro.ShiroUserInfo;
import com.hy.dto.SapBaseInfoDto;
import com.hy.dto.SapContractInfoDto;
import com.hy.dto.SapQuaCertificateDto;
import com.hy.dto.SapTechTitleDto;
import com.hy.enums.ResultCode;
import com.hy.model.HrmResource;
import com.hy.service.m.SelfHelpService;
import com.sap.conn.jco.JCoFunction;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.cache.Cache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletResponse;
import java.io.Serializable;
import java.util.*;

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
    @Autowired
    private RedisCacheManager redisCacheManager;
    private String cacheKey = "HT_access_token_key";
    private String tokenKey = "access_token";
    private String refreshKey = "refresh_token";
    private String itimeKey = "issued_time";
    private String etimeKey = "expire_time";
    private String cidKey = "client_id";

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
        ShiroUserInfo hrmResource = SecurityUtil.getUserInfo();
        if(!selfHelpService.authentication(code) ){//验证验证码
            return ResultObj.error(ResultCode.ERROR_USER_VERIFICATIONCODE);
        }else{
            Map<String, String> map = new HashMap<>();
            Calendar c = Calendar.getInstance();
            String year = c.get(Calendar.YEAR) + "";
            String month = "9";//c.get(Calendar.MONTH) + 1 + "";

            //添加假数据
            hrmResource.setLoginid("100496");
            hrmResource.setName("100496@huayou.com");
            password = "Pzy314504";

            //查询薪资数据，若返回失败，则返回验证密码失败，否则将数据传到下一个页面
            Map<String, Object> results = selfHelpService.getSalaryData(hrmResource.getLoginid(), hrmResource.getName(), password, year, month);
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

    /**
     * @Author 钱敏杰
     * @Description 检查恒拓app的access_token值是否已保存，并返回保存值
     * @Date 2018/11/7 16:53
     * @Param []
     * @return com.hy.common.ResultObj
     **/
    @PostMapping("/checkAccessToken")
    public ResultObj checkAccessToken(){
        Cache<Serializable, Object> cache = redisCacheManager.getCache(cacheKey);
        String accessToken = (String)cache.get(tokenKey);
        if(StringUtils.isEmpty(accessToken)){
            return ResultObj.error(ResultCode.ERROR_NO_RESOURCE);
        }else{
            return ResultObj.success(accessToken);
        }
    }

    /**
     * @Author 钱敏杰
     * @Description 保存恒拓app的access_token及其相关数据到缓存
     * @Date 2018/11/7 16:54
     * @Param [data]
     * @return com.hy.common.ResultObj
     **/
    @PostMapping("/saveAccessToken")
    public ResultObj saveAccessToken(@RequestBody Map<String, String> data){
        String accessToken = data.get("access_token");
        String refreshToken = data.get("refresh_token");
        String issuedTime = data.get("issued_time");
        String expireTime = data.get("expire_time");
        String clientId = data.get("client_id");
        Long itime = Long.parseLong(issuedTime);
        Long etime = Long.parseLong(expireTime);
        //设置缓存保存时间
        int expire = (int) (etime-itime)/1000-10;
        redisCacheManager.setExpire(expire);
        Cache<Serializable, Object> cache = redisCacheManager.getCache(cacheKey);
        cache.put(tokenKey, accessToken);
        cache.put(refreshKey, refreshToken);
        cache.put(itimeKey, issuedTime);
        cache.put(etimeKey, expireTime);
        cache.put(cidKey, clientId);
        return ResultObj.success();
    }

    /**
     * @Author 钱敏杰
     * @Description 获取当前用户的上月与当月的考勤汇总数据
     * @Date 2018/11/7 16:55
     * @Param []
     * @return com.hy.common.ResultObj
     **/
    @PostMapping("/getAttendance")
    public ResultObj getAttendance(){
        ShiroUserInfo hrmResource = SecurityUtil.getUserInfo();
        Calendar c = Calendar.getInstance();
        String year = c.get(Calendar.YEAR) + "";
        String month = "9";//c.get(Calendar.MONTH) + 1 + "";
        List<Map<String, Object>> list = new ArrayList<>();
        Map<String, Object> map1 = selfHelpService.getAttendance("100496", year, month);
        list.add(map1);
        month = "10";
        Map<String, Object> map2 = selfHelpService.getAttendance("100496", year, month);
        list.add(map2);
        return ResultObj.success(list);
    }

    /**
     * @Author 钱敏杰
     * @Description 获取个人基本信息数据
     * @Date 2018/11/7 19:35
     * @Param []
     * @return com.hy.common.ResultObj
     **/
    @PostMapping("/getBasicInfo")
    public ResultObj getBasicInfo(){
        ShiroUserInfo hrmResource = SecurityUtil.getUserInfo();
        JCoFunction function = selfHelpService.getEmployeeFunction("100496");
        //取基本信息
        SapBaseInfoDto base = selfHelpService.getBaseInfo(function);
        //取合同信息
        SapContractInfoDto ht = selfHelpService.getContractInfo(function);
        //取职称信息
        SapTechTitleDto tt = selfHelpService.getTechTitleInfo(function);
        //取资格证书信息
        SapQuaCertificateDto qc = selfHelpService.getQuaCertificateInfo(function);
        //整合数据
        Map<String, Object> result = new HashMap<>();
        result.put("base", base);
        result.put("ht", ht);
        result.put("tt", tt);
        result.put("qc", qc);
        return ResultObj.success(result);
    }

    @PostMapping("/getSchedulingInfo")
    public ResultObj getSchedulingInfo(){
        //selfHelpService.getSchedulingInfo();
        /*String flag = (String)results.get("flag");
        if("true".equals(flag)){
            SalaryDto dto = (SalaryDto)results.get("data");
            return ResultObj.success(dto);
        }else{
            return ResultObj.error(ResultCode.ERROR_USER_UNMATCH, (String)results.get("message"));
        }*/
        return ResultObj.success("");
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

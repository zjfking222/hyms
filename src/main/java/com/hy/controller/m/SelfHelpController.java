package com.hy.controller.m;

import com.hy.common.ResultObj;
import com.hy.common.SecurityUtil;
import com.hy.config.shiro.RedisCacheManager;
import com.hy.config.shiro.ShiroUserInfo;
import com.hy.dto.*;
import com.hy.enums.ResultCode;
import com.hy.model.Checkinout;
import com.hy.service.m.SelfHelpService;
import com.hy.utils.DateUtil;
import com.sap.conn.jco.JCoFunction;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.cache.Cache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletResponse;
import java.io.Serializable;
import java.text.SimpleDateFormat;
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
        //应用的身份凭证
        String accessToken = data.get("access_token");
        //更新身份凭证
        String refreshToken = data.get("refresh_token");
        //access_token的生效时间
        String issuedTime = data.get("issued_time");
        //access_token的过期时间
        String expireTime = data.get("expire_time");
        //当前登录用户的标识(目前系统只需要一个)
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
        String month = "11";//c.get(Calendar.MONTH) + 1 + "";此处需要保证当前月份放在最上面----------------------------------------------------------------------------------------------------------------------------
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
        List<SapTechTitleDto> tt = selfHelpService.getTechTitleInfo(function);
        //取资格证书信息
        List<SapQuaCertificateDto> qc = selfHelpService.getQuaCertificateInfo(function);
        //整合数据
        Map<String, Object> result = new HashMap<>();
        result.put("base", base);
        result.put("ht", ht);
        result.put("tt", tt);
        result.put("qc", qc);
        return ResultObj.success(result);
    }

    /**
     * @Author 钱敏杰
     * @Description 获取当前月份的排班表数据
     * @Date 2018/11/10 14:03
     * @Param [data]
     * @return com.hy.common.ResultObj
     **/
    @PostMapping("/getSchedulingInfo")
    public ResultObj getSchedulingInfo(@RequestBody Map<String, String> data){
        ShiroUserInfo hrmResource = SecurityUtil.getUserInfo();
        String year = data.get("year");
        String month = data.get("month");
        //计算出本月第一天与最后一天的日期
        Date d1 = DateUtil.getFirstDayOfMonth(Integer.parseInt(year), Integer.parseInt(month)).getTime();
        Date d2 = DateUtil.getLastDayOfMonth(Integer.parseInt(year), Integer.parseInt(month)).getTime();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        //获取排班表数据
        List<SapSchedulingDto> list = selfHelpService.getSchedulingInfo("100496", sdf.format(d1), sdf.format(d2));
        Map<String, SapSchedulingDto> map = new HashMap<>();
        //整理数据格式，使其以map形式返回，key为这天的去零字符串
        if(list != null && list.size() >0){
            for(SapSchedulingDto dto:list){
                String day = dto.getZrq();
                day = day.substring(day.length()-2, day.length());
                Integer d = Integer.parseInt(day);
                map.put(d.toString(), dto);
            }
        }
        return ResultObj.success(map);
    }

    /**
     * @Author 钱敏杰
     * @Description 获取年休假调休数据
     * @Date 2018/11/8 11:15
     * @Param []
     * @return com.hy.common.ResultObj
     **/
    @PostMapping("/getVacationInfo")
    public ResultObj getVacationInfo(){
        ShiroUserInfo hrmResource = SecurityUtil.getUserInfo();
        JCoFunction function = selfHelpService.getEmployeeFunction("100496");
        List<SapVacationDto> list = selfHelpService.getVacationInfo(function);
        return ResultObj.success(list);
    }

    /**
     * @Author 钱敏杰
     * @Description 获取当前月份的打卡记录数据
     * @Date 2018/11/10 14:02
     * @Param [data]
     * @return com.hy.common.ResultObj
     **/
    @PostMapping("/getRecord")
    public ResultObj getRecord(@RequestBody Map<String, String> data){
        ShiroUserInfo hrmResource = SecurityUtil.getUserInfo();
        String year = data.get("year");
        String month = data.get("month");
        //计算出本月第一天与最后一天的日期
        Calendar d1 = DateUtil.getFirstDayOfMonth(Integer.parseInt(year), Integer.parseInt(month));
        //当天最初一秒
        d1.set(Calendar.HOUR_OF_DAY, 0);
        d1.set(Calendar.MINUTE, 0);
        d1.set(Calendar.SECOND, 0);
        Calendar d2 = DateUtil.getLastDayOfMonth(Integer.parseInt(year), Integer.parseInt(month));
        //当天最后一秒
        d2.set(Calendar.HOUR_OF_DAY, 23);
        d2.set(Calendar.MINUTE, 59);
        d2.set(Calendar.SECOND, 59);
        //获取原始打卡记录数据
        Map<String, List<Checkinout>> map = selfHelpService.getRecord("100857", d1.getTime(), d2.getTime());//------------------------------------------------------------------------------写死数据暂未修改
        //整理数据
        Map<String, SelfRecordDto> result = selfHelpService.arrangeRecordData("100857", map);
        //返回数据到页面
        return ResultObj.success(result);
    }
}

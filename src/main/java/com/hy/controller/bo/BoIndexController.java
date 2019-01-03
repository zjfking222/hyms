package com.hy.controller.bo;

import com.hy.common.ResultObj;
import com.hy.common.SecurityUtil;
import com.hy.dto.BOCatalogueDto;
import com.hy.dto.BOInfoDto;
import com.hy.enums.ResultCode;
import com.hy.service.bo.BoConfigService;
import com.hy.service.bo.BoIndexService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Auther: 钱敏杰
 * @Date: 2018/12/25 9:02
 * @Description: BO报表登录相关controller
 */
@RestController
public class BoIndexController {

    private static final Logger logger = LoggerFactory.getLogger(BoIndexController.class);
    @Autowired
    private BoIndexService boIndexService;
    @Autowired
    private BoConfigService boConfigService;

    /**
     * @Author 钱敏杰
     * @Description BI平台登录
     * @Date 2018/12/25 13:54
     * @Param [logininfo]
     * @return com.hy.common.ResultObj
     **/
    @RequestMapping(value = "/bo/index/login", method = RequestMethod.POST)
    public ResultObj login(@RequestBody Map<String, String> logininfo) {
        Subject subject = SecurityUtils.getSubject();
        //检查当前用户是否已配置角色或者BO账号
        if(boIndexService.checkPermission(logininfo.get("loginid"))){
            UsernamePasswordToken token = new UsernamePasswordToken(logininfo.get("loginid"), logininfo.get("password"));
            try {
                //登录
                subject.login(token);
            } catch (UnknownAccountException lae) {
                token.clear();
                return ResultObj.error(ResultCode.ERROR_USER_UNEXISTS);
            } catch (AuthenticationException e) {
                token.clear();
                return ResultObj.error(ResultCode.ERROR_USER_UNMATCH, logininfo.get("password"));
            }
            return ResultObj.success();
        }else{
            //无权限，不予访问
            return ResultObj.error(ResultCode.ERROR_FUNCTION_NO_ACCESS);
        }
    }

    /**
     * @Author 钱敏杰
     * @Description 获取当前用户的报表目录目录树
     * @Date 2018/12/26 8:54
     * @Param []
     * @return com.hy.common.ResultObj
     **/
    @RequestMapping(value = "/bo/index/getEmpCatalogue", method = RequestMethod.POST)
    public ResultObj getEmpCatalogue() {
        String loginid =  SecurityUtil.getLoginid();
        List<BOCatalogueDto> catalogues = boConfigService.getCatalogueByEmp(loginid);
        Map<String, Object> map = new HashMap<>();
        map.put("username", SecurityUtil.getUserName());
        map.put("menus", catalogues);
        return ResultObj.success(map);
    }

    /**
     * @Author 钱敏杰
     * @Description 分页查询当前用户的报表目录下的报表
     * @Date 2018/12/26 11:07
     * @Param [page, pageSize, value, sort, dir, directoryid]
     * @return com.hy.common.ResultObj
     **/
    @RequestMapping("/bo/index/getDisplayReport")
    public ResultObj getDisplayReport(@RequestParam String directoryid) {
        String loginid =  SecurityUtil.getLoginid();
        List<BOInfoDto> list =  boIndexService.getDisplayReport(directoryid, loginid);
        return ResultObj.success(list);
    }

    /**
     * @Author 钱敏杰
     * @Description 打开当前BO报表
     * @Date 2019/1/3 8:13
     * @Param [directoryid]
     * @return com.hy.common.ResultObj
     **/
    @RequestMapping("/bo/index/getRealReport")
    public void getRealReport(@RequestParam String reportid, HttpServletResponse response) {
        String url =  boIndexService.getRealReport(reportid);
        try {
            response.sendRedirect(url);
        } catch (IOException e) {
            logger.error("重定向到"+ url +"失败", e);
        }
    }
}

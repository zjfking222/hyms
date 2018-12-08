package com.hy.controller.bo;

import com.hy.common.ResultObj;
import com.hy.dto.HrmResourceDto;
import com.hy.dto.ReportAccountDto;
import com.hy.dto.SysRolesUserDto;
import com.hy.enums.ResultCode;
import com.hy.model.ReportInfo;
import com.hy.service.bo.BoConfigService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Auther: 钱敏杰
 * @Date: 2018/12/3 8:36
 * @Description:BO报表权限配置controller
 */
@RestController
@RequestMapping(value = "/bo")
public class BoConfigController {
    @Autowired
    private BoConfigService boConfigService;

    //报表列表
    @RequestMapping("/getReportInfo")
    public ResultObj getReportInfo(int page, int pageSize, @RequestParam(required = false) String value, @RequestParam(required = false) String sort,
                                   @RequestParam(required = false) String dir) {
        Map<String, Object> map = new HashMap<>();
        map.put("data", boConfigService.getReportInfo(page, pageSize, value, sort, dir));
        map.put("total", boConfigService.getReportInfoTotal(value));
        return ResultObj.success(map);
    }

    @RequestMapping("/addReportInfo")
    public ResultObj addReportInfo(ReportInfo reportInfo){
        return boConfigService.addReportInfo(reportInfo)?
                ResultObj.success():
                ResultObj.error(ResultCode.ERROR_ADD_FAILED);
    }

    @RequestMapping("/setReportInfo")
    public ResultObj setReportInfo(ReportInfo reportInfo){
        return boConfigService.setReportInfo(reportInfo)?
                ResultObj.success():
                ResultObj.error(ResultCode.ERROR_UPDATE_FAILED);
    }

    @RequestMapping("/delReportInfo")
    public ResultObj delReportInfo(int id){
        return boConfigService.delReportInfo(id)?
                ResultObj.success():
                ResultObj.error(ResultCode.ERROR_DELETE_FAILED);
    }

    /**
     * @Author 钱敏杰
     * @Description 根据accountid查询BO账号
     * @Date 2018/12/3 14:07
     * @Param [map]
     * @return com.hy.common.ResultObj
     **/
    @RequestMapping(value = "/account/getAccount", method = RequestMethod.POST)
    public ResultObj getAccount(String accountid) {
        if(StringUtils.isEmpty(accountid)){
            accountid = null;
        }
        List<ReportAccountDto> result = boConfigService.selectByAccountid(accountid);
        return ResultObj.success(result);
    }

    /**
     * @Author 钱敏杰
     * @Description 添加或更新BO账号
     * @Date 2018/12/3 17:05
     * @Param [dto]
     * @return com.hy.common.ResultObj
     **/
    @RequestMapping(value = "/account/updateAccount", method = RequestMethod.POST)
    public ResultObj updateAccount(ReportAccountDto dto) {
        if(dto.getId() == null){//执行添加
            int i = boConfigService.addAccount(dto);
            if(i<1){
                return ResultObj.error(ResultCode.ERROR_ADD_FAILED);
            }
        }else{//执行更新
            int i = boConfigService.updateAccount(dto);
            if(i<1){
                return ResultObj.error(ResultCode.ERROR_UPDATE_FAILED);
            }
        }
        return ResultObj.success();
    }

    /**
     * @Author 钱敏杰
     * @Description 删除BO账号
     * @Date 2018/12/4 10:11
     * @Param [id]
     * @return com.hy.common.ResultObj
     **/
    @RequestMapping(value = "/account/deleteAccount", method = RequestMethod.POST)
    public ResultObj deleteAccount(String id) {
        Integer nid = Integer.parseInt(id);
        int i = boConfigService.deleteAccount(nid);
        if(i<1){
            return ResultObj.error(ResultCode.ERROR_DELETE_FAILED);
        }
        return ResultObj.success();
    }

    /**
     * @Author 钱敏杰
     * @Description 根据BO账号查询配置的相关员工数据
     * @Date 2018/12/4 14:57
     * @Param [accountid]
     * @return com.hy.common.ResultObj
     **/
    @RequestMapping(value = "/account/getADUsers", method = RequestMethod.POST)
    public ResultObj getADUsers(String accountid) {
        List<HrmResourceDto> results = boConfigService.getUsersByAccountid(accountid);
        return ResultObj.success(results);
    }

    /**
     * @Author 钱敏杰
     * @Description 保存BO账号与AD域账号的对应关系
     * @Date 2018/12/8 9:14
     * @Param [dto]
     * @return com.hy.common.ResultObj
     **/
    @RequestMapping(value = "/account/saveAccAdRelation", method = RequestMethod.POST)
    public ResultObj saveAccAdRelation(@RequestBody SysRolesUserDto dto) {
        boConfigService.buildRelation(dto);
        return ResultObj.success();
    }

    @RequestMapping(value = "/account/genAllReportTree", method = RequestMethod.POST)
    public ResultObj genAllReportTree() {
        boConfigService.genAllReportTree();
        return ResultObj.success();
    }

}

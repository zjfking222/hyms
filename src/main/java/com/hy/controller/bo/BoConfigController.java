package com.hy.controller.bo;

import com.hy.common.ResultObj;
import com.hy.dto.*;
import com.hy.enums.ResultCode;
import com.hy.model.BOInfo;
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
                                   @RequestParam(required = false) String dir, String directoryid) {
        Map<String, Object> map = new HashMap<>();
        map.put("data", boConfigService.getReportInfo(page, pageSize, value, sort, dir, directoryid));
        map.put("total", boConfigService.getReportInfoTotal(value, directoryid));
        return ResultObj.success(map);
    }

    @RequestMapping("/addReportInfo")
    public ResultObj addReportInfo(BOInfo reportInfo){
        return boConfigService.addReportInfo(reportInfo)?
                ResultObj.success():
                ResultObj.error(ResultCode.ERROR_ADD_FAILED);
    }

    @RequestMapping("/setReportInfo")
    public ResultObj setReportInfo(BOInfo reportInfo){
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
        List<BOAccountDto> result = boConfigService.selectByAccountid(accountid);
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
    public ResultObj updateAccount(BOAccountDto dto) {
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
        boConfigService.deleteAccount(id);
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

    /**
     * @Author 钱敏杰
     * @Description 获取当前BO账号的所有报表授权
     * @Date 2018/12/8 15:33
     * @Param [accountid]
     * @return com.hy.common.ResultObj
     **/
    @RequestMapping(value = "/account/getAllReportTree", method = RequestMethod.POST)
    public ResultObj getAllReportTree(String accountid) {
        //所有数据树
        List<BOCatalogueDto> tree = boConfigService.getAllReportTree(accountid);
        return ResultObj.success(tree);
    }

    /**
     * @Author 钱敏杰
     * @Description 获取当前BO账号的已授权数据以及当前员工的报表授权
     * @Date 2018/12/10 9:29
     * @Param [accountid, empnum]
     * @return com.hy.common.ResultObj
     **/
    @RequestMapping(value = "/account/getAccReportTree", method = RequestMethod.POST)
    public ResultObj getAccReportTree(String accountid, String empnum) {
        //所有数据树
        List<BOCatalogueDto> tree = boConfigService.getAccReportTree(accountid, empnum);
        return ResultObj.success(tree);
    }

    /**
     * @Author 钱敏杰
     * @Description 修改BO账号报表权限
     * @Date 2018/12/11 9:08
     * @Param [catalogueDto]
     * @return com.hy.common.ResultObj
     **/
    @PostMapping("/account/saveAccountReport")
    public ResultObj saveAccountReport(@RequestBody BOCatalogueDto catalogueDto){
        boConfigService.saveAccountReport(catalogueDto);
        return ResultObj.success();
    }

    /**
     * @Author 钱敏杰
     * @Description 修改员工BO账号权限
     * @Date 2018/12/11 10:39
     * @Param [catalogueDto]
     * @return com.hy.common.ResultObj
     **/
    @PostMapping("/account/saveEmpReport")
    public ResultObj saveEmpReport(@RequestBody BOCatalogueDto catalogueDto){
        boConfigService.saveEmpReport(catalogueDto);
        return ResultObj.success();
    }

    /**
     * @Author 钱敏杰
     * @Description 查询已绑定BO账号的员工信息
     * @Date 2018/12/12 14:30
     * @Param [empnum]
     * @return com.hy.common.ResultObj
     **/
    @PostMapping("/account/getAccountEmp")
    public ResultObj getAccountEmp(String empnum){
        List<BOAccadRelationDto> owner = boConfigService.getAccountEmp(empnum);
        return ResultObj.success(owner);
    }

    /**
     * @Author 钱敏杰
     * @Description 查询当前员工的所有报表权限及其值
     * @Date 2018/12/12 16:04
     * @Param [empnum]
     * @return com.hy.common.ResultObj
     **/
    @PostMapping("/account/getReportByEmp")
    public ResultObj getReportByEmp(String empnum){
        List<BOCatalogueDto> tree = boConfigService.getReportTreeByEmp(empnum);
        return ResultObj.success(tree);
    }

    /**
     * @Author 钱敏杰
     * @Description 更改人员权限
     * @Date 2018/12/13 17:11
     * @Param [catalogueDto]
     * @return com.hy.common.ResultObj
     **/
    @PostMapping("/account/updateAllByEmp")
    public ResultObj updateAllByEmp(@RequestBody BOCatalogueDto catalogueDto){
        boConfigService.saveEmpReport(catalogueDto);
        return ResultObj.success();
    }
    /**
     * @Author 沈超宇
     * @Description 角色查删controller
     * @Date 2018/12/13 10:38
     **/
    @PostMapping("/role/getRole")
    public ResultObj getRole(@RequestParam(required = false) String value){
        List<BORoleDto> boRoleDtosList = boConfigService.getRole(value);
        return ResultObj.success(boRoleDtosList);
    }


    @PostMapping("/role/delRole")
    public ResultObj delRole(int id){
        return boConfigService.delRole(id) ?
                ResultObj.success() :
                ResultObj.error(ResultCode.ERROR_DELETE_FAILED);
    }

    /**
     * @Author 沈超宇
     * @Description 角色AD域员工号关系表增删查(包含角色BO账号增删、角色表增删改)controller
     * @Date 2018/12/13 17:26
     **/
    @PostMapping("/roleAd/getRoleAd")
    public ResultObj getRoleAd(int rid){
        return ResultObj.success(boConfigService.getRoleAd(rid));
    }

    @PostMapping("/roleAd/addRoleAd")
    public ResultObj addRoleAd(@RequestBody SysRolesUserDto dto){
        return boConfigService.addRoleAd(dto) ?
                ResultObj.success() :
                ResultObj.error(ResultCode.ERROR_ADD_FAILED);
    }

    @PostMapping("/roleAd/setRoleAd")
    public ResultObj setRoleAd(@RequestBody SysRolesUserDto dto){
        return boConfigService.setRoleAd(dto) ?
                ResultObj.success() :
                ResultObj.error(ResultCode.ERROR_UPDATE_FAILED);
    }

    /**
     * @Author 沈超宇
     * @Description 角色、BO账号关系表查询controller
     * @Date 2018/12/15 8:59
     **/
    @PostMapping("/roleAccount/getRoleAcc")
    public ResultObj getRoleAcc(int rid){
        return ResultObj.success(boConfigService.getRoleAccount(rid));
    }

    /**
     * @Author 沈超宇
     * @Description 角色、报表关系表增删查controller
     * @Date 2018/12/17 10:33
     **/
    //查询并生成树
    @PostMapping("/roleReport/getRoleReport")
    public ResultObj getRoleReportTree(int rid, String accountid){
        List<BOCatalogueDto> tree = boConfigService.getRoleReportTree(rid, accountid);
        return ResultObj.success(tree);
    }

    //增删角色报表
    @PostMapping("/roleReport/setRoleReport")
    public ResultObj setRoleReport(@RequestBody BOCatalogueDto catalogueDto){
        boConfigService.saveRoleReport(catalogueDto);
        return ResultObj.success();
    }

    /**
     * @Author 钱敏杰
     * @Description 获取当前账号的配置人员数量
     * @Date 2018/12/13 17:17
     * @Param [accountid]
     * @return com.hy.common.ResultObj
     **/
    @PostMapping("/account/getAccEmpCount")
    public ResultObj getAccEmpCount(String accountid){
        int i = boConfigService.getAccEmpCount(accountid);
        return ResultObj.success(i);
    }

    /**
     * @Author 钱敏杰
     * @Description 获取全部报表目录
     * @Date 2018/12/14 9:52
     * @Param []
     * @return com.hy.common.ResultObj
     **/
    @PostMapping("/account/getAllCatalogue")
    public ResultObj getAllCatalogue(){
        List<BOCatalogueDto> list = boConfigService.getAllCatalogue();
        return ResultObj.success(list);
    }

    /**
     * @Author 钱敏杰
     * @Description 更改目录数据
     * @Date 2018/12/17 9:38
     * @Param [dto]
     * @return com.hy.common.ResultObj
     **/
    @PostMapping("/account/updateCatalogue")
    public ResultObj updateCatalogue(BOCatalogueDto dto){
        boConfigService.updateCatalogue(dto);
        return ResultObj.success();
    }

    /**
     * @Author 钱敏杰
     * @Description 查询当前目录下的报表数目
     * @Date 2018/12/18 11:05
     * @Param [id]
     * @return com.hy.common.ResultObj
     **/
    @PostMapping("/account/getReportInfoCount")
    public ResultObj getReportInfoCount(String id) {
        Integer num = boConfigService.getReportInfoTotal(null, id);
        return ResultObj.success(num);
    }

    /**
     * @Author 钱敏杰
     * @Description 删除目录数据
     * @Date 2018/12/17 16:04
     * @Param [id]
     * @return com.hy.common.ResultObj
     **/
    @PostMapping("/account/deleteCatalogue")
    public ResultObj deleteCatalogue(String id){
        boConfigService.deleteCatalogue(id);
        return ResultObj.success();
    }

    /**
     * @Author 钱敏杰
     * @Description 获取当前报表id的报表数目
     * @Date 2018/12/18 14:22
     * @Param [reportid]
     * @return com.hy.common.ResultObj
     **/
    @PostMapping("/account/getReportInfoCountByReportid")
    public ResultObj getReportInfoCountByReportid(String reportid) {
        Integer num = boConfigService.getInfoCount(reportid);
        return ResultObj.success(num);
    }
}

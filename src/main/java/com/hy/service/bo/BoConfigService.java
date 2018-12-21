package com.hy.service.bo;

import com.hy.dto.*;
import com.hy.model.BOInfo;
import com.hy.model.BORoleAd;

import java.util.List;

/**
 * @Auther: 钱敏杰
 * @Date: 2018/12/3 8:37
 * @Description:BO报表权限配置服务方法接口
 */
public interface BoConfigService {
    List<BOInfo> getReportInfo(int pageNum, int pageSize, String value, String sort, String dir, String directoryid);
    Integer getReportInfoTotal(String value, String directoryid);
    boolean addReportInfo(BOInfo reportInfos);
    boolean setReportInfo(BOInfo reportInfo);
    boolean delReportInfo(int id);
    /**
     * @Author 钱敏杰
     * @Description 根据accountid查询BO账号
     * @Date 2018/12/3 16:42
     * @Param [accountid]
     * @return java.util.List<com.hy.dto.ReportAccountDto>
     **/
    List<BOAccountDto> selectByAccountid(String accountid);

    /**
     * @Author 钱敏杰
     * @Description 新增BO账号
     * @Date 2018/12/4 10:30
     * @Param [dto]
     * @return int
     **/
    int addAccount(BOAccountDto dto);

    /**
     * @Author 钱敏杰
     * @Description 更新BO账号信息
     * @Date 2018/12/3 17:04
     * @Param [dto]
     * @return int
     **/
    int updateAccount(BOAccountDto dto);

    /**
     * @Author 钱敏杰
     * @Description 根据主键删除BO账号
     * @Date 2018/12/12 10:23
     * @Param [id]
     * @return void
     **/
    void deleteAccount(String id);

    /**
     * @Author 钱敏杰
     * @Description 根据BO账号查询AD域员工账号信息
     * @Date 2018/12/6 14:10
     * @Param [accountid]
     * @return java.util.List<com.hy.dto.HrmResourceDto>
     **/
    List<HrmResourceDto> getUsersByAccountid(String accountid);

    /**
     * @Author 钱敏杰
     * @Description 保存BO账号与AD域账号的关系数据
     * @Date 2018/12/6 15:23
     * @Param [dto]
     * @return void
     **/
    void buildRelation(SysRolesUserDto dto);

    /**
     * @Author 钱敏杰
     * @Description 从BO账号中删除该人员
     * @Date 2018/12/19 16:16
     * @Param [accountid, empnum]
     * @return void
     **/
    void deleteEmpAccount(String accountid, String empnum);

    /**
     * @Author 钱敏杰
     * @Description 获取全部目录及报表树，以及当前BO账号选中的报表
     * @Date 2018/12/10 14:21
     * @Param [accountid]
     * @return java.util.List<com.hy.dto.ReportCatalogueDto>
     **/
    List<BOCatalogueDto> getAllReportTree(String accountid);

    /**
     * @Author 钱敏杰
     * @Description 根据BO账户获取关联的BO报表
     * @Date 2018/12/10 14:19
     * @Param [accountid]
     * @return java.util.List<com.hy.model.ReportInfo>
     **/
    List<BOInfo> getReportInfoByAcc(String accountid);

    /**
     * @Author 钱敏杰
     * @Description 获取当前BO账号下的目录及报表树，以及当前员工选中的报表
     * @Date 2018/12/10 14:17
     * @Param [accountid, empnum]
     * @return java.util.List<com.hy.dto.ReportCatalogueDto>
     **/
    List<BOCatalogueDto> getAccReportTree(String accountid, String empnum);

    /**
     * @Author 钱敏杰
     * @Description 根据员工号与BO账号获取关联的BO报表
     * @Date 2018/12/11 11:23
     * @Param [empnum]
     * @return java.util.List<com.hy.model.ReportInfo>
     **/
    List<BOInfo> getReportInfoByEmp(String empnum);

    /**
     * @Author 钱敏杰
     * @Description 新增或删除BO账号与报表的关联数据
     * @Date 2018/12/11 9:07
     * @Param [catalogueDto]
     * @return void
     **/
    void saveAccountReport(BOCatalogueDto catalogueDto);

    /**
     * @Author 钱敏杰
     * @Description 新增或删除员工号与报表的关联数据
     * @Date 2018/12/11 10:38
     * @Param [catalogueDto]
     * @return void
     **/
    void saveEmpReport(BOCatalogueDto catalogueDto);

    /**
     * @Author 钱敏杰
     * @Description 根据员工号或员工姓名查询所有已关联的员工信息
     * @Date 2018/12/12 14:22
     * @Param [empnum]
     * @return java.util.List<com.hy.dto.ReportAccadRelationDto>
     **/
    List<BOAccadRelationDto> getAccountEmp(String empnum);

    /**
     * @Author 钱敏杰
     * @Description 获取当前员工绑定的BO账号id数据
     * @Date 2018/12/20 14:01
     * @Param [empnum]
     * @return java.util.List<java.lang.String>
     **/
    List<String> getEmpAccounts(String empnum);

    /**
     * @Author 钱敏杰
     * @Description 保存人员拥有的BO账号权限变化
     * @Date 2018/12/20 16:05
     * @Param [dto]
     * @return void
     **/
    void saveEmpAccounts(BOEmpAccountDto dto);

    /**
     * @Author 钱敏杰
     * @Description 删除BO操作人员及其相关权限
     * @Date 2018/12/20 17:09
     * @Param [empnum]
     * @return void
     **/
    void deleteEmp(String empnum);

    /**
     * @Author 钱敏杰
     * @Description 查询当前员工所有BO账号下的报表权限树及其选中值
     * @Date 2018/12/12 16:02
     * @Param [empnum]
     * @return java.util.List<com.hy.dto.ReportCatalogueDto>
     **/
    List<BOCatalogueDto> getReportTreeByEmp(String empnum);

    /**
     * @Author 沈超宇
     * @Description 角色增删改查service
     * @Date 2018/12/13 10:38
     **/
    List<BORoleDto> getRole(String value);
    boolean delRole(int id);
    List<BORoleDto> getRoleByAcc(String accountid);//根据BO账号查询对应的角色信息

    /**
     * @Author 沈超宇
     * @Description 角色、AD员工号关系表增删改查service
     * @Date 2018/12/13 17:00
     **/
    List<HrmResourceDto> getRoleAd(int rid);
    boolean addRoleAd(SysRolesUserDto dto);
    boolean setRoleAd(SysRolesUserDto dto);

    /**
     * @Author 沈超宇
     * @Description 角色、BO账号关系表增删查service
     * @Date 2018/12/15 8:36
     **/
    List<BORoleAccountDto> getRoleAccount(int rid);

    /**
     * @Author 沈超宇
     * @Description 角色、BO账号关系表根据角色BO账号删除对应数据service
     * @Date 2018/12/19 17:02
     **/
    boolean delByRidAcc(int rid, String accountid);

    /**
     * @Author 沈超宇
     * @Description 角色、报表关系表增删查service
     * @Date 2018/12/17 10:28
     **/
    //查询并生成树
    List<BOCatalogueDto> getRoleReportTree(int rid);
    //增删角色报表
    void saveRoleReport(BOCatalogueDto catalogueDto);

    /**
     * @Author 沈超宇
     * @Description 查询当前角色所拥有的报表service
     * @Date 2018/12/17 11:10
     **/
    List<BOInfo> getReportInfoByRid(int rid);

    /**
     * @Author 钱敏杰
     * @Description 获取当前BO账号下的人员数量
     * @Date 2018/12/13 17:16
     * @Param [accountid]
     * @return int
     **/
    int getAccEmpCount(String accountid);

    /**
     * @Author 钱敏杰
     * @Description 获取全部报表目录
     * @Date 2018/12/14 9:51
     * @Param []
     * @return java.util.List<com.hy.dto.BOCatalogueDto>
     **/
    List<BOCatalogueDto> getAllCatalogue();

    /**
     * @Author 钱敏杰
     * @Description 新增或更新目录
     * @Date 2018/12/17 9:35
     * @Param [dto]
     * @return void
     **/
    void updateCatalogue(BOCatalogueDto dto);

    /**
     * @Author 钱敏杰
     * @Description 删除目录
     * @Date 2018/12/17 16:03
     * @Param [id]
     * @return void
     **/
    void deleteCatalogue(String id);

    /**
     * @Author 钱敏杰
     * @Description 获取当前报表id的报表数量
     * @Date 2018/12/18 14:19
     * @Param [reportid]
     * @return java.lang.Integer
     **/
    Integer getInfoCount(String reportid);
}

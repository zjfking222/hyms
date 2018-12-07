package com.hy.service.bo;

import com.hy.dto.HrmResourceDto;
import com.hy.dto.ReportAccountDto;
import com.hy.dto.SysRolesUserDto;
import com.hy.model.ReportInfo;

import java.util.List;

/**
 * @Auther: 钱敏杰
 * @Date: 2018/12/3 8:37
 * @Description:BO报表权限配置服务方法接口
 */
public interface BoConfigService {
    List<ReportInfo> getReportInfo(int pageNum, int pageSize, String value, String sort, String dir);
    Integer getReportInfoTotal(String value);
    boolean addReportInfo(ReportInfo reportInfos);
    boolean setReportInfo(ReportInfo reportInfo);
    boolean delReportInfo(int id);
    /**
     * @Author 钱敏杰
     * @Description 根据accountid查询BO账号
     * @Date 2018/12/3 16:42
     * @Param [accountid]
     * @return java.util.List<com.hy.dto.ReportAccountDto>
     **/
    List<ReportAccountDto> selectByAccountid(String accountid);

    /**
     * @Author 钱敏杰
     * @Description 新增BO账号
     * @Date 2018/12/4 10:30
     * @Param [dto]
     * @return int
     **/
    int addAccount(ReportAccountDto dto);

    /**
     * @Author 钱敏杰
     * @Description 更新BO账号信息
     * @Date 2018/12/3 17:04
     * @Param [dto]
     * @return int
     **/
    int updateAccount(ReportAccountDto dto);

    /**
     * @Author 钱敏杰
     * @Description 根据主键删除BO账号
     * @Date 2018/12/4 10:09
     * @Param [id]
     * @return int
     **/
    int deleteAccount(Integer id);

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
}

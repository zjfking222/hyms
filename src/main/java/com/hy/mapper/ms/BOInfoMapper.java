package com.hy.mapper.ms;

import com.hy.model.BOInfo;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Auther: 钱敏杰
 * @Date: 2018/12/3 9:49
 * @Description:报表信息数据库操作
 */
@Repository
public interface BOInfoMapper {
    List<BOInfo> selectReport(@Param("filters")String filters, @Param("value") String value, @Param("sort") String sort, @Param("dir") String dir, @Param("directoryid") String directoryid);
    Integer selectReportAll(@Param("filters")String filters, @Param("value") String value, @Param("directoryid") String directoryid);
    Integer insertReport(BOInfo reportInfos);
    Integer updateReport(BOInfo reportInfo);
    Integer deleteReport(@Param("id") int id);

    /**
     * @Author 钱敏杰
     * @Description 根据reportid删除报表信息
     * @Date 2018/12/24 16:51
     * @Param [reportid]
     * @return int
     **/
    int deleteReportByrid(@Param("reportid") String reportid);

    /**
     * @Author 钱敏杰
     * @Description 根据BO账号id查询关联报表信息
     * @Date 2018/12/8 11:00
     * @Param [accountid]
     * @return java.util.List<com.hy.model.ReportInfo>
     **/
    List<BOInfo> selectByAccountid(@Param("accountid") String accountid);

    /**
     * @Author 钱敏杰
     * @Description 获取当前员工所具有的BO账号下的所有报表
     * @Date 2018/12/12 15:39
     * @Param [empnum]
     * @return java.util.List<com.hy.model.ReportInfo>
     **/
    List<BOInfo> selectAllByEmpAcc(@Param("empnum") String empnum);

    /**
     * @Author 钱敏杰
     * @Description 查询当前人员所拥有的报表
     * @Date 2018/12/12 15:51
     * @Param [empnum]
     * @return java.util.List<com.hy.model.ReportInfo>
     **/
    List<BOInfo> selectOwnByEmp(@Param("empnum") String empnum);

    /**
     * @Author 沈超宇
     * @Description 获取当前角色所具有的BO账号下的所有报表
     * @Date 2018/12/19 9:17
     * @Param [rid]
     * @return java.util.List<com.hy.model.ReportInfo>
     **/
    List<BOInfo> selectAllByRole(@Param("rid") int rid);

    /**
     * @Author 沈超宇
     * @Description 查询当前角色所拥有的报表
     * @Date 2018/12/17 9:50
     * @Param [rid]
     * @return java.util.List<com.hy.model.ReportInfo>
     **/
    List<BOInfo> selectOwnByRole(@Param("rid") int rid);

    /**
     * @Author 钱敏杰
     * @Description 查询当前报表id的数据量
     * @Date 2018/12/18 14:16
     * @Param [reportid]
     * @return java.lang.Integer
     **/
    Integer countInfoByReportid(@Param("reportid") String reportid);

    /**
     * @Author 钱敏杰
     * @Description //TODO
     * @Date 2018/12/25 17:17
     * @Param [empnum]
     * @return java.util.List<com.hy.model.BOInfo>
     **/
    List<BOInfo> selectAllByEmpRole(@Param("empnum") String empnum);

    /**
     * @Author 钱敏杰
     * @Description 分页查询当前员工相应目录下所拥有的报表
     * @Date 2018/12/26 10:20
     * @Param [directoryid, empnum]
     * @return java.util.List<com.hy.model.BOInfo>
     **/
    List<BOInfo> selectEmpReport(@Param("directoryid") String directoryid, @Param("empnum") String empnum);
}

package com.hy.mapper.ms;

import com.hy.model.ReportInfo;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Auther: 钱敏杰
 * @Date: 2018/12/3 9:49
 * @Description:报表信息数据库操作
 */
@Repository
public interface ReportInfoMapper {
    List<ReportInfo> selectReport(@Param("value") String value,@Param("sort") String sort, @Param("dir") String dir);
    Integer selectReportAll(@Param("value") String value);
    Integer insertReport(ReportInfo reportInfos);
    Integer updateReport(ReportInfo reportInfo);
    Integer deleteReport(@Param("id") int id);

    /**
     * @Author 钱敏杰
     * @Description 根据BO账号id查询关联报表信息
     * @Date 2018/12/8 11:00
     * @Param [accountid]
     * @return java.util.List<com.hy.model.ReportInfo>
     **/
    List<ReportInfo> selectByAccountid(@Param("accountid") String accountid);

    /**
     * @Author 钱敏杰
     * @Description 根据员工号查询关联报表信息
     * @Date 2018/12/10 9:25
     * @Param [empnum]
     * @return java.util.List<com.hy.model.ReportInfo>
     **/
    List<ReportInfo> selectByEmp(@Param("empnum") String empnum);

}

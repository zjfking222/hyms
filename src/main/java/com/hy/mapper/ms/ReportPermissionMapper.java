package com.hy.mapper.ms;

import com.hy.model.ReportPermission;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Auther: 钱敏杰
 * @Date: 2018/12/3 9:46
 * @Description:报表权限数据库操作
 */
@Repository
public interface ReportPermissionMapper {

    /**
     * @Author 钱敏杰
     * @Description 根据员工号、BO账号与报表id删除关联数据
     * @Date 2018/12/11 11:19
     * @Param [empnum, reportid, accountid]
     * @return int
     **/
    int deleteByEmpReport(@Param("empnum") String empnum, @Param("reportid") String reportid, @Param("accountid") String accountid);

    /**
     * @Author 钱敏杰
     * @Description 批量插入关联数据
     * @Date 2018/12/11 10:33
     * @Param [list]
     * @return int
     **/
    int insertEmpReportBatch(List<ReportPermission> list);
}

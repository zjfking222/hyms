package com.hy.mapper.ms;

import com.hy.model.BOPermission;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Auther: 钱敏杰
 * @Date: 2018/12/3 9:46
 * @Description:报表权限数据库操作
 */
@Repository
public interface BOPermissionMapper {

    /**
     * @Author 钱敏杰
     * @Description 根据员工号与报表id删除关联数据
     * @Date 2018/12/11 11:19
     * @Param [empnum, reportid]
     * @return int
     **/
    int deleteByEmpReport(@Param("empnum") String empnum, @Param("reportid") String reportid);

    /**
     * @Author 钱敏杰
     * @Description 删除员工当前BO账号下的所有已配置报表
     * @Date 2018/12/12 9:29
     * @Param [empnum]
     * @return int
     **/
    int deleteByEmp(@Param("empnum") String empnum);

    /**
     * @Author 钱敏杰
     * @Description 批量插入关联数据
     * @Date 2018/12/11 10:33
     * @Param [list]
     * @return int
     **/
    int insertEmpReportBatch(List<BOPermission> list);
}

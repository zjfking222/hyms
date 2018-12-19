package com.hy.mapper.ms;

import com.hy.model.BORoleReport;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Auther: 沈超宇
 * @Date: 2018/12/17 10:10
 * @Description:角色报表增删mapper
 */
@Repository
public interface BORoleReportMapper {
    //添加
    Integer insertRoleReport(List<BORoleReport> boRoleReports);
    //根据角色ID和报表id删除关联数据
    Integer deleteByRoleReport(@Param("rid") int rid, @Param("reportid") String reportid);
    //根据角色ID删除关联数据
    Integer deleteByRole(@Param("rid") int rid);
}

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
}

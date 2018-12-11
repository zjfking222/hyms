package com.hy.mapper.ms;

import com.hy.model.ReportAccInfo;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import java.util.List;

/**
 * @Auther: 钱敏杰
 * @Date: 2018/12/3 9:52
 * @Description:
 */
@Repository
public interface ReportAccInfoMapper {

    /**
     * @Author 钱敏杰
     * @Description 根据账户id和报表id删除关联数据
     * @Date 2018/12/6 10:57
     * @Param [accountid, empnum]
     * @return int
     **/
    int deleteByAccReport(@Param("accountid") String accountid, @Param("reportid") String reportid);

    /**
     * @Author 钱敏杰
     * @Description 批量插入关联数据
     * @Date 2018/12/6 15:11
     * @Param [list]
     * @return int
     **/
    int insertAccReportBatch(List<ReportAccInfo> list);
}

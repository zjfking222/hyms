package com.hy.mapper.ms;

import com.hy.model.ReportAccount;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Auther: 钱敏杰
 * @Date: 2018/12/3 9:51
 * @Description:BO账号数据库操作
 */
@Repository
public interface ReportAccountMapper extends BaseMapper<Integer, ReportAccount>{

    /**
     * @Author 钱敏杰
     * @Description 根据accountid查询bo账号，若accountid为null，则查询全部
     * @Date 2018/12/3 13:53
     * @Param [accountid]
     * @return java.util.List<com.hy.model.ReportAccount>
     **/
    List<ReportAccount> selectByAccountid(@Param("accountid")String accountid);

}

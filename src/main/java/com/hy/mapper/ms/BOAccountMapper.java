package com.hy.mapper.ms;

import com.hy.model.BOAccount;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Auther: 钱敏杰
 * @Date: 2018/12/3 9:51
 * @Description:BO账号数据库操作
 */
@Repository
public interface BOAccountMapper extends BaseMapper<Integer, BOAccount>{

    /**
     * @Author 钱敏杰
     * @Description 根据accountid查询bo账号，若accountid为null，则查询全部
     * @Date 2018/12/3 13:53
     * @Param [accountid]
     * @return java.util.List<com.hy.model.ReportAccount>
     **/
    List<BOAccount> selectByAccountid(@Param("accountid")String accountid);

    /**
     * @Author 钱敏杰
     * @Description 通过人员账号获取当前人员当前报表所能使用的BO账号信息
     * @Date 2019/1/2 17:14
     * @Param [reportid, empnum]
     * @return com.hy.model.BOAccount
     **/
    BOAccount getEmpReportAcc(@Param("reportid")String reportid, @Param("empnum")String empnum);

    /**
     * @Author 钱敏杰
     * @Description 通过人员角色获取当前人员当前报表所能使用的BO账号信息
     * @Date 2019/1/2 17:29
     * @Param [reportid, empnum]
     * @return com.hy.model.BOAccount
     **/
    BOAccount getRoleReportAcc(@Param("reportid")String reportid, @Param("empnum")String empnum);
}

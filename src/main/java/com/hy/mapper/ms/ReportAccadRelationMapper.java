package com.hy.mapper.ms;

import com.hy.model.ReportAccadRelation;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Auther: 钱敏杰
 * @Date: 2018/12/3 9:52
 * @Description:报表账号关系表数据库操作
 */
@Repository
public interface ReportAccadRelationMapper {

    /**
     * @Author 钱敏杰
     * @Description 根据accountid查询关联数据
     * @Date 2018/12/4 13:42
     * @Param []
     * @return java.util.List<com.hy.model.ReportAccadRelation>
     **/
    List<ReportAccadRelation> getListByAccountid(@Param("accountid")String accountid);

    /**
     * @Author 钱敏杰
     * @Description 根据员工号查询关联数据
     * @Date 2018/12/4 13:43
     * @Param []
     * @return java.util.List<com.hy.model.ReportAccadRelation>
     **/
    List<ReportAccadRelation> getListByEmpnum(@Param("empnum")String empnum);

    /**
     * @Author 钱敏杰
     * @Description 根据账户id和员工号删除关联数据
     * @Date 2018/12/6 10:57
     * @Param [accountid, empnum]
     * @return int
     **/
    int deleteByAccEmp(@Param("accountid")String accountid, @Param("empnum")String empnum);

    /**
     * @Author 钱敏杰
     * @Description 批量插入关联数据
     * @Date 2018/12/6 15:11
     * @Param [list]
     * @return int
     **/
    int insertBatch(List<ReportAccadRelation> list);
}

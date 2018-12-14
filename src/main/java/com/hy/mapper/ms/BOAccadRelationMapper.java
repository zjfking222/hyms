package com.hy.mapper.ms;

import com.hy.model.BOAccadRelation;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Auther: 钱敏杰
 * @Date: 2018/12/3 9:52
 * @Description:报表账号关系表数据库操作
 */
@Repository
public interface BOAccadRelationMapper {

    /**
     * @Author 钱敏杰
     * @Description 根据accountid查询关联数据
     * @Date 2018/12/4 13:42
     * @Param []
     * @return java.util.List<com.hy.model.ReportAccadRelation>
     **/
    List<BOAccadRelation> getListByAccountid(@Param("accountid")String accountid);

    /**
     * @Author 钱敏杰
     * @Description 根据员工号查询关联数据
     * @Date 2018/12/4 13:43
     * @Param []
     * @return java.util.List<com.hy.model.ReportAccadRelation>
     **/
    List<BOAccadRelation> getListByEmpnum(@Param("empnum")String empnum);

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
     * @Description 删除当前BO账号的关联数据
     * @Date 2018/12/12 10:01
     * @Param [accountid]
     * @return int
     **/
    int deleteByAccountid(@Param("accountid")String accountid);

    /**
     * @Author 钱敏杰
     * @Description 批量插入关联数据
     * @Date 2018/12/6 15:11
     * @Param [list]
     * @return int
     **/
    int insertBatch(List<BOAccadRelation> list);

    /**
     * @Author 钱敏杰
     * @Description 根据员工号或员工姓名查询已关联BO账号的员工数据，若条件为空，则查询全部
     * @Date 2018/12/12 14:08
     * @Param [empnum]
     * @return java.util.List<com.hy.model.ReportAccadRelation>
     **/
    List<BOAccadRelation> getAccountEmp(@Param("empnum") String empnum);
}

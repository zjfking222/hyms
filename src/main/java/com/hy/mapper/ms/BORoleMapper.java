package com.hy.mapper.ms;

import com.hy.model.BORole;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Auther: 沈超宇
 * @Date: 2018/12/13 9:30
 * @Description:角色表增删改查mapper
 */
@Repository
public interface BORoleMapper {
    List<BORole> selectRole(@Param("value") String value);
    List<BORole> selectRoleByAcc(@Param("accountid") String accountid);//根据BO账号查询对应的角色信息
    Integer insertRole(BORole boRole);
    Integer updateRole(BORole boRole);
    Integer deleteRole(@Param("id") int id);
}

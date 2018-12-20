package com.hy.mapper.ms;

import com.hy.model.BORoleAccount;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Auther: 沈超宇
 * @Date: 2018/12/13 16:50
 * @Description:角色、BO账号关系表增删查mapper
 */
@Repository
public interface BORoleAccountMapper {
    List<BORoleAccount> selectRoleAccount(@Param("rid") int rid);
    Integer insertRoleAccount(BORoleAccount boRoleAccount);
    Integer deleteRoleAccount(@Param("id") int id);
    Integer deleteRoleAccountAll(@Param("rid") int rid);//删除角色时，删除对应角色、BO账号关系表中的数据
    Integer deleteByRidAcc(@Param("rid") int rid, @Param("accountid") String accountid);//根据角色、BO账号删除对应的数据
}

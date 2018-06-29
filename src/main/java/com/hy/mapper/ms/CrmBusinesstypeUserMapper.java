package com.hy.mapper.ms;

import com.hy.model.CrmBusinesstypeUser;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CrmBusinesstypeUserMapper {
    List<CrmBusinesstypeUser> selectBusinesstypeUser(@Param("btid") int btid);
    Integer deleteBusinesstypeUser(@Param("btid")int btid, @Param("uid")int uid);
    Integer insertBusinesstypeUser(CrmBusinesstypeUser crmBusinesstypeUser);
}

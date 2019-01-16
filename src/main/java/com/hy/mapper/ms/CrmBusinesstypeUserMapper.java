package com.hy.mapper.ms;

import com.hy.model.CrmBusinesstypeUser;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CrmBusinesstypeUserMapper {
    List<CrmBusinesstypeUser> selectBusinesstypeUser(@Param("filters")String filters, @Param("btid")int btid, @Param("sort")String sort, @Param("dir")String dir);
    Integer deleteBusinesstypeUser(@Param("btid")int btid, @Param("uid")String uid);
    Integer insertBusinesstypeUser(CrmBusinesstypeUser crmBusinesstypeUser);
}

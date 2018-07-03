package com.hy.mapper.ms;

import com.hy.model.CrmBusinesstype;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CrmBusinesstypeMapper {
    Integer insertBusinesstype(CrmBusinesstype crmBusinesstype);
    Integer updateBusinesstype(CrmBusinesstype crmBusinesstype);
    Integer deleteBusinesstype(@Param("id")Integer id);
    List<CrmBusinesstype> selectBusinesstype();
    List<CrmBusinesstype> selectBusinesstypeByName(@Param("name")String name);
    CrmBusinesstype selectBusinesstypeById(int id);
}

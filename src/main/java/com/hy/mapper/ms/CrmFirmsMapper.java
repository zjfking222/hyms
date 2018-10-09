package com.hy.mapper.ms;

import com.hy.model.CrmFirms;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CrmFirmsMapper {
    Integer insertCrmFirms(CrmFirms crmFirms);
    Integer updateCrmFirms(CrmFirms crmFirms);
    Integer deleteCrmFirms(@Param("id") int id);
    List<CrmFirms> selectCrmFirms(@Param("value") String value, @Param("uid") int uid, @Param("sort") String sort,
                                  @Param("dir") String dir);
    Integer selectCrmFirmsTotal(@Param("value") String value, @Param("uid") int uid);
    CrmFirms selectCrmFirmsById(@Param("id") int id);
    List<CrmFirms> selectCrmFirmsByUid(@Param("uid") int uid);
    List<CrmFirms> selectAllCrmFirms();
    Integer insertBatchCrmFirms(List<CrmFirms> crmFirms);
}

package com.hy.mapper.ms;

import com.hy.model.CrmCustomers;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CrmCustomersMapper {
    Integer insertCrmCustomer(CrmCustomers crmCustomers);
    Integer updateCrmCustomer(CrmCustomers crmCustomers);
    Integer deleteCrmCustomer(@Param("id") int id);
    List<CrmCustomers> selectCrmCustomer(@Param("uid") int uid, @Param("value") String value, @Param("sort") String sort,
                                         @Param("dir") String dir);
    Integer selectCrmCustomerTotal(@Param("uid")int uid, @Param("value") String value);
}

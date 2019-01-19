package com.hy.mapper.ms;

import com.hy.model.CrmCustomers;
import com.hy.model.VCrmCustomerFirm;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CrmCustomersMapper {
    Integer insertCrmCustomer(CrmCustomers crmCustomers);
    Integer updateCrmCustomer(CrmCustomers crmCustomers);
    Integer deleteCrmCustomer(@Param("id") int id);
    List<CrmCustomers> selectCrmCustomer(@Param("filters")String filters, @Param("uid") String uid, @Param("value") String value, @Param("sort") String sort,
                                         @Param("dir") String dir);
    Integer selectCrmCustomerTotal(@Param("filters")String filters, @Param("uid")String uid, @Param("value") String value);
    CrmCustomers seleCrmCustomerById(@Param("id") int id);

    List<VCrmCustomerFirm> selectCrmCustomerByUid(@Param("filters")String filters, @Param("uid") String uid, @Param("mid") int mid, @Param("value")String value,
                                            @Param("sort") String sort, @Param("dir")String dir);
    Integer selectCrmCustomerByUidTotal(@Param("filters")String filters, @Param("uid") String uid, @Param("mid") int mid, @Param("value")String value);
    Integer insertBatchCrmCustomer(List<CrmCustomers> crmCustomers);
}

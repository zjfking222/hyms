package com.hy.service.crm;

import com.hy.dto.CrmCustomerFirmViewDto;
import com.hy.dto.CrmCustomersDto;
import com.hy.dto.CrmCustomersFetchDto;

import java.util.List;

public interface CustomersService {
    Integer addCustomer(CrmCustomersFetchDto crmCustomersFetchDto);
    boolean setCustomer(CrmCustomersFetchDto crmCustomersFetchDto);
    boolean delCustomer(int id);
    Integer batchAddCustomer(String filepath);
    List<CrmCustomersDto> getCrmCustomer(String filters, int pageNum, int pageSize, String value, String sort, String dir);
    int getCrmCustomerTotal(String filters, String value);
    CrmCustomersDto getCrmCustomerById(int id);

    List<CrmCustomerFirmViewDto> getCrmCustomerByUid(String filters, int pageNum, int pageSize,int mid, String value,
                                                     String sort, String dir);
    Integer getCrmCustomerByUidTotal(String filters, int mid, String value);
}

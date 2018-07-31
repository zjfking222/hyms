package com.hy.service.crm;

import com.hy.dto.CrmCustomersDto;
import com.hy.dto.CrmCustomersFetchDto;

import java.util.List;

public interface CustomersService {
    Integer addCustomer(CrmCustomersFetchDto crmCustomersFetchDto);
    boolean setCustomer(CrmCustomersFetchDto crmCustomersFetchDto);
    boolean delCustomer(int id);
    List<CrmCustomersDto> getCrmCustomer(int pageNum, int pageSize, String value, String sort, String dir);
    int getCrmCustomerTotal(String value);
    CrmCustomersDto getCrmCustomerById(int id);
}

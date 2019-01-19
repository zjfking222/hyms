package com.hy.service.crm;

import com.hy.dto.CrmBusinessUserDto;
import com.hy.dto.CrmBusinesstypeUserDto;
import com.hy.model.CrmBusinesstypeUser;

import java.util.List;

public interface BusinessTypeUserService {
    List<CrmBusinessUserDto> getBusinesstypeUser(String filters, int btid, String sort, String dir);
    boolean setBusinesstypeUser(CrmBusinesstypeUserDto crmBusinesstypeUserDto);
}

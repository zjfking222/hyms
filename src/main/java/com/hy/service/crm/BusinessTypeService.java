package com.hy.service.crm;

import com.hy.dto.CrmBusinesstypeDto;
import com.hy.model.CrmBusinesstype;

import java.util.List;

public interface BusinessTypeService {
    boolean addBusinessType(CrmBusinesstypeDto businesstypeDto);
    boolean delBusinessType(Integer id);
    boolean setBusinessType(CrmBusinesstypeDto businesstypeDto);
    List<CrmBusinesstypeDto> getBusinessType();
    List<CrmBusinesstypeDto> searchBusinessTypeByName(String name);
    CrmBusinesstypeDto getBusinesstypeById(int id);
    List<CrmBusinesstypeDto> getBusinessTypeByUid();
}

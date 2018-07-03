package com.hy.service.crm;

import com.hy.dto.CrmFirmsDto;

import java.util.List;

public interface FirmsService {
    boolean addCrmFirm(CrmFirmsDto crmFirmsDto);
    boolean setCrmFirm(CrmFirmsDto crmFirmsDto);
    boolean delCrmFirm(int id);
    List<CrmFirmsDto> getCrmFirm(int pageNum, int pageSize, String value, String sort, String dir);
    int getCrmFirmTotal(String value);
}

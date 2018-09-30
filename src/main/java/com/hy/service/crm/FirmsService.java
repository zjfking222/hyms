package com.hy.service.crm;

import com.hy.dto.CrmFirmsDto;
import com.hy.dto.CrmFirmsFetchDto;
import com.hy.model.CrmFirms;

import java.util.HashMap;
import java.util.List;

public interface FirmsService {
    Integer addCrmFirm(CrmFirmsFetchDto crmFirmsDto);
    boolean setCrmFirm(CrmFirmsFetchDto crmFirmsDto);
    boolean delCrmFirm(int id);
    Integer batchAddFirm(String filepath);
    List<CrmFirmsDto> getCrmFirm(int pageNum, int pageSize, String value, String sort, String dir);
    int getCrmFirmTotal(String value);
    CrmFirmsFetchDto getCrmFirmById(int id);
    //获取企业列表，不分页
    List<CrmFirmsDto> getCrmFirmByUid();
    //获取企业字典
    HashMap<String, Integer> getAllCrmFirm();
}

package com.hy.service.crm;

import com.hy.common.SecurityUtil;
import com.hy.dto.CrmBusinesstypeDto;
import com.hy.mapper.ms.CrmBusinesstypeMapper;
import com.hy.model.CrmBusinesstype;
import com.hy.utils.DTOUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BusinessTypeServiceImpl implements BusinessTypeService {

    @Autowired
    private CrmBusinesstypeMapper crmBusinesstypeMapper;

    @Override
    public boolean addBusinessType(CrmBusinesstypeDto businesstypeDto) {
        CrmBusinesstype crmBusinesstype = DTOUtil.populate(businesstypeDto, CrmBusinesstype.class);
        crmBusinesstype.setCreater(SecurityUtil.getLoginid());
        crmBusinesstype.setModifier(SecurityUtil.getLoginid());
        return crmBusinesstypeMapper.insertBusinesstype(crmBusinesstype) == 1;
    }

    @Override
    public boolean delBusinessType(Integer id) {
        return crmBusinesstypeMapper.deleteBusinesstype(id) == 1;
    }

    @Override
    public boolean setBusinessType(CrmBusinesstypeDto businesstypeDto) {
        CrmBusinesstype crmBusinesstype = DTOUtil.populate(businesstypeDto, CrmBusinesstype.class);
        crmBusinesstype.setModifier(SecurityUtil.getLoginid());
        return crmBusinesstypeMapper.updateBusinesstype(crmBusinesstype) == 1;
    }

    @Override
    public List<CrmBusinesstypeDto> getBusinessType() {
        return DTOUtil.populateList(crmBusinesstypeMapper.selectBusinesstype(), CrmBusinesstypeDto.class);
    }

    @Override
    public List<CrmBusinesstypeDto> searchBusinessTypeByName(String name) {
        return DTOUtil.populateList(crmBusinesstypeMapper.selectBusinesstypeByName(name), CrmBusinesstypeDto.class);
    }

    @Override
    public CrmBusinesstypeDto getBusinesstypeById(int id) {
        return DTOUtil.populate(crmBusinesstypeMapper.selectBusinesstypeById(id), CrmBusinesstypeDto.class);
    }

    @Override
    public List<CrmBusinesstypeDto> getBusinessTypeByUid() {
        return DTOUtil.populateList(crmBusinesstypeMapper.selectBusinesstypeByUid(SecurityUtil.getLoginid()),CrmBusinesstypeDto.class);
    }
}

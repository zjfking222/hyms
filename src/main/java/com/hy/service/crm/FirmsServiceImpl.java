package com.hy.service.crm;

import com.github.pagehelper.PageHelper;
import com.hy.common.SecurityHelp;
import com.hy.dto.CrmFirmsDto;
import com.hy.mapper.ms.CrmFirmsMapper;
import com.hy.model.CrmFirms;
import com.hy.utils.DTOUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class FirmsServiceImpl implements FirmsService{

    @Autowired
    private CrmFirmsMapper crmFirmsMapper;
    @Autowired
    private BusinessTypeService businessTypeService;

    @Override
    public boolean addCrmFirm(CrmFirmsDto crmFirmsDto) {
        //todo 需要修改
        CrmFirms crmFirms = DTOUtil.populate(crmFirmsDto, CrmFirms.class);
        crmFirms.setCreater(SecurityHelp.getUserId());
        crmFirms.setModifier(SecurityHelp.getUserId());
        crmFirms.setDomain(SecurityHelp.getDepartmentId());
        return crmFirmsMapper.insertCrmFirms(crmFirms) == 1;
    }

    @Override
    public boolean setCrmFirm(CrmFirmsDto crmFirmsDto) {
        //todo 需要修改
        CrmFirms crmFirms = DTOUtil.populate(crmFirmsDto, CrmFirms.class);
        crmFirms.setModifier(SecurityHelp.getUserId());
        return crmFirmsMapper.updateCrmFirms(crmFirms) == 1;
    }

    @Override
    public boolean delCrmFirm(int id) {
        return crmFirmsMapper.deleteCrmFirms(id) == 1;
    }

    @Override
    public List<CrmFirmsDto> getCrmFirm(int pageNum, int pageSize, String value, String sort, String dir) {
        PageHelper.startPage(pageNum, pageSize);
        List<CrmFirms> crms = crmFirmsMapper.selectCrmFirms(value, SecurityHelp.getUserId(), sort ,dir);
        List<CrmFirmsDto> dtos = new ArrayList<>();
        for (CrmFirms crm : crms){
            dtos.add(new CrmFirmsDto(crm.getId(), crm.getName(),crm.getPhone(),crm.getAddress(),crm.getContacter(),
                    crm.getCmobile(),crm.getCphone(),crm.getEmail(),businessTypeService.getBusinesstypeById(crm.getBtid()),
                    crm.getRemark()));
        }
        return dtos;
    }

    @Override
    public int getCrmFirmTotal(String value) {
        return crmFirmsMapper.selectCrmFirmsTotal(value, SecurityHelp.getUserId());
    }
}

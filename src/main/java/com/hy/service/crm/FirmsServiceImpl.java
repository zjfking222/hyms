package com.hy.service.crm;

import com.github.pagehelper.PageHelper;
import com.hy.common.SecurityHelp;
import com.hy.dto.CrmFirmsDto;
import com.hy.dto.CrmFirmsFetchDto;
import com.hy.mapper.ms.CrmFirmsMapper;
import com.hy.model.CrmFirms;
import com.hy.utils.DTOUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.IntStream;

@Service
public class FirmsServiceImpl implements FirmsService{

    @Autowired
    private CrmFirmsMapper crmFirmsMapper;
    @Autowired
    private BusinessTypeService businessTypeService;

    @Override
    public Integer addCrmFirm(CrmFirmsFetchDto crmFirmsDto) {
        CrmFirms crmFirms = DTOUtil.populate(crmFirmsDto, CrmFirms.class);
        crmFirms.setCreater(SecurityHelp.getUserId());
        crmFirms.setModifier(SecurityHelp.getUserId());
        crmFirms.setDomain(SecurityHelp.getDepartmentId());
        crmFirmsMapper.insertCrmFirms(crmFirms);
        return crmFirms.getId();
    }

    @Override
    public boolean setCrmFirm(CrmFirmsFetchDto crmFirmsDto) {
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

    @Override
    public CrmFirmsFetchDto getCrmFirmById(int id) {
        return DTOUtil.populate(crmFirmsMapper.selectCrmFirmsById(id),CrmFirmsFetchDto.class);
    }

    @Override
    public List<CrmFirmsDto> getCrmFirmByUid() {
        return DTOUtil.populateList(crmFirmsMapper.selectCrmFirmsByUid(SecurityHelp.getUserId()),CrmFirmsDto.class);
    }

    @Override
    public HashMap<String, Integer> getAllCrmFirm() {
        HashMap<String, Integer> map = new HashMap<>();
        List<CrmFirmsDto> list = DTOUtil.populateList(crmFirmsMapper.selectAllCrmFirms(),CrmFirmsDto.class);
        IntStream.range(0, list.size()).forEach(i ->
            map.put(list.get(i).getName(),list.get(i).getId())
        );
        return map;
    }
}

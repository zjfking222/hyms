package com.hy.service.crm;

import com.github.pagehelper.PageHelper;
import com.hy.common.SecurityHelp;
import com.hy.dto.CrmCustomersDto;
import com.hy.dto.CrmCustomersFetchDto;
import com.hy.mapper.ms.CrmCustomersMapper;
import com.hy.model.CrmCustomers;
import com.hy.utils.DTOUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CustomersServiceImpl implements CustomersService{

    @Autowired
    private CrmCustomersMapper customersMapper;

    @Autowired
    private BusinessTypeService businessTypeService;

    @Autowired
    private FirmsService firmsService;

    @Override
    public Integer addCustomer(CrmCustomersFetchDto crmCustomersFetchDto) {
        CrmCustomers customer = DTOUtil.populate(crmCustomersFetchDto, CrmCustomers.class);
        customer.setCreater(SecurityHelp.getUserId());
        customer.setModifier(SecurityHelp.getUserId());
        customer.setDomain(SecurityHelp.getDepartmentId());
        customersMapper.insertCrmCustomer(customer);
        return customer.getId();
    }

    @Override
    public boolean setCustomer(CrmCustomersFetchDto crmCustomersFetchDto) {
        CrmCustomers customers = DTOUtil.populate(crmCustomersFetchDto, CrmCustomers.class);
        customers.setModifier(SecurityHelp.getUserId());
        return customersMapper.updateCrmCustomer(customers) == 1;
    }

    @Override
    public boolean delCustomer(int id) {
        return customersMapper.deleteCrmCustomer(id) == 1;
    }

    @Override
    public List<CrmCustomersDto> getCrmCustomer(int pageNum, int pageSize, String value, String sort, String dir) {
        PageHelper.startPage(pageNum, pageSize);
        List<CrmCustomers> crms = customersMapper.selectCrmCustomer(SecurityHelp.getUserId(), value, sort, dir);
        List<CrmCustomersDto> dtos = new ArrayList<>();
        for (CrmCustomers crm : crms){
            dtos.add(new CrmCustomersDto(crm.getId(),crm.getName(),crm.getPost(),crm.getNationality(),crm.getAddress(),
                    crm.getSex(),crm.getMobile(),crm.getPhone(),crm.getEmail(),
                    businessTypeService.getBusinesstypeById(crm.getBtid()),firmsService.getCrmFirmById(crm.getFid()),
                    crm.getVip(),crm.getRemark()));
        }
        return dtos;
    }

    @Override
    public int getCrmCustomerTotal(String value) {
        return customersMapper.selectCrmCustomerTotal(SecurityHelp.getUserId(), value);
    }

    @Override
    public CrmCustomersDto getCrmCustomerById(int id) {
        CrmCustomers customers = customersMapper.seleCrmCustomerById(id);
        return new CrmCustomersDto(customers.getId(),customers.getName(),customers.getPost(),
                customers.getNationality(),customers.getAddress(),customers.getSex(),customers.getMobile(),
                customers.getPhone(),customers.getEmail(),businessTypeService.getBusinesstypeById(customers.getBtid()),
                firmsService.getCrmFirmById(customers.getFid()),customers.getVip(),customers.getRemark());
    }

}

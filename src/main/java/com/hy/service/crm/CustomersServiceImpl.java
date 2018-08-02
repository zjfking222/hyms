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

    private String male = "男";
    private String female = "女";

    @Override
    public Integer addCustomer(CrmCustomersFetchDto crmCustomersFetchDto) {
        CrmCustomers customer = DTOUtil.populate(crmCustomersFetchDto, CrmCustomers.class);
        customer.setCreater(SecurityHelp.getUserId());
        customer.setModifier(SecurityHelp.getUserId());
        customer.setDomain(SecurityHelp.getDepartmentId());

        if (crmCustomersFetchDto.getSex().equals(female)){
            customer.setSex(false);
        }
        else if(crmCustomersFetchDto.getSex().equals(male)){
            customer.setSex(true);
        }
        //篡改为非男非女的操作默认性别为男
        else {
            customer.setSex(true);
        }

        customersMapper.insertCrmCustomer(customer);
        return customer.getId();
    }

    @Override
    public boolean setCustomer(CrmCustomersFetchDto crmCustomersFetchDto) {
        CrmCustomers customers = DTOUtil.populate(crmCustomersFetchDto, CrmCustomers.class);
        customers.setModifier(SecurityHelp.getUserId());
        if(crmCustomersFetchDto.getSex().equals(male)){
            customers.setSex(true);
        }
        else if (crmCustomersFetchDto.getSex().equals(female)){
            customers.setSex(false);
        }
        else {
            customers.setSex(true);
        }
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
            CrmCustomersDto customersDto = new CrmCustomersDto(crm.getId(),crm.getName(),crm.getPost(),crm.getNationality(),crm.getAddress(),crm.getMobile(),crm.getPhone(),crm.getEmail(),
                    businessTypeService.getBusinesstypeById(crm.getBtid()),firmsService.getCrmFirmById(crm.getFid()),
                    crm.getVip(),crm.getRemark());
            if(crm.getSex()){
                customersDto.setSex(male);
            }
            else {
                customersDto.setSex(female);
            }
            dtos.add(customersDto);
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
        CrmCustomersDto crmCustomersDto = new CrmCustomersDto(customers.getId(),customers.getName(),customers.getPost(),
                customers.getNationality(),customers.getAddress(),customers.getMobile(),
                customers.getPhone(),customers.getEmail(),businessTypeService.getBusinesstypeById(customers.getBtid()),
                firmsService.getCrmFirmById(customers.getFid()),customers.getVip(),customers.getRemark());
        if(customers.getSex()){
            crmCustomersDto.setSex(male);
        }
        else {
            crmCustomersDto.setSex(female);
        }
        return crmCustomersDto;
    }

}

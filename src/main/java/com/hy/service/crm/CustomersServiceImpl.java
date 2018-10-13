package com.hy.service.crm;

import com.github.pagehelper.PageHelper;
import com.hy.common.SecurityHelp;
import com.hy.dto.CrmBusinesstypeDto;
import com.hy.dto.CrmCustomerFirmViewDto;
import com.hy.dto.CrmCustomersDto;
import com.hy.dto.CrmCustomersFetchDto;

import com.hy.mapper.ms.CrmCustomersMapper;
import com.hy.model.CrmCustomers;
import com.hy.model.VCrmCustomerFirm;
import com.hy.utils.DTOUtil;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.IntStream;

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

    private String[] titleRow = {"姓名","性别","国籍","职位","企业名称","手机","固话","邮箱",
            "地址","业务类型","客户等级","备注"};

    private boolean titleFlag;

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

    private Object getCell(Row row, int index){
        if(row.getCell(index) == null){
            return "";
        }
        else{
            switch (row.getCell(index).getCellType()){
                case STRING:
                    return row.getCell(index).getStringCellValue().trim();
                case BLANK:
                    return "";
                case NUMERIC:
                    return ((Double)row.getCell(index).getNumericCellValue()).longValue();
                case BOOLEAN:
                    return row.getCell(index).getBooleanCellValue();
                default:
                    return "";
            }
        }

    }

    @Override
    public Integer batchAddCustomer(String filepath) {
        try {
            titleFlag = true;
            InputStream inputStream = new FileInputStream("files" + filepath);

            XSSFWorkbook workbook = new XSSFWorkbook(inputStream);
            XSSFSheet sheetAt = workbook.getSheetAt(0);
            List<CrmCustomers> customers = new ArrayList<>();
            Map<String, Integer> firmsMap = firmsService.getAllCrmFirm();
            Map<Integer, CrmBusinesstypeDto> businessTypeMap = new HashMap<>();
            businessTypeService.getBusinessType().forEach(i -> businessTypeMap.put(i.getId(),i));

            IntStream.range(0, titleRow.length).forEach(i -> {
                try {
                    if(!sheetAt.getRow(0).getCell(i).getStringCellValue().equals(titleRow[i])){
                        titleFlag = false;
                    }
                }catch (NullPointerException e){
                    titleFlag = false;
                }
            });
            if(titleFlag){
                for(Row row : sheetAt){
                    if(row.getRowNum() != 0 && row.getRowNum() != sheetAt.getLastRowNum()){
                        String name = String.valueOf(getCell(row, 0));
                        boolean sex = getCell(row, 1).equals("男");
                        String nationality = String.valueOf(getCell(row, 2));
                        String post = String.valueOf(getCell(row, 3));
                        int fid = firmsMap.getOrDefault(String.valueOf(getCell(row, 4)), -1);
                        String mobile = String.valueOf(getCell(row, 5));
                        String phone = String.valueOf(getCell(row, 6));
                        String email = String.valueOf(getCell(row, 7));
                        String address = String.valueOf(getCell(row, 8));
                        int btid;

                        try{
                            if(businessTypeMap.containsKey(Integer.valueOf(String.valueOf(getCell(row, 9))))){
                                btid = Integer.valueOf(String.valueOf(getCell(row, 9)));
                            }else{
                                btid = -1;
                            }
                        }catch (NumberFormatException e){
                            btid = -1;
                        }
                        int vip;
                        try{
                            vip = Integer.valueOf(String.valueOf(getCell(row, 10)));
                        }catch (NumberFormatException e){
                            vip = 0;
                        }
                        String remark = String.valueOf(getCell(row, 11));
                        customers.add(new CrmCustomers(name, post, nationality, address, sex, mobile, phone,
                                email, btid, fid, vip, remark, SecurityHelp.getUserId(), SecurityHelp.getUserId(),
                                SecurityHelp.getDepartmentId()));

                    }
                }
                if(customers.size()==0){
                    return 0;
                }else {
                    return customersMapper.insertBatchCrmCustomer(customers);
                }

            }
            return -1;
        } catch (IOException e) {
            e.printStackTrace();
            return -2;
        }
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
        crmCustomersDto.setSex(customers.getSex() ? male : female);
        return crmCustomersDto;
    }

    @Override
    public List<CrmCustomerFirmViewDto> getCrmCustomerByUid(int pageNum, int pageSize, int mid, String value,
                                                            String sort, String dir) {
        PageHelper.startPage(pageNum, pageSize);
        List<VCrmCustomerFirm> vCrmCustomerFirms = customersMapper.selectCrmCustomerByUid(SecurityHelp.getUserId(),
                mid,value,sort,dir);
        List<CrmCustomerFirmViewDto> dto = DTOUtil.populateList(vCrmCustomerFirms, CrmCustomerFirmViewDto.class);
        IntStream.range(0, vCrmCustomerFirms.size()).forEach(i ->
            dto.get(i).setSex(vCrmCustomerFirms.get(i).getSex() ? male : female)
        );
        return dto;
    }

    @Override
    public Integer getCrmCustomerByUidTotal(int mid, String value) {
        return customersMapper.selectCrmCustomerByUidTotal(SecurityHelp.getUserId(), mid, value);
    }
}

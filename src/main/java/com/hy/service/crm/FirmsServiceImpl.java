package com.hy.service.crm;

import com.github.pagehelper.PageHelper;
import com.hy.common.SecurityUtil;
import com.hy.dto.CrmBusinesstypeDto;
import com.hy.dto.CrmFirmsDto;
import com.hy.dto.CrmFirmsFetchDto;
import com.hy.mapper.ms.CrmFirmsMapper;
import com.hy.model.CrmFirms;
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
public class FirmsServiceImpl implements FirmsService {

    @Autowired
    private CrmFirmsMapper crmFirmsMapper;
    @Autowired
    private BusinessTypeService businessTypeService;

    private String[] titleRow = {"企业名称", "固话", "地址", "联系人", "手机", "联系人固话", "邮箱", "所属部门", "备注"};

    private boolean titleFlag;

    @Override
    public Integer addCrmFirm(CrmFirmsFetchDto crmFirmsDto) {
        CrmFirms crmFirms = DTOUtil.populate(crmFirmsDto, CrmFirms.class);
        crmFirms.setCreater(SecurityUtil.getLoginid());
        crmFirms.setModifier(SecurityUtil.getLoginid());
        crmFirms.setDomain(SecurityUtil.getDepartmentId());
        crmFirmsMapper.insertCrmFirms(crmFirms);
        return crmFirms.getId();
    }

    @Override
    public boolean setCrmFirm(CrmFirmsFetchDto crmFirmsDto) {
        CrmFirms crmFirms = DTOUtil.populate(crmFirmsDto, CrmFirms.class);
        crmFirms.setModifier(SecurityUtil.getLoginid());
        return crmFirmsMapper.updateCrmFirms(crmFirms) == 1;
    }

    @Override
    public boolean delCrmFirm(int id) {
        return crmFirmsMapper.deleteCrmFirms(id) == 1;
    }

    private Object getCell(Row row, int index) {
        if (row.getCell(index) == null) {
            return "";
        } else {
            switch (row.getCell(index).getCellType()) {
                case STRING:
                    return row.getCell(index).getStringCellValue().trim();
                case BLANK:
                    return "";
                case NUMERIC:
                    return ((Double) row.getCell(index).getNumericCellValue()).longValue();
                case BOOLEAN:
                    return row.getCell(index).getBooleanCellValue();
                default:
                    return "";
            }
        }
    }

    @Override
    public Integer batchAddFirm(String filepath) {
        try {
            titleFlag = true;
            InputStream inputStream = new FileInputStream("files" + filepath);
            XSSFWorkbook workbook = new XSSFWorkbook(inputStream);
            XSSFSheet sheetAt = workbook.getSheetAt(0);
            List<CrmFirms> firms = new ArrayList<>();
            List<CrmFirmsDto> list = DTOUtil.populateList(crmFirmsMapper.selectAllCrmFirms(), CrmFirmsDto.class);
            HashMap<String, String> firmsMap = new HashMap<>();
            Map<Integer, CrmBusinesstypeDto> businessTypeMap = new HashMap<>();
            businessTypeService.getBusinessType().forEach(i -> businessTypeMap.put(i.getId(),i));

            IntStream.range(0, list.size()).forEach(i ->
                    firmsMap.put(list.get(i).getName(), list.get(i).getName())
            );
            IntStream.range(0, titleRow.length).forEach(i -> {
                try {
                    if (!sheetAt.getRow(0).getCell(i).getStringCellValue().equals(titleRow[i])) {
                        titleFlag = false;
                    }
                } catch (NullPointerException e) {
                    titleFlag = false;
                }
            });
            if (titleFlag) {
                for (Row row : sheetAt) {
                    if (row.getRowNum() != 0 && row.getRowNum() != sheetAt.getLastRowNum()) {
                        String name = firmsMap.getOrDefault(String.valueOf(getCell(row, 0)), null) == null
                                ? String.valueOf(getCell(row, 0)) : null;
                        String phone = String.valueOf(getCell(row, 1));
                        String address = String.valueOf(getCell(row, 2));
                        String contacter = String.valueOf(getCell(row, 3));
                        String cmobile = String.valueOf(getCell(row, 4));
                        String cphone = String.valueOf(getCell(row, 5));
                        String email = String.valueOf(getCell(row, 6));
                        int btid;
                        try{
                            if(businessTypeMap.containsKey(Integer.valueOf(String.valueOf(getCell(row, 7))))){
                                btid = Integer.valueOf(String.valueOf(getCell(row, 7)));
                            }else{
                                btid = -1;
                            }
                        }catch (NumberFormatException e){
                            btid = -1;
                        }
                        String remark = String.valueOf(getCell(row, 8));
                        if (name != null  && !name.equals("")) {
                            firms.add(new CrmFirms(name, phone, address, contacter, cmobile, cphone, email, btid, remark,
                                    SecurityUtil.getLoginid(), SecurityUtil.getLoginid(), SecurityUtil.getDepartmentId()));
                        }
                    }

                }
                if (firms.size() != 0) {
                    return crmFirmsMapper.insertBatchCrmFirms(firms);
                } else {
                    return 0;
                }
            }
            return -1;

        } catch (IOException e) {
            e.printStackTrace();
            return -2;
        }
    }

    @Override
    public List<CrmFirmsDto> getCrmFirm(String filters, int pageNum, int pageSize, String value, String sort, String dir) {
        PageHelper.startPage(pageNum, pageSize);
        List<CrmFirms> crms = crmFirmsMapper.selectCrmFirms(filters, value, SecurityUtil.getLoginid(), sort, dir);
        List<CrmFirmsDto> dtos = new ArrayList<>();
        for (CrmFirms crm : crms) {
            dtos.add(new CrmFirmsDto(crm.getId(), crm.getName(), crm.getPhone(), crm.getAddress(), crm.getContacter(),
                    crm.getCmobile(), crm.getCphone(), crm.getEmail(), businessTypeService.getBusinesstypeById(crm.getBtid()),
                    crm.getRemark()));
        }
        return dtos;
    }

    @Override
    public int getCrmFirmTotal(String filters, String value) {
        return crmFirmsMapper.selectCrmFirmsTotal(filters, value, SecurityUtil.getLoginid());
    }

    @Override
    public CrmFirmsFetchDto getCrmFirmById(int id) {
        return DTOUtil.populate(crmFirmsMapper.selectCrmFirmsById(id), CrmFirmsFetchDto.class);
    }

    @Override
    public List<CrmFirmsDto> getCrmFirmByUid() {
        return DTOUtil.populateList(crmFirmsMapper.selectCrmFirmsByUid(SecurityUtil.getLoginid()), CrmFirmsDto.class);
    }

    @Override
    public List<CrmFirmsDto> getCrmFirmByLike(String value){
        List<CrmFirms> crmFirms = crmFirmsMapper.selectCrmFirmsByLike(SecurityUtil.getLoginid(),value);
        return DTOUtil.populateList(crmFirms,CrmFirmsDto.class);
    }

    @Override
    public HashMap<String, Integer> getAllCrmFirm() {
        HashMap<String, Integer> map = new HashMap<>();
        List<CrmFirmsDto> list = DTOUtil.populateList(crmFirmsMapper.selectAllCrmFirms(), CrmFirmsDto.class);
        IntStream.range(0, list.size()).forEach(i ->
                map.put(list.get(i).getName(), list.get(i).getId())
        );
        return map;
    }
}

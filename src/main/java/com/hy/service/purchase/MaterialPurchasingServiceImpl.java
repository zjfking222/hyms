package com.hy.service.purchase;

import com.github.pagehelper.PageHelper;
import com.hy.common.SecurityUtil;
import com.hy.dto.MaterialInfoDto;
import com.hy.dto.PurchaseSalesmanDto;
import com.hy.dto.SysDictDto;
import com.hy.mapper.ms.MaterialInfoMapper;
import com.hy.dto.PurchaseTracerDto;
import com.hy.mapper.ms.PurchaseSalesmanMapper;
import com.hy.model.MaterialInfo;
import com.hy.mapper.ms.PurchaseTracerMapper;
import com.hy.model.PurchaseSalesman;
import com.hy.service.system.SysDictService;
import com.hy.model.PurchaseTracer;
import com.hy.utils.DTOUtil;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @Auther: 沈超宇
 * @Date: 2019/1/21 16:21
 * @Description:物资采购serviceImpl
 */
@Service
public class MaterialPurchasingServiceImpl implements MaterialPurchasingService {
    @Autowired
    private PurchaseSalesmanMapper purchaseSalesmanMapper;
    @Autowired
    private MaterialInfoMapper materialInfoMapper;
    @Autowired
    private SysDictService sysDictService;
    @Autowired
    private PurchaseTracerMapper purchaseTracerMapper;

    @Override
    //查询业务员信息
    public List<PurchaseSalesmanDto> getSalesman(String value){
        List<PurchaseSalesman> salesmanTracers = purchaseSalesmanMapper.selectSalesman(value);
        return DTOUtil.populateList(salesmanTracers, PurchaseSalesmanDto.class);
    }

    @Override
    //新增业务员
    public boolean addSalesman(PurchaseSalesmanDto purchaseSalesmanDto){
        PurchaseSalesman purchaseSalesman = DTOUtil.populate(purchaseSalesmanDto, PurchaseSalesman.class);
        purchaseSalesman.setCreater(SecurityUtil.getLoginid());
        purchaseSalesman.setModifier(SecurityUtil.getLoginid());
        return purchaseSalesmanMapper.insertSalesman(purchaseSalesman) == 1;
    }

    @Override
    @Transactional
    //删除业务员时需确认采购物资中是否含有业务员，删除业务员时同时删除跟单员
    public void delSalesman(int id){
        try {
            if(purchaseSalesmanMapper.deleteSalesman(id) != 1){
                throw new RuntimeException("删除业务员失败！");
            }
            if(purchaseTracerMapper.deleteTracerBySid(id) > 0){
                throw new RuntimeException("删除跟单员失败！");
            }
        }catch (Exception e) {
            System.out.println(e);
            throw e;
        }
    }

    @Override
    //查询跟单员
    public List<PurchaseTracerDto> getTracer(String filters, int sid, String value){
        List<PurchaseTracer> purchaseTracers = purchaseTracerMapper.selectTracer(filters ,sid, value);
        return DTOUtil.populateList(purchaseTracers, PurchaseTracerDto.class);
    }

    @Override
    //新增跟单员
    public boolean addTracer(PurchaseTracerDto purchaseTracerDto){
        PurchaseTracer purchaseTracer = DTOUtil.populate(purchaseTracerDto, PurchaseTracer.class);
        purchaseTracer.setCreater(SecurityUtil.getLoginid());
        purchaseTracer.setModifier(SecurityUtil.getLoginid());
        return purchaseTracerMapper.insertTracer(purchaseTracer) == 1;
    }

    @Override
    //删除跟单员
    public boolean delTracer(int id){
        return purchaseTracerMapper.deleteTracer(id) == 1;
    }

    /**
     * @Author 钱敏杰
     * @Description 分页查询物资信息
     * @Date 2019/1/22 16:55
     * @Param [pageNum, pageSize, filters, sort, dir, value]
     * @return java.util.List<com.hy.dto.MaterialInfoDto>
     **/
    @Override
    public List<MaterialInfoDto> getMaterialInfoPage(Integer pageNum, Integer pageSize,String filters, String sort, String dir, String value){
        PageHelper.startPage(pageNum, pageSize);
        List<MaterialInfo> list = materialInfoMapper.selectMaterialInfoPage(filters, sort, dir, value);
        return DTOUtil.populateList(list, MaterialInfoDto.class);
    }

    /**
     * @Author 钱敏杰
     * @Description 根据条件统计当前数据量
     * @Date 2019/1/22 17:02
     * @Param [filters, value]
     * @return int
     **/
    @Override
    public int countMaterialInfo(String filters, String value){
        int num = materialInfoMapper.selectMaterialInfoTotal(filters, value);
        return num;
    }

    /**
     * @Author 钱敏杰
     * @Description 新增物资信息
     * @Date 2019/1/22 11:02
     * @Param [infoDto]
     * @return int
     **/
    @Override
    public int addMaterialInfo(MaterialInfoDto infoDto){
        MaterialInfo info = DTOUtil.populate(infoDto, MaterialInfo.class);
        return materialInfoMapper.insertSelective(info);
    }

    /**
     * @Author 钱敏杰
     * @Description 批量添加物资信息
     * @Date 2019/1/22 11:05
     * @Param [infoList]
     * @return int
     **/
    @Override
    public int addMaterialInfoBatch(List<MaterialInfo> infoList){
        return materialInfoMapper.insertBatch(infoList);
    }

    /**
     * @Author 钱敏杰
     * @Description 更新当前物资信息
     * @Date 2019/1/22 13:41
     * @Param [infoDto]
     * @return int
     **/
    @Override
    public int updateMaterialInfo(MaterialInfoDto infoDto){
        MaterialInfo info = DTOUtil.populate(infoDto, MaterialInfo.class);
        return materialInfoMapper.updateByPrimaryKeySelective(info);
    }

    /**
     * @Author 钱敏杰
     * @Description 删除当前物资信息
     * @Date 2019/1/22 13:42
     * @Param [id]
     * @return int
     **/
    @Override
    public int deleteMaterialInfo(Integer id){
        return materialInfoMapper.deleteByPrimaryKey(id);
    }

    /**
     * @Author 钱敏杰
     * @Description 解析Excel文件并保存数据到数据库
     * @Date 2019/1/22 16:23
     * @Param [input]
     * @return void
     **/
    @Override
    @Transactional
    public void importMaterialExcel(InputStream input) throws IOException {
        XSSFWorkbook workbook = new XSSFWorkbook(input);
        //只读取第一张sheet
        XSSFSheet sheetAt = workbook.getSheetAt(0);
        //查询数据字典中的参数
        List<SysDictDto> dicList = sysDictService.selectByCode("");
        //list转map
        Map<String, SysDictDto> dicMap = dicList.stream().collect(Collectors.toMap(SysDictDto::getName, a -> a, (k1, k2) -> k1));
        List<MaterialInfo> list = new ArrayList<>();
        MaterialInfo info = null;
        for(int i=1;i<sheetAt.getLastRowNum();i++){
            //第一行数据为标题，去除
            Row row = sheetAt.getRow(i);
            if(row != null && row.getLastCellNum() == 35){//判断是否为规定格式的Excel文件
                info = new MaterialInfo();//row.getCell(0);
                if(StringUtils.isNotEmpty(row.getCell(0).getStringCellValue())){
                    info.setApplytype("日常".equals(row.getCell(0).getStringCellValue())? 1:2);//申请类别：1 日常；2 项目
                }
                if(StringUtils.isNotEmpty(row.getCell(1).getStringCellValue())){
                    info.setCompanyname(row.getCell(1).getStringCellValue());//公司名称
                }
                if(StringUtils.isNotEmpty(row.getCell(2).getStringCellValue())){
                    SysDictDto dto = dicMap.get(row.getCell(2).getStringCellValue());
                    if(dto != null){
                        info.setMattype(dto.getId());//物资类别
                        info.setMattypename(dto.getName());//物资类别名称
                    }
                }
                if(StringUtils.isNotEmpty(row.getCell(3).getStringCellValue())){
                    info.setBatch(row.getCell(3).getStringCellValue());//追溯号(批次)
                }
                if(StringUtils.isNotEmpty(row.getCell(4).getStringCellValue())){
                    info.setMaterialname(row.getCell(4).getStringCellValue());//物资名称
                }
                if(StringUtils.isNotEmpty(row.getCell(5).getStringCellValue())){
                    info.setMaterialdes(row.getCell(5).getStringCellValue());//物资描述
                }
                if(StringUtils.isNotEmpty(row.getCell(6).getStringCellValue())){
                    info.setAmount(Float.valueOf(row.getCell(6).getStringCellValue()));//数量
                }
                if(StringUtils.isNotEmpty(row.getCell(7).getStringCellValue())){
                    info.setUnit(row.getCell(7).getStringCellValue());//单位
                }
                if(StringUtils.isNotEmpty(row.getCell(8).getStringCellValue())){
                    //info.setEmpnum();//业务员员工号
                    info.setEmpname(row.getCell(8).getStringCellValue());//业务员姓名
                }
                if(StringUtils.isNotEmpty(row.getCell(9).getStringCellValue())){
                    info.setApplydept(row.getCell(9).getStringCellValue());//申请部门
                }
                if(StringUtils.isNotEmpty(row.getCell(10).getStringCellValue())){
                    info.setApplyperson(row.getCell(10).getStringCellValue());//申请联系人
                }
                if(StringUtils.isNotEmpty(row.getCell(11).getStringCellValue()) && !"/".equals(row.getCell(11).getStringCellValue())){
                    info.setDispatchdate(this.formatDate(row.getCell(11).getStringCellValue()));//物资分派日期
                }
                if(StringUtils.isNotEmpty(row.getCell(12).getStringCellValue()) && !"/".equals(row.getCell(12).getStringCellValue())){
                    info.setRequireddate(this.formatDate(row.getCell(12).getStringCellValue()));//要求到货日期
                }
                if(StringUtils.isNotEmpty(row.getCell(13).getStringCellValue()) && !"/".equals(row.getCell(13).getStringCellValue())){
                    info.setOverseasdate(this.formatDate(row.getCell(13).getStringCellValue()));//海外到货日期
                }
                if(StringUtils.isNotEmpty(row.getCell(14).getStringCellValue())){
                    info.setOrdernum(row.getCell(14).getStringCellValue());//合同号（订单号）
                }
                if(StringUtils.isNotEmpty(row.getCell(15).getStringCellValue())){
                    info.setSupplier(row.getCell(15).getStringCellValue());//供应商名称
                }
                if(StringUtils.isNotEmpty(row.getCell(16).getStringCellValue()) && !"/".equals(row.getCell(16).getStringCellValue())){
                    info.setContractdate(this.formatDate(row.getCell(16).getStringCellValue()));//合同签订日期
                }
                if(StringUtils.isNotEmpty(row.getCell(17).getStringCellValue()) && !"/".equals(row.getCell(17).getStringCellValue())){
                    info.setConarrivaldate(this.formatDate(row.getCell(17).getStringCellValue()));//合同到货日期
                }
                if(StringUtils.isNotEmpty(row.getCell(18).getStringCellValue())){
                    info.setSupplyperson(row.getCell(18).getStringCellValue());//供应商联系人
                }
                if(StringUtils.isNotEmpty(row.getCell(19).getStringCellValue())){
                    info.setSupplycontact(row.getCell(19).getStringCellValue());//供应商联系方式
                }
                //付款方式
                String fkfs = "";
                if(StringUtils.isNotEmpty(row.getCell(20).getStringCellValue())){
                    fkfs += row.getCell(20).getStringCellValue() + ",";
                }
                if(StringUtils.isNotEmpty(row.getCell(21).getStringCellValue())){
                    fkfs += row.getCell(21).getStringCellValue() + ",";
                }
                if(StringUtils.isNotEmpty(row.getCell(22).getStringCellValue())){
                    fkfs += row.getCell(22).getStringCellValue() + ",";
                }
                if(StringUtils.isNotEmpty(row.getCell(23).getStringCellValue())){
                    fkfs += row.getCell(23).getStringCellValue() + ",";
                }
                if(StringUtils.isNotEmpty(row.getCell(24).getStringCellValue())){
                    fkfs += row.getCell(24).getStringCellValue() + ",";
                }
                if(StringUtils.isNotEmpty(row.getCell(25).getStringCellValue())){
                    fkfs += row.getCell(25).getStringCellValue() + ",";
                }
                info.setPayment(fkfs);
                if(StringUtils.isNotEmpty(row.getCell(26).getStringCellValue()) && !"/".equals(row.getCell(26).getStringCellValue())){
                    info.setMatarrivaldate(this.formatDate(row.getCell(26).getStringCellValue()));//物资到货日期
                }
                if(StringUtils.isNotEmpty(row.getCell(27).getStringCellValue())){
                    info.setUnaccreason(row.getCell(27).getStringCellValue());//物资未验收原因
                }
                if(StringUtils.isNotEmpty(row.getCell(28).getStringCellValue()) && !"/".equals(row.getCell(28).getStringCellValue())){
                    info.setAcceptdate(this.formatDate(row.getCell(28).getStringCellValue()));//物资验收日期
                }
                if(StringUtils.isNotEmpty(row.getCell(29).getStringCellValue())){
                    info.setNonstoreason(row.getCell(29).getStringCellValue());//仓库未入库原因
                }
                if(StringUtils.isNotEmpty(row.getCell(30).getStringCellValue()) && !"/".equals(row.getCell(30).getStringCellValue())){
                    info.setStoragedate(this.formatDate(row.getCell(30).getStringCellValue()));//仓库入库日期
                }
                if(StringUtils.isNotEmpty(row.getCell(31).getStringCellValue()) && !"/".equals(row.getCell(31).getStringCellValue())){
                    info.setPackingdate(this.formatDate(row.getCell(31).getStringCellValue()));//装箱日期
                }
                if(StringUtils.isNotEmpty(row.getCell(32).getStringCellValue()) && !"/".equals(row.getCell(32).getStringCellValue())){
                    info.setInvoicedate(this.formatDate(row.getCell(32).getStringCellValue()));//发票到票日期
                }
                if(StringUtils.isNotEmpty(row.getCell(33).getStringCellValue())){
                    info.setRemark(row.getCell(33).getStringCellValue());//备注
                }
                //状态：0 合同未签订；1 合同已签订；2 合同到货；3 物资装箱；4 发票到票；5 已完成；
                if(StringUtils.isNotEmpty(row.getCell(34).getStringCellValue())){
                    info.setState(5);//备注
                }
                info.setCreater(SecurityUtil.getLoginid());
                info.setModifier(SecurityUtil.getLoginid());
                list.add(info);
            }
            if(list.size() >100){//每100条数据保存一次，防止数据过多
                //保存并新建list
                int r = materialInfoMapper.insertBatch(list);
                if(r != list.size()){
                    throw new RuntimeException("添加失败，未完整添加数据到数据库！");
                }
                list = new ArrayList<>();
            }
        }
        if(list.size() >0){
            int r = materialInfoMapper.insertBatch(list);
            if(r != list.size()){
                throw new RuntimeException("添加失败，未完整添加数据到数据库！");
            }
        }
    }

    /**
     * @Author 钱敏杰
     * @Description 将日期字符串格式化为yyyy-MM-dd的格式
     * @Date 2019/1/22 15:17
     * @Param [date]
     * @return java.lang.String
     **/
    private String formatDate(String date){
        return date;
    }
}

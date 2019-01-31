package com.hy.service.purchase;

import com.github.pagehelper.PageHelper;
import com.hy.common.SecurityUtil;
import com.hy.dto.MaterialInfoDto;
import com.hy.dto.PurchaseSalesmanDto;
import com.hy.mapper.ms.MaterialInfoMapper;
import com.hy.dto.PurchaseTracerDto;
import com.hy.mapper.ms.PurchaseSalesmanMapper;
import com.hy.model.MaterialInfo;
import com.hy.mapper.ms.PurchaseTracerMapper;
import com.hy.model.PurchaseSalesman;
import com.hy.model.PurchaseTracer;
import com.hy.utils.DTOUtil;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.io.InputStream;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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

    @Override
    //根据id查询物资信息
    public MaterialInfoDto getMaterialInfoById(int id){
        MaterialInfo materialInfo = materialInfoMapper.selectByPrimaryKey(id);
        return DTOUtil.populate(materialInfo, MaterialInfoDto.class);
    }

    @Override
    //查询所有业务员
    public List<String> getEmpnumAll(){
        return materialInfoMapper.selectEmpnumAll();
    }

    /**
     * @Author 钱敏杰
     * @Description 分页查询物资信息
     * @Date 2019/1/22 16:55
     * @Param [pageNum, pageSize, filters, sort, dir, value,state,empnum]
     * @return java.util.List<com.hy.dto.MaterialInfoDto>
     **/
    @Override
    public List<MaterialInfoDto> getMaterialInfoPage(Integer pageNum, Integer pageSize,String filters, String sort, String dir, String value, String state, String empnum){
        PageHelper.startPage(pageNum, pageSize);
        List<MaterialInfo> list = materialInfoMapper.selectMaterialInfoPage(filters, sort, dir, value, state, empnum);
        return DTOUtil.populateList(list, MaterialInfoDto.class);
    }

    /**
     * @Author 钱敏杰
     * @Description 根据条件统计当前数据量
     * @Date 2019/1/22 17:02
     * @Param [filters, value,empnum]
     * @return int
     **/
    @Override
    public int countMaterialInfo(String filters, String value, String state, String empnum){
        int num = materialInfoMapper.selectMaterialInfoTotal(filters, value, state, empnum);
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
        info.setCreater(SecurityUtil.getLoginid());
        info.setModifier(SecurityUtil.getLoginid());
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
        if(infoList != null && infoList.size() >0){
            for(MaterialInfo info:infoList){
                info.setModifier(SecurityUtil.getLoginid());
            }
            return materialInfoMapper.insertBatch(infoList);
        }
        return 0;
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
        info.setModifier(SecurityUtil.getLoginid());
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
     * @Date 2019/1/25 11:01
     * @Param [file]
     * @return void
     **/
    @Override
    @Transactional
    public void importMaterialExcel(MultipartFile file) throws IOException {
        InputStream input = null;
        Workbook workbook = null;
        try{
            input = file.getInputStream();
            workbook = WorkbookFactory.create(input);
            //只读取第一张sheet
            Sheet sheet = workbook.getSheetAt(0);
            //当前业务员
            List<PurchaseSalesman> salesList = purchaseSalesmanMapper.selectSalesman(null);
            Map<String, PurchaseSalesman> salesMap = salesList.stream().collect(Collectors.toMap(PurchaseSalesman::getSalesmanname, a -> a, (k1, k2) -> k1));
            List<MaterialInfo> list = new ArrayList<>();
            MaterialInfo info = null;
            for(int i=1;i<sheet.getLastRowNum();i++){
                //第一行数据为标题，去除
                Row row = sheet.getRow(i);
                if(row != null){//判断是否为规定格式的Excel文件
                    info = this.parseMaterial(row, salesMap);
                    list.add(info);
                }
                if(list.size() >999){//每1000条数据保存一次，防止数据过多
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
        }catch (Exception e){
            throw new RuntimeException("物资信息Excel数据导入异常", e);
        }finally{
            if(workbook != null){
                workbook.close();
            }
            if(input != null){
                input.close();
            }
        }
    }

    /**
     * @Author 钱敏杰
     * @Description 生成物资信息对象
     * @Date 2019/1/25 10:20
     * @Param [row, salesMap]
     * @return com.hy.model.MaterialInfo
     **/
    private MaterialInfo parseMaterial(Row row, Map<String, PurchaseSalesman> salesMap){
        MaterialInfo info = new MaterialInfo();
        info.setApplytype(this.parseString(row.getCell(0)));//申请类别
        info.setCompanyname(this.parseString(row.getCell(1)));//公司名称
        //物资类别
        info.setMattype(this.parseString(row.getCell(2)));
        info.setBatch(this.parseString(row.getCell(3)));//追溯号(批次)
        info.setMaterialname(this.parseString(row.getCell(4)));//物资名称
        info.setMaterialdes(this.parseString(row.getCell(5)));//物资描述
        if(row.getCell(6) != null){
            Double d = Double.valueOf(row.getCell(6).getNumericCellValue());
            info.setAmount(d);//数量
        }
        info.setUnit(this.parseString(row.getCell(7)));//单位
        //业务员
        String name = this.parseString(row.getCell(8));
        if(StringUtils.isNotEmpty(name)){
            info.setEmpname(name);//业务员姓名
            if(salesMap != null){
                PurchaseSalesman man = salesMap.get(name);
                if(man != null){
                    info.setEmpnum(man.getSalesmannum());//业务员员工号
                }
            }
        }
        info.setApplydept(this.parseString(row.getCell(9)));//申请部门
        info.setApplyperson(this.parseString(row.getCell(10)));//申请联系人
        info.setDispatchdate(this.parseDate(row.getCell(11)));//物资分派日期
        info.setRequireddate(this.parseDate(row.getCell(12)));//要求到货日期
        info.setOverseasdate(this.parseDate(row.getCell(13)));//海外到货日期
        info.setOrdernum(this.parseString(row.getCell(14)));//合同号（订单号）
        info.setSupplier(this.parseString(row.getCell(15)));//供应商名称
        info.setContractdate(this.parseDate(row.getCell(16)));//合同签订日期
        info.setConarrivaldate(this.parseDate(row.getCell(17)));//合同到货日期
        info.setSupplyperson(this.parseString(row.getCell(18)));//供应商联系人
        info.setSupplycontact(this.parseString(row.getCell(19)));//供应商联系方式
        info.setPayment(this.parseString(row.getCell(20)));//付款方式
        //付款进度
        String progress = "";
        String fkfs1 = this.parseString(row.getCell(21));
        if(StringUtils.isNotEmpty(fkfs1)){
            progress += fkfs1 + ";";
        }
        String fkfs2 = this.parseString(row.getCell(22));
        if(StringUtils.isNotEmpty(fkfs2)){
            progress += fkfs2 + ";";
        }
        String fkfs3 = this.parseString(row.getCell(23));
        if(StringUtils.isNotEmpty(fkfs3)){
            progress += fkfs3 + ";";
        }
        String fkfs4 = this.parseString(row.getCell(24));
        if(StringUtils.isNotEmpty(fkfs4)){
            progress += fkfs4 + ";";
        }
        String fkfs5 = this.parseString(row.getCell(25));
        if(StringUtils.isNotEmpty(fkfs5)){
            progress += fkfs5 + ";";
        }
        info.setPayprogress(progress);
        info.setMatarrivaldate(this.parseDate(row.getCell(26)));//物资到货日期
        info.setUnaccreason(this.parseString(row.getCell(27)));//物资未验收原因
        info.setAcceptdate(this.parseDate(row.getCell(28)));//物资验收日期
        info.setNonstoreason(this.parseString(row.getCell(29)));//仓库未入库原因
        info.setStoragedate(this.parseDate(row.getCell(30)));//仓库入库日期
        info.setPackingdate(this.parseDate(row.getCell(31)));//装箱日期
        info.setInvoicedate(this.parseDate(row.getCell(32)));//发票到票日期
        info.setRemark(this.parseString(row.getCell(33)));//备注
        //状态：合同未签订；合同已签订；合同到货；物资装箱；发票到票；已完成；
        if(StringUtils.isNotEmpty(this.parseString(row.getCell(34)))){
            info.setState("已完成");//备注
        }else{//默认值
            info.setState("合同未签订");
        }
        info.setCreater(SecurityUtil.getLoginid());
        info.setModifier(SecurityUtil.getLoginid());
        return info;
    }

    /**
     * @Author 钱敏杰
     * @Description 解析Excel表格单元格的日期数据
     * @Date 2019/1/25 15:40
     * @Param [cell]
     * @return java.lang.String
     **/
    private String parseDate(Cell cell){
        String date = null;
        if(cell != null){
            CellType type = cell.getCellType();
            if(CellType.NUMERIC.equals(type)){
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                Date d = cell.getDateCellValue();
                date = sdf.format(d);
            }else if(CellType.STRING.equals(type)){
                //字符串格式的日期，直接转为相应格式
                date = cell.getStringCellValue();
                if(StringUtils.isNotEmpty(date)){
                    if(date.contains(".")){
                        //改变格式
                        date = date.replaceAll("\\.", "-");
                    }else if("/".equals(date)){//空数据
                        date = "";
                    }
                }
            }else{
                //无其他格式，不处理
            }
        }
        return date;
    }

    /**
     * @Author 钱敏杰
     * @Description 解析Excel表格单元格的字符串格式数据
     * @Date 2019/1/25 14:54
     * @Param [cell]
     * @return java.lang.String
     **/
    private String parseString(Cell cell){
        String str = null;
        if(cell != null){
            CellType type = cell.getCellType();
            if(CellType.NUMERIC.equals(type)){
                //数字格式转为字符串，不含小数点
                Double d = Double.valueOf(cell.getNumericCellValue());
                DecimalFormat df = new DecimalFormat("######0");
                str = df.format(d);
            }else if(CellType.STRING.equals(type)){
                //字符串格式
                str = cell.getStringCellValue();
            }else{
                //无其他格式，不处理
            }
        }
        return str;
    }

    @Override
    //跟单员只能查看自己对应业务员的物资信息
    public List<MaterialInfoDto> getInfoByTracer(Integer pageNum, Integer pageSize,String filters, String sort, String dir, String value, String state){
        PageHelper.startPage(pageNum, pageSize);
        List<MaterialInfo> list = materialInfoMapper.selectInfoByTracer(SecurityUtil.getLoginid(), filters, sort, dir, value, state);
        return DTOUtil.populateList(list, MaterialInfoDto.class);

    }

    @Override
    //统计跟单员对应业务员的物资信息的数量
    public int getInfoByTracerTotal(String filters, String value, String state){
        return materialInfoMapper.selectInfoByTracerTotal(SecurityUtil.getLoginid(), filters, value, state);
    }

}

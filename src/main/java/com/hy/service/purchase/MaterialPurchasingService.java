package com.hy.service.purchase;

import com.hy.dto.MaterialInfoDto;
import com.hy.dto.PurchaseSalesmanDto;
import com.hy.model.MaterialInfo;
import com.hy.dto.PurchaseTracerDto;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.util.List;

/**
 * @Auther: 沈超宇
 * @Date: 2019/1/21 16:12
 * @Description:物资采购service
 */
public interface MaterialPurchasingService {
    //查询业务员信息
    List<PurchaseSalesmanDto> getSalesman(String value);
    //新增业务员信息
    boolean addSalesman(PurchaseSalesmanDto purchaseSalesmanDtos);
    //删除业务员
    void delSalesman(int id);
    //查询跟单员
    List<PurchaseTracerDto> getTracer(String filters, int sid, String value);
    //新增跟单员
    boolean addTracer(PurchaseTracerDto purchaseTracerDto);
    //删除跟单员
    boolean delTracer(int id);

    //根据id查询物资信息
    MaterialInfoDto getMaterialInfoById(int id);

    //查询所有业务员
    List<String> getEmpnumAll();

    /**
     * @Author 钱敏杰
     * @Description 分页查询物资信息
     * @Date 2019/1/22 16:55
     * @Param [pageNum, pageSize, filters, sort, dir, value, state, empnum]
     * @return java.util.List<com.hy.dto.MaterialInfoDto>
     **/
    List<MaterialInfoDto> getMaterialInfoPage(Integer pageNum, Integer pageSize,String filters, String sort, String dir, String value, String state, String empnum);

    /**
     * @Author 钱敏杰
     * @Description 根据条件统计当前数据量
     * @Date 2019/1/22 17:02
     * @Param [filters, value, empnum]
     * @return int
     **/
    int countMaterialInfo(String filters, String value, String state, String empnum);

    /**
     * @Author 钱敏杰
     * @Description 添加物资信息
     * @Date 2019/1/22 11:02
     * @Param [infoDto]
     * @return int
     **/
    int addMaterialInfo(MaterialInfoDto infoDto);

    /**
     * @Author 钱敏杰
     * @Description 批量添加物资信息
     * @Date 2019/1/22 11:05
     * @Param [infoList]
     * @return int
     **/
    int addMaterialInfoBatch(List<MaterialInfo> infoList);

    /**
     * @Author 钱敏杰
     * @Description 更新当前物资信息
     * @Date 2019/1/22 13:41
     * @Param [infoDto]
     * @return int
     **/
    int updateMaterialInfo(MaterialInfoDto infoDto);

    /**
     * @Author 钱敏杰
     * @Description 删除当前物资信息
     * @Date 2019/1/22 13:42
     * @Param [id]
     * @return int
     **/
    int deleteMaterialInfo(Integer id);

    /**
     * @Author 钱敏杰
     * @Description 解析Excel文件并保存数据到数据库
     * @Date 2019/1/25 11:01
     * @Param [file]
     * @return void
     **/
    void importMaterialExcel(MultipartFile file) throws IOException;

    //跟单员只能查看自己对应业务员的物资信息
    List<MaterialInfoDto> getInfoByTracer(Integer pageNum, Integer pageSize,String filters, String sort, String dir, String value, String state);

    //统计跟单员对应业务员的物资信息的数量
    int getInfoByTracerTotal(String filters, String value, String state);
}

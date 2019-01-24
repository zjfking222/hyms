package com.hy.service.purchase;

import com.hy.dto.MaterialInfoDto;
import com.hy.dto.PurchaseSalesmanDto;
import com.hy.model.MaterialInfo;

import java.io.IOException;
import java.io.InputStream;
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
    //根据业务员员工号删除所有该业务员、跟单员信息
    boolean delSalesman(int id);

    /**
     * @Author 钱敏杰
     * @Description 分页查询物资信息
     * @Date 2019/1/22 16:55
     * @Param [pageNum, pageSize, filters, sort, dir, value]
     * @return java.util.List<com.hy.dto.MaterialInfoDto>
     **/
    List<MaterialInfoDto> getMaterialInfoPage(Integer pageNum, Integer pageSize,String filters, String sort, String dir, String value);

    /**
     * @Author 钱敏杰
     * @Description 根据条件统计当前数据量
     * @Date 2019/1/22 17:02
     * @Param [filters, value]
     * @return int
     **/
    int countMaterialInfo(String filters, String value);

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
     * @Date 2019/1/22 16:23
     * @Param [input]
     * @return void
     **/
    void importMaterialExcel(InputStream input) throws IOException;
}

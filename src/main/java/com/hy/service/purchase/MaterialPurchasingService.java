package com.hy.service.purchase;

import com.hy.dto.PurchaseSalesmanDto;
import com.hy.dto.PurchaseTracerDto;

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
}

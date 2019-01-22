package com.hy.service.purchase;

import com.hy.dto.PurchaseSalesmanDto;

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
}

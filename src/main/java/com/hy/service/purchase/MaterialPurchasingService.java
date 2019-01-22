package com.hy.service.purchase;

import com.hy.dto.SalesmanTracerDto;

import java.util.List;

/**
 * @Auther: 沈超宇
 * @Date: 2019/1/21 16:12
 * @Description:物资采购service
 */
public interface MaterialPurchasingService {
    //查询当前业务员的信息以及跟单员信息
    List<SalesmanTracerDto> getSalesmanTracer(String salesmannum);
    //查询去重后的业务员信息,用于初始化显示业务员
    List<SalesmanTracerDto> getDistinctSalesman(String value);
    //更新业务员、跟单员信息
    void setSalesmanTracer(String salesmannum, List<SalesmanTracerDto> salesmanTracerDtos, int[] array);
    //根据业务员员工号删除所有该业务员、跟单员信息
    boolean delBySalesmannum(String salesmannum);
}

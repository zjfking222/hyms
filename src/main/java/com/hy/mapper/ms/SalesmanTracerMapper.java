package com.hy.mapper.ms;

import com.hy.model.SalesmanTracer;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Auther: 沈超宇
 * @Date: 2019/1/21 14:58
 * @Description:业务员、跟单员关系表mapper
 */
@Repository
public interface SalesmanTracerMapper {
    //查询当前业务员的信息以及跟单员信息
    List<SalesmanTracer> selectSalesmanTracer(@Param("salesmannum") String salesmannum);
    //查询去重后的业务员信息,用于初始化显示业务员
    List<SalesmanTracer> selectDistinctSalesman(@Param("value") String value);
    //新增业务员、跟单员信息
    Integer insertSalesmanTracer(List<SalesmanTracer> salesmanTracer);
    //根据id删除对应业务员、跟单员信息
    Integer deleteSalesmanTracer(int[] array);
    //根据业务员员工号删除所有该业务员的信息
    Integer deleteBySalesmannum(@Param("salesmannum") String salesmannum);
}

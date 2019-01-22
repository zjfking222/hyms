package com.hy.controller.purchase;

import com.hy.common.ResultObj;
import com.hy.dto.SalesmanTracerDto;
import com.hy.enums.ResultCode;
import com.hy.service.purchase.MaterialPurchasingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @Auther: 沈超宇
 * @Date: 2019/1/22 9:05
 * @Description:物资采购controller
 */
@RestController
@RequestMapping("/purchase")
public class MaterialPurchasingController {

    @Autowired
    private MaterialPurchasingService materialPurchasingService;


    @PostMapping("/salesmanTracer/getSalesmanTracer")
    //查询当前业务员的信息以及跟单员信息
    public ResultObj getSalesmanTracer(String salesmannum){
        return ResultObj.success(materialPurchasingService.getSalesmanTracer(salesmannum));
    }

    @PostMapping("/salesmanTracer/getDistinctSalesman")
    //查询去重后的业务员信息,用于初始化显示业务员
    public ResultObj getDistinctSalesman(String value){
        return ResultObj.success(materialPurchasingService.getDistinctSalesman(value));
    }

    @PostMapping("/salesmanTracer/setSalesmanTracer")
    //更新业务员、跟单员信息
    public ResultObj setSalesmanTracer(String salesmannum, List<SalesmanTracerDto> salesmanTracerDtos, int[] array){
        materialPurchasingService.setSalesmanTracer(salesmannum, salesmanTracerDtos, array);
        return ResultObj.success();
    }

    @PostMapping("/salesmanTracer/delBySalesmannum")
    //根据业务员员工号删除所有该业务员、跟单员信息
    public ResultObj delBySalesmannum(String salesmannum){
        return materialPurchasingService.delBySalesmannum(salesmannum) ?
                ResultObj.success() :
                ResultObj.error(ResultCode.ERROR_DELETE_FAILED);
    }
}

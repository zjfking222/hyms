package com.hy.controller.purchase;

import com.hy.common.ResultObj;
import com.hy.dto.PurchaseSalesmanDto;
import com.hy.enums.ResultCode;
import com.hy.service.purchase.MaterialPurchasingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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


    @PostMapping("/salesman/getSalesman")
    //查询业务员信息
    public ResultObj getSalesman(@RequestParam(required = false) String value){
        return ResultObj.success(materialPurchasingService.getSalesman(value));
    }

    @PostMapping("/salesman/addSalesman")
    //更新业务员、跟单员信息
    public ResultObj addSalesman(PurchaseSalesmanDto purchaseSalesmanDto){
        return materialPurchasingService.addSalesman(purchaseSalesmanDto) ?
                ResultObj.success() :
                ResultObj.error(ResultCode.ERROR_ADD_FAILED);
    }

    @PostMapping("/salesman/delSalesman")
    //删除业务员
    public ResultObj delSalesman(int id){
        return materialPurchasingService.delSalesman(id) ?
                ResultObj.success() :
                ResultObj.error(ResultCode.ERROR_DELETE_FAILED);
    }
}

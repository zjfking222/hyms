package com.hy.controller.purchase;

import com.hy.common.ResultObj;
import com.hy.dto.MaterialInfoDto;
import com.hy.dto.PurchaseSalesmanDto;
import com.hy.enums.ResultCode;
import com.hy.model.MaterialInfo;
import com.hy.service.purchase.MaterialPurchasingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    /**
     * @Author 钱敏杰
     * @Description 根据条件分页查询
     * @Date 2019/1/22 17:06
     * @Param [page, pageSize, filters, sort, dir, value]
     * @return com.hy.common.ResultObj
     **/
    @PostMapping("/planner/getMaterialInfoPage")
    public ResultObj getMaterialInfoPage(Integer page, Integer pageSize, String filters, String sort, String dir, String value) {
        List<MaterialInfoDto> list = materialPurchasingService.getMaterialInfoPage(page, pageSize, filters, sort, dir, value);
        int total = materialPurchasingService.countMaterialInfo(filters, value);
        Map<String, Object> map = new HashMap<>();
        map.put("data", list);
        map.put("total", total);
        return ResultObj.success(map);
    }

    /**
     * @Author 钱敏杰
     * @Description 添加一条物资信息数据
     * @Date 2019/1/22 11:11
     * @Param [infoDto]
     * @return com.hy.common.ResultObj
     **/
    @PostMapping("/planner/addMaterialInfo")
    public ResultObj addMaterialInfo(MaterialInfoDto infoDto){
        return materialPurchasingService.addMaterialInfo(infoDto) >0 ?
                ResultObj.success() : ResultObj.error(ResultCode.ERROR_ADD_FAILED);
    }

    @PostMapping("/planner/deleteMaterialInfo")
    public ResultObj deleteMaterialInfo(Integer id){
        return materialPurchasingService.deleteMaterialInfo(id) >0 ?
                ResultObj.success() : ResultObj.error(ResultCode.ERROR_ADD_FAILED);
    }
}

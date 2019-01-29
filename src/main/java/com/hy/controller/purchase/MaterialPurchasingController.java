package com.hy.controller.purchase;

import com.hy.common.ResultObj;
import com.hy.dto.MaterialInfoDto;
import com.hy.dto.PurchaseSalesmanDto;
import com.hy.dto.PurchaseTracerDto;
import com.hy.enums.ResultCode;
import com.hy.service.purchase.MaterialPurchasingService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
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
    //查询业务员
    public ResultObj getSalesman(@RequestParam(required = false) String value){
        return ResultObj.success(materialPurchasingService.getSalesman(value));
    }

    @PostMapping("/salesman/addSalesman")
    //新增业务员
    public ResultObj addSalesman(PurchaseSalesmanDto purchaseSalesmanDto){
        return materialPurchasingService.addSalesman(purchaseSalesmanDto) ?
                ResultObj.success() :
                ResultObj.error(ResultCode.ERROR_ADD_FAILED);
    }

    @PostMapping("/salesman/delSalesman")
    //删除业务员
    public ResultObj delSalesman(int id){
        materialPurchasingService.delSalesman(id);
        return ResultObj.success();
    }

    @PostMapping("/tracer/getTracer")
    //查询跟单员
    public ResultObj getTracer(@RequestParam(required = false) String filters, int sid, @RequestParam(required = false) String value){
        return ResultObj.success(materialPurchasingService.getTracer(filters, sid, value));
    }

    @PostMapping("/tracer/addTracer")
    //新增跟单员
    public ResultObj addTracer(PurchaseTracerDto purchaseTracerDto){
        return materialPurchasingService.addTracer(purchaseTracerDto) ?
                ResultObj.success() :
                ResultObj.error(ResultCode.ERROR_ADD_FAILED);
    }

    @PostMapping("/tracer/delTracer")
    //删除跟单员
    public ResultObj delTracer(int id){
        return materialPurchasingService.delTracer(id) ?
                ResultObj.success() :
                ResultObj.error(ResultCode.ERROR_DELETE_FAILED);
    }

    @PostMapping("/material/getMaterialById")
    //根据id查询物资信息
    public ResultObj getMaterialById(int id){
        return ResultObj.success(materialPurchasingService.getMaterialInfoById(id));
    }

    @PostMapping("/material/getEmpnumAll")
    //查询所有业务员
    public ResultObj getgetEmpnumAll(){
        return ResultObj.success(materialPurchasingService.getEmpnumAll());
    }

    /**
     * @Author 钱敏杰
     * @Description 根据条件分页查询
     * @Date 2019/1/22 17:06
     * @Param [page, pageSize, filters, sort, dir, value]
     * @return com.hy.common.ResultObj
     **/
    @PostMapping("/planner/getMaterialInfoPage")
    public ResultObj getMaterialInfoPage(Integer page, Integer pageSize, String filters, String sort, String dir, String value, String state) {
        List<MaterialInfoDto> list = materialPurchasingService.getMaterialInfoPage(page, pageSize, filters, sort, dir, value, state);
        int total = materialPurchasingService.countMaterialInfo(filters, value, state);
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
    @PostMapping("/planner/updateMaterialInfo")
    public ResultObj addMaterialInfo(MaterialInfoDto infoDto){
        if(infoDto != null && infoDto.getId() != null){//更新
            int i = materialPurchasingService.updateMaterialInfo(infoDto);
            return  i>0 ? ResultObj.success() : ResultObj.error(ResultCode.ERROR_UPDATE_FAILED);
        }else{//添加
            int i = materialPurchasingService.addMaterialInfo(infoDto);
            return  i>0 ? ResultObj.success() : ResultObj.error(ResultCode.ERROR_ADD_FAILED);
        }
    }

    /**
     * @Author 钱敏杰
     * @Description 删除当前物资信息数据
     * @Date 2019/1/24 14:42
     * @Param [id]
     * @return com.hy.common.ResultObj
     **/
    @PostMapping("/planner/deleteMaterialInfo")
    public ResultObj deleteMaterialInfo(Integer id){
        int i = materialPurchasingService.deleteMaterialInfo(id);
        return i >0 ? ResultObj.success() : ResultObj.error(ResultCode.ERROR_ADD_FAILED);
    }

    @PostMapping("/planner/importMaterialExcel")
    public ResultObj importMaterialExcel(@RequestParam("file") MultipartFile[] file) throws IOException {
        materialPurchasingService.importMaterialExcel(file[0]);
        return ResultObj.success();
    }

    @PostMapping("/planner/getInfoByTracer")
    public ResultObj getInfoByTracer(Integer page, Integer pageSize,String filters, String sort, String dir, String value, String state) {
        List<MaterialInfoDto> list = materialPurchasingService.getInfoByTracer(page, pageSize, filters, sort, dir, value, state);
        int total = materialPurchasingService.getInfoByTracerTotal(filters, value, state);
        Map<String, Object> map = new HashMap<>();
        map.put("data", list);
        map.put("total", total);
        return ResultObj.success(map);
    }
}

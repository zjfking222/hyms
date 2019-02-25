package com.hy.controller.purchase;

import com.hy.common.ResultObj;
import com.hy.common.SecurityUtil;
import com.hy.dto.MaterialInfoDto;
import com.hy.dto.MaterialSalesmanDto;
import com.hy.dto.MaterialTracerDto;
import com.hy.enums.ResultCode;
import com.hy.service.purchase.MaterialPurchasingService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
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

    private static final Logger logger = LoggerFactory.getLogger(MaterialPurchasingController.class);
    @Autowired
    private MaterialPurchasingService materialPurchasingService;


    @PostMapping("/salesman/getSalesman")
    //查询业务员
    public ResultObj getSalesman(@RequestParam(required = false) String value){
        return ResultObj.success(materialPurchasingService.getSalesman(value));
    }

    @PostMapping("/salesman/addSalesman")
    //新增业务员
    public ResultObj addSalesman(MaterialSalesmanDto purchaseSalesmanDto){
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
    public ResultObj getTracer(@RequestParam(required = false) String filters, Integer sid, @RequestParam(required = false) String value){
        return ResultObj.success(materialPurchasingService.getTracer(filters, sid, value));
    }

    @PostMapping("/tracer/addTracer")
    //新增跟单员
    public ResultObj addTracer(MaterialTracerDto purchaseTracerDto){
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
        List<MaterialInfoDto> list = materialPurchasingService.getMaterialInfoPage(page, pageSize, filters, sort, dir, value, state, null);
        int total = materialPurchasingService.countMaterialInfo(filters, value, state, null);
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

    /**
     * @Author 钱敏杰
     * @Description 查询当前业务员的分页物资信息
     * @Date 2019/1/29 14:35
     * @Param [page, pageSize, filters, sort, dir, value, state]
     * @return com.hy.common.ResultObj
     **/
    @PostMapping("/salesman/getSalesmanInfoPage")
    public ResultObj getSalesmanInfoPage(Integer page, Integer pageSize, String filters, String sort, String dir, String value, String state) {
        String empnum = SecurityUtil.getLoginid();
        List<MaterialInfoDto> list = materialPurchasingService.getMaterialInfoPage(page, pageSize, filters, sort, dir, value, state, empnum);
        int total = materialPurchasingService.countMaterialInfo(filters, value, state, empnum);
        Map<String, Object> map = new HashMap<>();
        map.put("data", list);
        map.put("total", total);
        return ResultObj.success(map);
    }

    /**
     * @Author 钱敏杰
     * @Description 计划员导出物资信息Excel文件
     * @Date 2019/2/1 15:04
     * @Param [filters, sort, dir, value, state, response]
     * @return void
     **/
    @PostMapping("/planner/exportMaterialInfo")
    public void exportPlannerInfo(String filters, String sort, String dir, String value, String state, HttpServletResponse response) {
        //查询当前需要导出的数据
        List<MaterialInfoDto> list = materialPurchasingService.getMaterialInfoPage(filters, sort, dir, value, state, null);
        OutputStream os = null;
        try{
            this.setResponseHeader(response, "计划员物资信息表.xls");
            os = response.getOutputStream();
            //导出Excel
            materialPurchasingService.exportMaterialInfo(os, list);
        }catch (Exception e){
            logger.error("导出计划员物资信息Excel失败！", e);
        }finally {
            if(os != null){
                try {
                    os.close();
                } catch (IOException e) {
                    logger.error("关闭计划员物资信息输出流失败！", e);
                }
            }
        }
    }

    /**
     * @Author 钱敏杰
     * @Description 业务员导出物资信息Excel文件
     * @Date 2019/2/2 11:11
     * @Param [filters, sort, dir, value, state, response]
     * @return void
     **/
    @PostMapping("/salesman/exportSalesmanInfo")
    public void exportSalesmanInfo(String filters, String sort, String dir, String value, String state, HttpServletResponse response) {
        //查询当前需要导出的数据
        String empnum = SecurityUtil.getLoginid();
        List<MaterialInfoDto> list = materialPurchasingService.getMaterialInfoPage(filters, sort, dir, value, state, empnum);
        OutputStream os = null;
        try{
            this.setResponseHeader(response, "业务员物资信息表.xls");
            os = response.getOutputStream();
            //导出Excel
            materialPurchasingService.exportMaterialInfo(os, list);
        }catch (Exception e){
            logger.error("导出计划员物资信息Excel失败！", e);
        }finally {
            if(os != null){
                try {
                    os.close();
                } catch (IOException e) {
                    logger.error("关闭计划员物资信息输出流失败！", e);
                }
            }
        }
    }

    /**
     * @Author 钱敏杰
     * @Description 跟单员导出物资信息Excel文件
     * @Date 2019/2/2 11:11
     * @Param [filters, sort, dir, value, state, response]
     * @return void
     **/
    @PostMapping("/tracer/exportTracerInfo")
    public void exportTracerInfo(String filters, String sort, String dir, String value, String state, HttpServletResponse response) {
        //查询当前需要导出的数据
        List<MaterialInfoDto> list = materialPurchasingService.getInfoByTracer(filters, sort, dir, value, state);
        OutputStream os = null;
        try{
            this.setResponseHeader(response, "跟单员物资信息表.xls");
            os = response.getOutputStream();
            //导出Excel
            materialPurchasingService.exportMaterialInfo(os, list);
        }catch (Exception e){
            logger.error("导出计划员物资信息Excel失败！", e);
        }finally {
            if(os != null){
                try {
                    os.close();
                } catch (IOException e) {
                    logger.error("关闭计划员物资信息输出流失败！", e);
                }
            }
        }
    }

    /**
     * @Author 钱敏杰
     * @Description 设置响应参数及文件名
     * @Date 2019/2/2 8:08
     * @Param [response, fileName]
     * @return void
     **/
    private void setResponseHeader(HttpServletResponse response, String fileName) {
        try {
            try {
                fileName = new String(fileName.getBytes(),"ISO8859-1");
            } catch (UnsupportedEncodingException e) {
                logger.error("", e);
            }
            response.setContentType("application/octet-stream;charset=ISO8859-1");
            response.setHeader("Content-Disposition", "attachment;filename="+ fileName);
            response.addHeader("Pargam", "no-cache");
            response.addHeader("Cache-Control", "no-cache");
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}

package com.hy.controller.bo;

import com.hy.common.ResultObj;
import com.hy.enums.ResultCode;
import com.hy.model.ReportInfo;
import com.hy.service.bo.BoConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;

/**
 * @Auther: 钱敏杰
 * @Date: 2018/12/3 8:36
 * @Description:BO报表权限配置controller
 */
@RestController
@RequestMapping("/bo")
public class BoConfigController {
    @Autowired
    private BoConfigService boConfigService;

    //报表列表
    @RequestMapping("/getReportInfo")
    public ResultObj getReportInfo(int page, int pageSize, @RequestParam(required = false) String value, @RequestParam(required = false) String sort,
                                   @RequestParam(required = false) String dir) {
        HashMap<String, Object> map = new HashMap<>();
        map.put("data", boConfigService.getReportInfo(page, pageSize, value, sort, dir));
        map.put("total", boConfigService.getReportInfoTotal(value));
        return ResultObj.success(map);
    }

    @RequestMapping("/addReportInfo")
    public ResultObj addReportInfo(ReportInfo reportInfo){
        return boConfigService.addReportInfo(reportInfo)?
                ResultObj.success():
                ResultObj.error(ResultCode.ERROR_ADD_FAILED);
    }

    @RequestMapping("/setReportInfo")
    public ResultObj setReportInfo(ReportInfo reportInfo){
        return boConfigService.setReportInfo(reportInfo)?
                ResultObj.success():
                ResultObj.error(ResultCode.ERROR_UPDATE_FAILED);
    }

    @RequestMapping("/delReportInfo")
    public ResultObj delReportInfo(int id){
        return boConfigService.delReportInfo(id)?
                ResultObj.success():
                ResultObj.error(ResultCode.ERROR_DELETE_FAILED);
    }
}

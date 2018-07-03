package com.hy.controller.crm;

import com.hy.common.ResultObj;
import com.hy.dto.CrmFirmsDto;
import com.hy.enums.ResultCode;
import com.hy.service.crm.FirmsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;

@RestController
@RequestMapping("/crm")
public class FirmsController {

    @Autowired
    private FirmsService firmsService;

    @PostMapping("/firm/add")
    public ResultObj addCrmFirm(CrmFirmsDto crmFirmsDto){
        return firmsService.addCrmFirm(crmFirmsDto)?
                ResultObj.success():
                ResultObj.error(ResultCode.ERROR_ADD_FAILED);
    }

    @PostMapping("/firm/set")
    public ResultObj setCrmFirm(CrmFirmsDto crmFirmsDto){
        return firmsService.setCrmFirm(crmFirmsDto)?
                ResultObj.success():
                ResultObj.error(ResultCode.ERROR_UPDATE_FAILED);
    }

    @PostMapping("/firm/del")
    public ResultObj delCrmFirm(int id){
        return firmsService.delCrmFirm(id)?
                ResultObj.success():
                ResultObj.error(ResultCode.ERROR_DELETE_FAILED);
    }

    @PostMapping("/firm/get")
    public ResultObj getCrmFirm(int page, int pageSize, @RequestParam(required = false) String value,
                                @RequestParam(required = false) String sort,
                                @RequestParam(required = false) String dir){
        HashMap<String,Object> map = new HashMap<>();

        //假如前端传回的排序号为btid.name,需要转化为btid
        try {
            if(sort.equals("btid.name")){
                sort = "btid";
            }
        }catch (Exception e)
        {
        }

        map.put("data",firmsService.getCrmFirm(page, pageSize, value, sort, dir));
        map.put("total",firmsService.getCrmFirmTotal(value));
        return ResultObj.success(map);
    }

}

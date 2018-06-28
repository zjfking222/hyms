package com.hy.controller.crm;

import com.hy.common.ResultObj;
import com.hy.dto.CrmBusinesstypeDto;
import com.hy.enums.ResultCode;
import com.hy.model.CrmBusinesstype;
import com.hy.service.crm.BusinessTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/crm")
public class BusinessTypeController {

    @Autowired
    private BusinessTypeService businessTypeService;

    @PostMapping("/business/get")
    public ResultObj getBusinessType(){
        return ResultObj.success(businessTypeService.getBusinessType());
    }

    @PostMapping("/business/search")
    public ResultObj searchBusinessType(String name){
        return ResultObj.success(businessTypeService.searchBusinessTypeByName(name));
    }

    @PostMapping("/business/set")
    public ResultObj setBusinessType(CrmBusinesstypeDto crmBusinesstypeDto){
        return businessTypeService.setBusinessType(crmBusinesstypeDto)?
                ResultObj.success():
                ResultObj.error(ResultCode.ERROR_UPDATE_FAILED);
    }

    @PostMapping("/business/del")
    public ResultObj delBusinessType(Integer id){
        return businessTypeService.delBusinessType(id)?
                ResultObj.success():
                ResultObj.error(ResultCode.ERROR_DELETE_FAILED);
    }

    @PostMapping("/business/add")
    public ResultObj addBusinessType(CrmBusinesstypeDto crmBusinesstypeDto){
        return businessTypeService.addBusinessType(crmBusinesstypeDto)?
                ResultObj.success():
                ResultObj.error(ResultCode.ERROR_ADD_FAILED);
    }
}

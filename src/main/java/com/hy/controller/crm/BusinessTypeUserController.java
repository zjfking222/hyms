package com.hy.controller.crm;

import com.hy.common.ResultObj;
import com.hy.dto.CrmBusinesstypeUserDto;
import com.hy.enums.ResultCode;
import com.hy.service.crm.BusinessTypeUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/crm")
public class BusinessTypeUserController {

    @Autowired
    private BusinessTypeUserService businessTypeUserService;

    @PostMapping("/businessUser/get")
    public ResultObj getBusinessUser(int btid){
        return ResultObj.success(businessTypeUserService.getBusinesstypeUser(btid));
    }

    @PostMapping("/businessUser/set")
    public ResultObj setBusinessUser(CrmBusinesstypeUserDto crmBusinesstypeUserDto){
        return businessTypeUserService.setBusinesstypeUser(crmBusinesstypeUserDto) ?
                ResultObj.success():
                ResultObj.error(ResultCode.ERROR_UNKNOWN);
    }
}

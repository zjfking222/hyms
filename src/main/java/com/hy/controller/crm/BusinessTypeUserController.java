package com.hy.controller.crm;

import com.hy.common.ResultObj;
import com.hy.dto.CrmBusinesstypeUserDto;
import com.hy.enums.ResultCode;
import com.hy.service.crm.BusinessTypeUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/crm")
public class BusinessTypeUserController {

    @Autowired
    private BusinessTypeUserService businessTypeUserService;

    @PostMapping("/businessUser/get")
    public ResultObj getBusinessUser(int btid, @RequestParam(required = false) String sort,
                                     @RequestParam(required = false) String dir){
        return ResultObj.success(businessTypeUserService.getBusinesstypeUser(btid));
    }

    @PostMapping("/businessUser/set")
    public ResultObj setBusinessUser(@RequestBody CrmBusinesstypeUserDto crmBusinesstypeUserDto){
        System.out.println(crmBusinesstypeUserDto.getnHrmResource().length);
        System.out.println(crmBusinesstypeUserDto.getrHrmResource().length);
        return businessTypeUserService.setBusinesstypeUser(crmBusinesstypeUserDto) ?
                ResultObj.success():
                ResultObj.error(ResultCode.ERROR_UNKNOWN);
    }
}

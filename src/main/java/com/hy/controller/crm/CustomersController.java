package com.hy.controller.crm;

import com.hy.common.ResultObj;
import com.hy.dto.CrmCustomersFetchDto;
import com.hy.enums.ResultCode;
import com.hy.service.crm.CustomersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;

@RestController
@RequestMapping("/crm")
public class CustomersController {

    @Autowired
    private CustomersService customersService;

    @PostMapping("/customer/add")
    public ResultObj addCustomer(CrmCustomersFetchDto crmCustomersFetchDto){
        try{
            return ResultObj.success(customersService.addCustomer(crmCustomersFetchDto));
        }catch (Exception e){
            return ResultObj.error(ResultCode.ERROR_ADD_FAILED);
        }
    }

    @PostMapping("/customer/set")
    public ResultObj setCustomer(CrmCustomersFetchDto crmCustomersFetchDto){
        return customersService.setCustomer(crmCustomersFetchDto)?
                ResultObj.success():
                ResultObj.error(ResultCode.ERROR_UPDATE_FAILED);
    }

    @PostMapping("/customer/del")
    public ResultObj delCustomer(int id){
        try{
            if(customersService.delCustomer(id)){
                return ResultObj.success();
            }
            else
            {
                return ResultObj.error(ResultCode.ERROR_DELETE_FAILED);
            }
        }catch (Exception e){
            return ResultObj.error(ResultCode.ERROR_DELETE_FOREIGN);
        }
    }

    @PostMapping("/customer/get")
    public ResultObj getCustomer(int page, int pageSize, @RequestParam(required = false) String value,
                                 @RequestParam(required = false) String sort,
                                 @RequestParam(required = false) String dir){
        HashMap<String, Object> map = new HashMap<>();

        //假如前端传回的排序号为btid.name,需要转化为btid
        try {
            if(sort.equals("btid.name")){
                sort = "btid";
            }
            else if(sort.equals("fid.name")){
                sort = "fid";
            }
        }catch (Exception e)
        {

        }
        map.put("data",customersService.getCrmCustomer(page, pageSize, value, sort, dir));
        map.put("total",customersService.getCrmCustomerTotal(value));
        return ResultObj.success(map);
    }
}

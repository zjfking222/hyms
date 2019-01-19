package com.hy.controller.crm;

import com.hy.common.ResultObj;
import com.hy.dto.CrmCustomersFetchDto;
import com.hy.enums.ResultCode;
import com.hy.service.crm.CustomersService;
import com.hy.utils.FileUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/crm")
public class CustomersController {

    @Autowired
    private CustomersService customersService;
    @Value("${upload.location}")
    private String location;
    private final String UPLOAD_URL;

    public CustomersController() {
        UPLOAD_URL = "/crm/upload/excel/";
    }

    @PostMapping("/customer/batchAdd")
    public ResultObj batchAddCustomer(@RequestParam("file") MultipartFile[] file){
        String reStr = FileUtil.upload(file, UPLOAD_URL, location);
        return reStr != null ? ResultObj.success(reStr) : ResultObj.error(ResultCode.ERROR_UPLOAD_FAILED);
    }

    @PostMapping("/customer/batchSubmit")
    public  ResultObj batchSubmitCustomer(String filename){
        int check = customersService.batchAddCustomer(filename);
        switch (check) {
            case 0:
                return ResultObj.error(ResultCode.ERROR_DATA_FAILED);
            case -1:
                return ResultObj.error(ResultCode.ERROR_UPLOAD_FAILED);
            case -2:
                return ResultObj.error(ResultCode.ERROR_ADD_FAILED);
            default:
                return ResultObj.success();
        }
    }

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
    public ResultObj getCustomer(@RequestParam(required = false) String filters, int page, int pageSize, @RequestParam(required = false) String value,
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
//            e.printStackTrace();
        }
        map.put("data", customersService.getCrmCustomer(filters, page, pageSize, value, sort, dir));
        map.put("total", customersService.getCrmCustomerTotal(filters, value));
        return ResultObj.success(map);
    }

    @PostMapping("/customerfirm/get")
    public ResultObj getCustomerFirmView(@RequestParam(required = false) String filters, int page, int pageSize, int mid,
                                         @RequestParam(required = false) String value,
                                         @RequestParam(required = false) String sort,
                                         @RequestParam(required = false) String dir){
        Map<String, Object> map = new HashMap<>();
        map.put("data", customersService.getCrmCustomerByUid(filters, page, pageSize, mid, value, sort, dir));
        map.put("total", customersService.getCrmCustomerByUidTotal(filters, mid, value));
        return ResultObj.success(map);
    }
}

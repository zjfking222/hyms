package com.hy.controller.crm;

import com.hy.common.ResultObj;
import com.hy.dto.CrmFirmsFetchDto;
import com.hy.enums.ResultCode;
import com.hy.service.crm.FirmsService;
import com.hy.utils.FileUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;

@RestController
@RequestMapping("/crm")
public class FirmsController {

    @Autowired
    private FirmsService firmsService;

    @Value("${upload.location}")
    private String location;
    private final String UPLOAD_URL;

    public FirmsController() {
        UPLOAD_URL = "/crm/upload/firm_excel/";
    }

    @PostMapping("/firm/batchAdd")
    public ResultObj batchAddFirms(@RequestParam("file") MultipartFile[] files) {
        String reStr = FileUtil.upload(files, UPLOAD_URL, location);
        return reStr != null ? ResultObj.success(reStr) : ResultObj.error(ResultCode.ERROR_UPLOAD_FAILED);
    }

    @PostMapping("/firm/batchSubmit")
    public ResultObj batchSubmitFirm(String filename) {
        int check = firmsService.batchAddFirm(filename);
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

    @PostMapping("/firm/add")
    public ResultObj addCrmFirm(CrmFirmsFetchDto crmFirmsDto) {
        try {
            return ResultObj.success(firmsService.addCrmFirm(crmFirmsDto));
        } catch (Exception e) {
            return ResultObj.error(ResultCode.ERROR_ADD_FAILED);
        }
    }

    @PostMapping("/firm/set")
    public ResultObj setCrmFirm(CrmFirmsFetchDto crmFirmsDto) {
        return firmsService.setCrmFirm(crmFirmsDto) ?
                ResultObj.success() :
                ResultObj.error(ResultCode.ERROR_UPDATE_FAILED);
    }

    @PostMapping("/firm/del")
    public ResultObj delCrmFirm(int id) {
        return firmsService.delCrmFirm(id) ?
                ResultObj.success() :
                ResultObj.error(ResultCode.ERROR_DELETE_FAILED);
    }

    @PostMapping("/firm/get")
    public ResultObj getCrmFirm(@RequestParam(required = false) String filters, int page, int pageSize, @RequestParam(required = false) String value,
                                @RequestParam(required = false) String sort,
                                @RequestParam(required = false) String dir) {
        HashMap<String, Object> map = new HashMap<>();

        //假如前端传回的排序号为btid.name,需要转化为btid
        try {
            if (sort.equals("btid.name")) {
                sort = "btid";
            }
        } catch (Exception e) {
//            e.printStackTrace();
        }

        map.put("data", firmsService.getCrmFirm(filters, page, pageSize, value, sort, dir));
        map.put("total", firmsService.getCrmFirmTotal(filters, value));
        return ResultObj.success(map);
    }

    @PostMapping("/firm/getByUid")
    public ResultObj getCrmFirmByUid() {
        return ResultObj.success(firmsService.getCrmFirmByUid());
    }

    @PostMapping("/firm/getByLike")
    public ResultObj getCrmFirmByLike(String value){
        return ResultObj.success(firmsService.getCrmFirmByLike(value));
    }

}

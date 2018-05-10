package com.hy.controller.qzgz;

import com.hy.common.ResultObj;
import com.hy.dto.BusinessTravelDto;
import com.hy.enums.ResultCode;
import com.hy.service.qzgz.BusinessTravelService;
import com.hy.utils.FileUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;

@RestController
@RequestMapping("/qzgz")
public class BusinessTravelController {

    @Autowired
    private BusinessTravelService businessTravelService;
    @Value("${upload.location}")
    private String location;
    private final String UPLOAD_URL="/qzgz/upload/images/";

    @PostMapping("/web/getBusinessTravel")
    public ResultObj getBusinessTravel()
    {
        return ResultObj
                .success(businessTravelService.getBusinessTravel());
    }
    @PostMapping("/admin/setImg")
    public ResultObj setImg(@RequestParam("file")MultipartFile[] file){
        String fileName = file[0].getOriginalFilename();
        try {
            File path = new File(ResourceUtils.getURL("file:").getPath()
                    + location + UPLOAD_URL);
            String filePath = path.getAbsolutePath();
            FileUtil.uploadFile(file[0].getBytes(), filePath, fileName);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ResultObj.success( UPLOAD_URL + fileName);
    }

    /**
     * 未使用
     */
    @PostMapping("/admin/addBusinessTravel")
    public ResultObj addBusinessTravel(String title,String img)
    {
        return businessTravelService.addBusinessTravel(title,img)?
                ResultObj.success():
                ResultObj.error(ResultCode.ERROR_INVALID_PARAMETER);
    }
    /**
     * 未使用
     */
    @PostMapping("/admin/delBusinessTravel")
    public ResultObj delBusinessTravel(int id)
    {
        return businessTravelService.delBusinessTravel(id)?
                ResultObj.success():
                ResultObj.error(ResultCode.ERROR_INVALID_PARAMETER);
    }
    @PostMapping("/admin/setBusinessTravel")
    public ResultObj setBusinessTravel(BusinessTravelDto businessTravelDto)
    {
        return businessTravelService.setBusinessTravel(businessTravelDto)?
                ResultObj.success():
                ResultObj.error(ResultCode.ERROR_INVALID_PARAMETER);
    }
}

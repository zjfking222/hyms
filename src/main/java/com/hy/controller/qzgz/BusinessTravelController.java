package com.hy.controller.qzgz;

import com.hy.common.ResultObj;
import com.hy.enums.ResultCode;
import com.hy.service.qzgz.BusinessTravelService;
import com.hy.utils.FileUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;

@RestController
public class BusinessTravelController {

    @Autowired
    private BusinessTravelService businessTravelService;
    private final String UPLOAD_URL="/files/qzgz/upload/images/";

    @PostMapping("/getBusinessTravel")
    public ResultObj getBusinessTravel()
    {
        return ResultObj
                .success(businessTravelService.getBusinessTravel());
    }
    @PostMapping("/setImg")
    public ResultObj setImg(@RequestParam("file")MultipartFile[] file){
        String fileName = file[0].getOriginalFilename();
        try {
            File path = new File(ResourceUtils.getURL("classpath:").getPath()
                    + "/static" + UPLOAD_URL);
            String filePath = path.getAbsolutePath();
            FileUtil.uploadFile(file[0].getBytes(), filePath, fileName);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ResultObj.success(UPLOAD_URL+fileName);
    }
    @PostMapping("/addBusinessTravel")
    public ResultObj addBusinessTravel(String title,String img)
    {
        return businessTravelService.addBusinessTravel(title,img)?
                ResultObj.success():
                ResultObj.error(ResultCode.ERROR_INVALID_PARAMETER);
    }
    @PostMapping("/delBusinessTravel")
    public ResultObj delBusinessTravel(int id)
    {
        return businessTravelService.delBusinessTravel(id)?
                ResultObj.success():
                ResultObj.error(ResultCode.ERROR_INVALID_PARAMETER);
    }
}

package com.hy.controller.qzgz;

import com.hy.common.ResultObj;
import com.hy.dto.BiPictureDto;
import com.hy.enums.ResultCode;
import com.hy.model.BiPicture;
import com.hy.service.qzgz.NewsBannerService;
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
import java.util.List;
import java.util.stream.IntStream;

@RestController
@RequestMapping("/qzgz")
public class NewsBannerController {
    @Autowired
    private NewsBannerService bannerService;
    @Value("${upload.location}")
    private String location;
    private final String UPLOAD_URL="/qzgz/upload/banners/";

    @PostMapping("/admin/getNewsBanner")
    //查询banner图
    public ResultObj getNewsBanner(){
        return ResultObj.success(bannerService.getNewsBanner());
    }

    @PostMapping("/admin/saveBanner")
    //保存banner图
    public ResultObj saveBanner(@RequestParam("file")MultipartFile[] file){
        try {
            String fileName = file[0].getOriginalFilename();
            String filePath = null;
            String url = UPLOAD_URL + fileName;
            boolean check = false;
            List<BiPictureDto> biPictures = bannerService.getNewsBanner();
            for (int i = 0; i < biPictures.size(); i++){
                if(biPictures.get(i).getUrl().equals(url)){
                    check = true;
                    break;
                }
            }
            if(!check){
                File path = new File(ResourceUtils.getURL("file:").getPath()
                        + location + UPLOAD_URL);
                filePath = path.getAbsolutePath();
                FileUtil.uploadFile(file[0].getBytes(), filePath, fileName);
                BiPictureDto biPictureDto = new BiPictureDto();
                biPictureDto.setUrl(url);
                biPictureDto.setPath(filePath + File.separator + fileName);
                return ResultObj.success(biPictureDto);
            }else {
                return ResultObj.error(ResultCode.ERROR_UPLOAD_FAILED);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ResultObj.error(ResultCode.ERROR_UPLOAD_FAILED);
        }
    }

    @PostMapping("/admin/addNewsBanner")
    //新增banner图
    public ResultObj addNewsBanner(BiPictureDto biPictureDto){
        return bannerService.addNewsBanner(biPictureDto) ?
                ResultObj.success() :
                ResultObj.error(ResultCode.ERROR_ADD_FAILED);
    }

    @PostMapping("/admin/delNewsBanner")
    public ResultObj delNewsBanner(int id){
        return bannerService.delNewsBanner(id) ?
                ResultObj.success() :
                ResultObj.error(ResultCode.ERROR_DELETE_FAILED);
    }
}

package com.hy.controller.qzgz;

import com.hy.common.ResultObj;
import com.hy.dto.BiPictureDto;
import com.hy.enums.ResultCode;
import com.hy.service.baseinfo.BiPictureService;
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

/**
 * @Auther: 沈超宇
 * @Date: 2018/12/22 11:09
 * @Description:衢州news_banner图controller
 */
@RestController
@RequestMapping("/qzgz")
public class NewsBannerController {
    @Autowired
    private BiPictureService bannerService;
    @Value("${upload.location}")
    private String location;
    private final String UPLOAD_URL="/qzgz/upload/";

    @PostMapping("/admin/getNewsBanner")
    //查询banner图
    public ResultObj getNewsBanner(String type){
        return ResultObj.success(bannerService.getNewsBanner(type));
    }

    private BiPictureDto codeFile(String code, MultipartFile[] file){
        try {
            String fileName = file[0].getOriginalFilename();
            String filePath = null;
            String url = UPLOAD_URL + code + fileName;
            File path = new File(ResourceUtils.getURL("file:").getPath()
                    + location + UPLOAD_URL + code);
            filePath = path.getAbsolutePath();
            FileUtil.uploadFile(file[0].getBytes(), filePath, fileName);
            BiPictureDto biPictureDto = new BiPictureDto();
            biPictureDto.setUrl(url);
            biPictureDto.setPath(filePath + File.separator + fileName);
            return biPictureDto;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @PostMapping("/admin/saveBannerActive")
    //保存动态新闻banner图
    public ResultObj saveBannerActive(@RequestParam("file")MultipartFile[] file){
        String dt = "dtBanners/";
        BiPictureDto biPictureDto = codeFile(dt, file);
        return biPictureDto != null ? ResultObj.success(biPictureDto) : ResultObj.error(ResultCode.ERROR_UPLOAD_FAILED);
    }

    @PostMapping("/admin/saveBannerFigure")
    //保存动态新闻banner图
    public ResultObj saveBannerFigure(@RequestParam("file")MultipartFile[] file){
        String rw = "rwBanners/";
        BiPictureDto biPictureDto = codeFile(rw, file);
        return biPictureDto != null ? ResultObj.success(biPictureDto) : ResultObj.error(ResultCode.ERROR_UPLOAD_FAILED);
    }

    @PostMapping("/admin/addNewsBanner")
    //新增banner图
    public ResultObj addNewsBanner(BiPictureDto biPictureDto){
        return bannerService.addNewsBanner(biPictureDto) ?
                ResultObj.success() :
                ResultObj.error(ResultCode.ERROR_ADD_FAILED);
    }

    @PostMapping("/admin/delNewsBanner")
    //删除banner图
    public ResultObj delNewsBanner(int id){
        return bannerService.delNewsBanner(id) ?
                ResultObj.success() :
                ResultObj.error(ResultCode.ERROR_DELETE_FAILED);
    }

    @PostMapping("/web/getNewsBanner")
    //web查询banner图
    public ResultObj getNewsBannerWeb(String type){
        return ResultObj.success(bannerService.getNewsBanner(type));
    }
}

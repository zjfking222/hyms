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
import java.util.List;

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

    @PostMapping("/admin/saveBannerActive")
    //保存动态新闻banner图
    public ResultObj saveBannerActive(@RequestParam("file")MultipartFile[] file){
        try {
            String dt = "dtBanners/";
            String fileName = file[0].getOriginalFilename();
            String filePath = null;
            String url = UPLOAD_URL + dt + fileName;
            boolean check = false;
            List<BiPictureDto> biPictures = bannerService.getNewsBanner("qzgz_news_active");
            for (int i = 0; i < biPictures.size(); i++){
                if(biPictures.get(i).getUrl().equals(url)){
                    check = true;
                    break;
                }
            }
            if(!check){
                File path = new File(ResourceUtils.getURL("file:").getPath()
                        + location + UPLOAD_URL + dt);
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

    @PostMapping("/admin/saveBannerFigure")
    //保存动态新闻banner图
    public ResultObj saveBannerFigure(@RequestParam("file")MultipartFile[] file){
        try {
            String rw = "rwBanners/";
            String fileName = file[0].getOriginalFilename();
            String filePath = null;
            String url = UPLOAD_URL + rw + fileName;
            boolean check = false;
            List<BiPictureDto> biPictures = bannerService.getNewsBanner("qzgz_news_figure");
            for (int i = 0; i < biPictures.size(); i++){
                if(biPictures.get(i).getUrl().equals(url)){
                    check = true;
                    break;
                }
            }
            if(!check){
                File path = new File(ResourceUtils.getURL("file:").getPath()
                        + location + UPLOAD_URL + rw);
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

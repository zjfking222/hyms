package com.hy.controller.qzgz;

import com.hy.common.ResultObj;
import com.hy.dto.BiPictureDto;
import com.hy.enums.ResultCode;
import com.hy.service.baseinfo.BiPictureService;
import com.hy.utils.FileUtil;
import org.apache.catalina.servlet4preview.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;

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

    /**
     * @Author 钱敏杰
     * @Description 可被跨域访问的二进制文件保存方法(暂时测试使用)
     * @Date 2019/1/5 9:05
     * @Param [fileName, request]
     * @return java.lang.String
     **/
    @CrossOrigin
    @PostMapping("/web/saveInputStream")
    //保存动态新闻banner图
    public ResultObj saveInputStream(@RequestParam("fileName") String fileName, HttpServletRequest request) {
        try {
            BufferedInputStream fileIn = new BufferedInputStream(request.getInputStream());
            String path = ResourceUtils.getURL("file:").getPath()
                    + location + UPLOAD_URL + fileName;
            path = path.substring(2);
            File file = new File(path);
            path = file.getAbsolutePath();
            System.out.println(path);
            file = new File(path);
            if(file.exists()){//删除原数据，更新为新的
                file.delete();
                file = new File(path);
            }
            BufferedOutputStream fileOut = new BufferedOutputStream(new FileOutputStream(file));
            byte[] buf = new byte[1024];
            while (true) {
                // 读取数据
                int bytesIn = fileIn.read(buf, 0, 1024);
                if (bytesIn == -1) {
                    break;
                } else {
                    fileOut.write(buf, 0, bytesIn);
                }
            }
            fileOut.flush();
            fileOut.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return ResultObj.success();
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

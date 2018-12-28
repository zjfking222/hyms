package com.hy.service.baseinfo;

import com.hy.common.SecurityUtil;
import com.hy.dto.BiPictureDto;
import com.hy.mapper.ms.BiPictureMapper;
import com.hy.model.BiPicture;
import com.hy.utils.DTOUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.List;

/**
 * @Auther: 沈超宇
 * @Date: 2018/12/22 10:40
 * @Description:衢州news_banner图serciceImpl
 */
@Service
public class BiPictureServiceImpl implements BiPictureService {
    @Autowired
    private BiPictureMapper newsBannerMapper;

    @Override
    //查询banner图
    public List<BiPictureDto> getNewsBanner() {
        return DTOUtil.populateList(newsBannerMapper.selectNewsBanner(), BiPictureDto.class);
    }

    @Override
    //新增banner图
    public boolean addNewsBanner(BiPictureDto bannerDto) {
        BiPicture banner = DTOUtil.populate(bannerDto, BiPicture.class);
        banner.setType("qzgz_xw_banner");
        banner.setCreater(SecurityUtil.getLoginid());
        banner.setModifier(SecurityUtil.getLoginid());
        return newsBannerMapper.insertNewsBanner(banner) == 1;
    }

    @Override
    //删除banner图
    public boolean delNewsBanner(int id) {
        String path = newsBannerMapper.selectNewsBannerById(id).getPath();
        boolean check = false;
        try{
            File file = new File(path);
            if(file.exists()){
                check = file.delete();
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        if(check){
            return newsBannerMapper.deleteNewsBanner(id) == 1;
        }else {
            return false;
        }
    }
}

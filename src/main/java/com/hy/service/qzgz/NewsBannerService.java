package com.hy.service.qzgz;

import com.hy.dto.BiPictureDto;

import java.util.List;

/**
 * @Auther: 沈超宇
 * @Date: 2018/12/22 9:56
 * @Description:衢州news_banner增删查service
 */
public interface NewsBannerService {
    //查询banner图
    List<BiPictureDto> getNewsBanner();
    //新增banner图
    boolean addNewsBanner(BiPictureDto biPictureDto);
    //删除banner图
    boolean delNewsBanner(int id);
}

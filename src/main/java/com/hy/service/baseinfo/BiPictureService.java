package com.hy.service.baseinfo;

import com.hy.dto.BiPictureDto;

import java.util.List;

/**
 * @Auther: 沈超宇
 * @Date: 2018/12/22 9:56
 * @Description:BiPicture表service
 */
public interface BiPictureService {
    //查询banner图
    List<BiPictureDto> getNewsBanner();
    //新增banner图
    boolean addNewsBanner(BiPictureDto biPictureDto);
    //删除banner图
    boolean delNewsBanner(int id);
}

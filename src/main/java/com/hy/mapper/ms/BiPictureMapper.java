package com.hy.mapper.ms;

import com.hy.model.BiPicture;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Auther: 沈超宇
 * @Date: 2018/12/22 15:10
 * @Description:衢州公众news_banner表增删查
 */
@Repository
public interface BiPictureMapper {
    //查询banner图
    List<BiPicture> selectNewsBanner(@Param("type") String type);
    //新增banner图
    Integer insertNewsBanner(BiPicture biPicture);
    //删除banner图
    Integer deleteNewsBanner(@Param("id") int id);
    //根据ID查询banner图信息
    BiPicture selectNewsBannerById(@Param("id") int id);
}

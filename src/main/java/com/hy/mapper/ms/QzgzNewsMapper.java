package com.hy.mapper.ms;

import com.hy.model.QzgzNews;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Auther: 沈超宇
 * @Date: 2018/12/22 9:03
 * @Description:衢州公众news表增删改查
 */
@Repository
public interface QzgzNewsMapper {
    //查询新闻信息
    List<QzgzNews> selectNews(@Param("value") String value, @Param("sort") String sort, @Param("dir") String dir);
    //查询新闻信息总条数，用于分页
    Integer selectNewsTotal(@Param("value") String value);
    //新增新闻
    Integer insertNews(QzgzNews qzgzNews);
    //修改新闻信息
    Integer updateNews(QzgzNews qzgzNews);
    //删除新闻
    Integer deleteNews(@Param("id") int id);
    //查询新闻类型
    List<QzgzNews> selectNewsType();
    //根据新闻类型查询新闻并分页
    List<QzgzNews> selectNewsByType(@Param("type") String type, @Param("num") int num);
    //根据新闻类型查询新闻总数
    Integer selectNewsByTypeTotal(@Param("type") String type);
}

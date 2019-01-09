package com.hy.service.qzgz;

import com.hy.dto.QzgzNewsDto;

import java.util.List;

/**
 * @Auther: 沈超宇
 * @Date: 2018/12/22 9:56
 * @Description:衢州新闻service
 */
public interface NewsService {
    //查询新闻
    List<QzgzNewsDto> getNews(String value, String sort, String dir, int page, int pageSize, String code);
    //查询新闻总条数，用于分页
    Integer getNewsTotal(String value, String code);
    //删除新闻类型时根据type查询是否有新闻
    Integer getNewsTypeDel(int type);
    //新增新闻
    boolean addNews(QzgzNewsDto qzgzNewsDto);
    //修改新闻信息
    boolean setNews(QzgzNewsDto qzgzNewsDto);
    //删除新闻
    boolean delNews(int id);
    //查询新闻类型
    List<QzgzNewsDto> getNewsType(String code);
    //根据新闻类型查询新闻并分页
    List<QzgzNewsDto> getNewsByType(int type, int num);
    //根据新闻类型查询新闻总数
    Integer getNewsByTypeTotal(int type);
}

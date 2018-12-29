package com.hy.service.qzgz.impl;

import com.github.pagehelper.PageHelper;
import com.hy.common.SecurityUtil;
import com.hy.dto.QzgzNewsDto;
import com.hy.mapper.ms.QzgzNewsMapper;
import com.hy.model.QzgzNews;
import com.hy.service.qzgz.NewsService;
import com.hy.utils.DTOUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Auther: 沈超宇
 * @Date: 2018/12/22 10:10
 * @Description:衢州新闻serviceImpl
 */
@Service
public class NewsServiceImpl implements NewsService{
    @Autowired
    private QzgzNewsMapper qzgzNewsMapper;

    @Override
    //查询新闻信息
    public List<QzgzNewsDto> getNews(String value, String sort, String dir, int page, int pageSize){
        PageHelper.startPage(page, pageSize);
        List<QzgzNews> qzgzNews = qzgzNewsMapper.selectNews(value, sort, dir);
        return DTOUtil.populateList(qzgzNews, QzgzNewsDto.class);
    }

    @Override
    //查询新闻总条数，用于分页
    public Integer getNewsTotal(String value){
        return qzgzNewsMapper.selectNewsTotal(value);
    }

    @Override
    //新增新闻
    public boolean addNews(QzgzNewsDto qzgzNewsDto){
       QzgzNews qzgzNews = DTOUtil.populate(qzgzNewsDto, QzgzNews.class);
       qzgzNews.setCreater(SecurityUtil.getLoginid());
       qzgzNews.setModifier(SecurityUtil.getLoginid());
       return qzgzNewsMapper.insertNews(qzgzNews) == 1;
    }

    @Override
    //修改新闻信息
    public boolean setNews(QzgzNewsDto qzgzNewsDto){
        QzgzNews qzgzNews = DTOUtil.populate(qzgzNewsDto, QzgzNews.class);
        qzgzNews.setModifier(SecurityUtil.getLoginid());
        return qzgzNewsMapper.updateNews(qzgzNews) == 1;
    }

    @Override
    //删除新闻
    public boolean delNews(int id){
        return qzgzNewsMapper.deleteNews(id) == 1;
    }

    @Override
    //查询新闻类型
    public List<QzgzNewsDto> getNewsType(){
        return DTOUtil.populateList(qzgzNewsMapper.selectNewsType(), QzgzNewsDto.class);
    }
    //根据新闻类型查询新闻
    public List<QzgzNewsDto> getNewsByType(String type, int num){
        return DTOUtil.populateList(qzgzNewsMapper.selectNewsByType(type, num), QzgzNewsDto.class);
    }
    //根据新闻类型查询新闻总数
    public Integer getNewsByTypeTotal(String type){
        return qzgzNewsMapper.selectNewsByTypeTotal(type);
    }
}

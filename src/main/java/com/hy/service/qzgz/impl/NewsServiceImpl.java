package com.hy.service.qzgz.impl;

import com.github.pagehelper.PageHelper;
import com.hy.common.SecurityUtil;
import com.hy.dto.QzgzNewsDto;
import com.hy.mapper.ms.QzgzNewsMapper;
import com.hy.mapper.ms.SysDictMapper;
import com.hy.model.QzgzNews;
import com.hy.model.SysDict;
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
    @Autowired
    private SysDictMapper sysDictMapper;

    @Override
    //查询动态新闻信息
    public List<QzgzNewsDto> getNews(String filters, String value, String sort, String dir, int page, int pageSize, String code){
        PageHelper.startPage(page, pageSize);
        List<QzgzNews> qzgzNews = qzgzNewsMapper.selectNews(filters, value, sort, dir, code);
        return DTOUtil.populateList(qzgzNews, QzgzNewsDto.class);
    }

    @Override
    //查询动态新闻总条数，用于分页
    public Integer getNewsTotal(String filters, String value, String code){
        return qzgzNewsMapper.selectNewsTotal(filters, value, code);
    }

    @Override
    //删除新闻类型时根据type查询是否有新闻
    public Integer getNewsTypeDel(int type){
        return qzgzNewsMapper.selectNewTypeDel(type);
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
    public List<QzgzNewsDto> getNewsType(String code){
        List<SysDict> sysDict = sysDictMapper.selectChildByCode(code);
        return DTOUtil.populateList(qzgzNewsMapper.selectNewsType(sysDict), QzgzNewsDto.class);
    }
    //根据新闻类型查询新闻
    public List<QzgzNewsDto> getNewsByType(int type, int num){
        return DTOUtil.populateList(qzgzNewsMapper.selectNewsByType(type, num), QzgzNewsDto.class);
    }
    //根据新闻类型查询新闻总数
    public Integer getNewsByTypeTotal(int type){
        return qzgzNewsMapper.selectNewsByTypeTotal(type);
    }
}

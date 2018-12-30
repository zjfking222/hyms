package com.hy.service.system;

import com.hy.dto.SysDictDto;

import java.util.List;

/**
 * @Auther: 沈超宇
 * @Date: 2018/12/27 14:49
 * @Description:基础信息数据字典表service
 */
public interface SysDictService {
    //查询衢州新闻类型（未删除）
    List<SysDictDto> getNewsType();
    //新增衢州新闻类型
    boolean addNewsType(SysDictDto sysDictDto);
    //修改衢州新闻类型
    boolean setNewsType(SysDictDto sysDictDto);
    //删除衢州新闻类型
    boolean delNewsType(int id);
}

package com.hy.mapper.ms;

import com.hy.model.SysDict;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Auther: 沈超宇
 * @Date: 2018/12/27 14:19
 * @Description:基础信息数据字典表mapper
 */
@Repository
public interface SysDictMapper {
    //查询衢州新闻类型父id
    SysDict selectQzgzNewsTypeId();
    //查询衢州新闻类型
    List<SysDict> selectQzgzNewsType(@Param("pid") int pid);
    //新增衢州新闻类型
    Integer insertQzgzNewsType(SysDict sysDict);
    //修改衢州新闻类型
    Integer updateQzgzNewsType(SysDict sysDict);
    //删除时将删除标记改成1
    Integer deleteQzgzNewsType(@Param("id") int id);
}

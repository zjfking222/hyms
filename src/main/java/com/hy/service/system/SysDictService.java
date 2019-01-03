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

    /**
     * @Author 钱敏杰
     * @Description 根据条件分页查询数据字典
     * @Date 2018/12/30 10:19
     * @Param [pageNum, pageSize, sort, dir, value]
     * @return java.util.List<com.hy.dto.SysDictDto>
     **/
    List<SysDictDto> getDictsPage(Integer pageNum, Integer pageSize, String sort, String dir, String value);

    /**
     * @Author 钱敏杰
     * @Description 根据条件统计全部数据量
     * @Date 2018/12/30 10:21
     * @Param [value]
     * @return java.lang.Integer
     **/
    Integer getDictCount(String value);

    /**
     * @Author 钱敏杰
     * @Description 在父节点下根据查询条件查询子节点
     * @Date 2018/12/29 14:06
     * @Param [pid, value]
     * @return java.util.List<com.hy.model.SysDict>
     **/
    List<SysDictDto> getDictChildren(Integer pid, String value);

    /**
     * @Author 钱敏杰
     * @Description 检查当前编码是否重复，若重复则返回true
     * @Date 2018/12/30 14:49
     * @Param [code, id]
     * @return boolean
     **/
    boolean checkCode(String code, Integer id);

    /**
     * @Author 钱敏杰
     * @Description 更新数据字典节点数据
     * @Date 2018/12/29 15:35
     * @Param [sysDictDto]
     * @return void
     **/
    void updateSysDict(SysDictDto sysDictDto);

    /**
     * @Author 钱敏杰
     * @Description 添加数据字典节点数据
     * @Date 2019/1/3 14:12
     * @Param [sysDictDto]
     * @return void
     **/
    void addSysDict(SysDictDto sysDictDto);

    /**
     * @Author 钱敏杰
     * @Description 删除数据字典数据
     * @Date 2018/12/29 16:21
     * @Param [id]
     * @return void
     **/
    void deleteSysDict(Integer id);

    /**
     * @Author 钱敏杰
     * @Description 根据编码查询当前节点的全部子节点
     * @Date 2018/12/30 16:35
     * @Param [code]
     * @return java.util.List<com.hy.dto.SysDictDto>
     **/
    List<SysDictDto> selectByCode(String code);
}

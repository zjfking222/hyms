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

    /**
     * @Author 钱敏杰
     * @Description 按查询条件分页查询数据字典数据
     * @Date 2018/12/30 10:12
     * @Param [sort, dir, value]
     * @return java.util.List<com.hy.model.SysDict>
     **/
    List<SysDict> selectDictsPage(@Param("filters")String filters, @Param("sort")String sort, @Param("dir")String dir, @Param("value")String value);

    /**
     * @Author 钱敏杰
     * @Description 统计数据字典数据量
     * @Date 2019/1/10 16:44
     * @Param [filters, value]
     * @return java.lang.Integer
     **/
    Integer countDicts(@Param("filters") String filters, @Param("value") String value);

    /**
     * @Author 钱敏杰
     * @Description 在父节点下根据查询条件查询子节点
     * @Date 2018/12/29 14:06
     * @Param [pid, value]
     * @return java.util.List<com.hy.model.SysDict>
     **/
    List<SysDict> selectChildren(@Param("pid") Integer pid, @Param("value") String value);

    /**
     * @Author 钱敏杰
     * @Description 添加单个数据字典对象
     * @Date 2018/12/29 15:27
     * @Param [sysDict]
     * @return int
     **/
    int insert(SysDict sysDict);

    /**
     * @Author 钱敏杰
     * @Description 更新单个数据字典数据
     * @Date 2018/12/29 15:42
     * @Param [sysDict]
     * @return int
     **/
    int updateByPrimaryKey(SysDict sysDict);

    /**
     * @Author 钱敏杰
     * @Description 删除数据字典数据
     * @Date 2018/12/29 16:19
     * @Param [id]
     * @return int
     **/
    int deleteByPrimaryKey(@Param("id") Integer id);

    /**
     * @Author 钱敏杰
     * @Description 更新数据字典非空数据
     * @Date 2018/12/29 17:19
     * @Param [sysDict]
     * @return int
     **/
    int updateByPrimaryKeySelective(SysDict sysDict);

    /**
     * @Author 钱敏杰
     * @Description 检查当前数据字典编码是否重复
     * @Date 2018/12/30 14:45
     * @Param [code, id]
     * @return int
     **/
    int countrepeat(@Param("code") String code, @Param("id") Integer id);

    /**
     * @Author 钱敏杰
     * @Description 更新子节点中冗余的父节点名称
     * @Date 2018/12/30 15:57
     * @Param [pidname, pid]
     * @return int
     **/
    int updatePidname(@Param("pidname") String pidname, @Param("pid") Integer pid);

    /**
     * @Author 钱敏杰
     * @Description 根据编码查询全部子节点数据
     * @Date 2018/12/30 16:16
     * @Param [code]
     * @return java.util.List<com.hy.model.SysDict>
     **/
    List<SysDict> selectChildByCode(@Param("code") String code);

    /**
     * @Author 钱敏杰
     * @Description 根据编码查询当前节点
     * @Date 2018/12/30 16:31
     * @Param [code]
     * @return java.util.List<com.hy.model.SysDict>
     **/
    SysDict selectByCode(@Param("code") String code);
}

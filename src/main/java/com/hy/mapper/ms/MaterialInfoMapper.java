package com.hy.mapper.ms;

import com.hy.model.MaterialInfo;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Auther: 钱敏杰
 * @Date: 2019/1/22 8:42
 * @Description:物资信息表数据库操作
 */
@Repository
public interface MaterialInfoMapper extends BaseMapper<Integer, MaterialInfo>{

    /**
     * @Author 钱敏杰
     * @Description 分页查询物资信息数据
     * @Date 2019/1/22 16:50
     * @Param [filters, sort, dir, value]
     * @return java.util.List<com.hy.model.MaterialInfo>
     **/
    List<MaterialInfo> selectMaterialInfoPage(@Param("filters")String filters, @Param("sort")String sort, @Param("dir")String dir, @Param("value")String value, @Param("state")String state);

    /**
     * @Author 钱敏杰
     * @Description 统计数据量
     * @Date 2019/1/22 17:01
     * @Param [filters, value]
     * @return int
     **/
    int selectMaterialInfoTotal(@Param("filters")String filters, @Param("value")String value, @Param("state")String state);

    /**
     * @Author 钱敏杰
     * @Description 批量插入物资信息
     * @Date 2019/1/22 10:49
     * @Param [list]
     * @return int
     **/
    int insertBatch(List<MaterialInfo> list);

    //根据id查询物资信息
    MaterialInfo selectByPrimaryKey(@Param("id") int id);

    //查询所有业务员
    List<String> selectEmpnumAll();

    //跟单员只能查看自己对应业务员的物资信息
    List<MaterialInfo> selectInfoByTracer(@Param("tracernum") String tracernum, @Param("filters")String filters, @Param("sort")String sort, @Param("dir")String dir, @Param("value")String value, @Param("state")String state);

    //统计跟单员对应业务员的物资信息的数量
    int selectInfoByTracerTotal(@Param("tracernum") String tracernum, @Param("filters")String filters, @Param("value")String value, @Param("state")String state);
}

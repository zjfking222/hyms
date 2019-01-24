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
    List<MaterialInfo> selectMaterialInfoPage(@Param("filters")String filters, @Param("sort")String sort, @Param("dir")String dir, @Param("value")String value);

    /**
     * @Author 钱敏杰
     * @Description 统计数据量
     * @Date 2019/1/22 17:01
     * @Param [filters, value]
     * @return int
     **/
    int selectMaterialInfoTotal(@Param("filters")String filters, @Param("value")String value);

    /**
     * @Author 钱敏杰
     * @Description 批量插入物资信息
     * @Date 2019/1/22 10:49
     * @Param [list]
     * @return int
     **/
    int insertBatch(List<MaterialInfo> list);
}

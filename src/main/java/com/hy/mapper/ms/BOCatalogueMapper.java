package com.hy.mapper.ms;

import com.hy.model.BOCatalogue;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Auther: 钱敏杰
 * @Date: 2018/12/7 13:42
 * @Description:
 */
@Repository
public interface BOCatalogueMapper {

    /**
     * @Author 钱敏杰
     * @Description 取出全部目录数据
     * @Date 2018/12/7 13:44
     * @Param []
     * @return java.util.List<com.hy.model.ReportCatalogue>
     **/
    List<BOCatalogue> selectAll();

    /**
     * @Author 钱敏杰
     * @Description 新增目录数据
     * @Date 2018/12/17 9:24
     * @Param [catalogue]
     * @return int
     **/
    int insertSelective(BOCatalogue catalogue);

    /**
     * @Author 钱敏杰
     * @Description 更新目录数据
     * @Date 2018/12/17 9:29
     * @Param [catalogue]
     * @return int
     **/
    int updateByPrimaryKeySelective(BOCatalogue catalogue);

    /**
     * @Author 钱敏杰
     * @Description 根据主键删除目录数据
     * @Date 2018/12/17 16:01
     * @Param [id]
     * @return int
     **/
    int deleteByPrimaryKey(Integer id);
}

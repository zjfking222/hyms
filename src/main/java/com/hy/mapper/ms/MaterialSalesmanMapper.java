package com.hy.mapper.ms;

import com.hy.model.MaterialSalesman;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Auther: 沈超宇
 * @Date: 2019/1/21 14:58
 * @Description:业务员表mapper
 */
@Repository
public interface MaterialSalesmanMapper {
    //查询业务员信息
    List<MaterialSalesman> selectSalesman(@Param("value") String value);
    //新增业务员信息
    Integer insertSalesman(MaterialSalesman purchaseSalesman);
    //根据id删除对应业务员
    Integer deleteSalesman(@Param("id") int id);
}

package com.hy.mapper.ms;

import com.hy.model.MaterialTracer;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Auther: 沈超宇
 * @Date: 2019/1/22 10:47
 * @Description:跟单员mapper
 */
@Repository
public interface MaterialTracerMapper {
    //查询对应业务员下的跟单员
    List<MaterialTracer> selectTracer(@Param("filters") String filters, @Param("sid") int sid, @Param("value") String value);
    //新增跟单员
    Integer insertTracer(MaterialTracer purchaseTracer);
    //删除跟单员
    Integer deleteTracer(@Param("id") int id);
    //根据sid删除所有该业务员下的跟单员
    Integer deleteTracerBySid(@Param("sid") int sid);
}

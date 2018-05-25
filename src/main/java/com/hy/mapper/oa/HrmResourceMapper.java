package com.hy.mapper.oa;

import com.hy.model.HrmResource;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface HrmResourceMapper {
    HrmResource findByLoginId(@Param("loginid") String loginid);
    List<HrmResource> selectHrByLike(@Param("loginid")String loginid,
                                     @Param("lastname")String lastname);
}
package com.hy.mapper.oa;

import com.hy.model.HrmResource;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

@Component
public interface HrmResourceMapper {
    HrmResource findByLoginId(@Param("loginid") String loginid);
}
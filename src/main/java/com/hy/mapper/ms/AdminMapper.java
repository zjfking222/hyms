package com.hy.mapper.ms;

import com.hy.model.Admin;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface AdminMapper {
    Admin selectAdminByUsername (String username);
    Integer updateToken(@Param("token")String token, @Param("username")String username);
    Integer selectToken(@Param("token")String token);
}

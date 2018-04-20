package com.hy.mapper.ms;

import com.hy.model.QzgzAdmin;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface QzgzAdminMapper {
    QzgzAdmin selectAdminByUsername (String username);
    Integer updateToken(@Param("token")String token, @Param("username")String username);
    Integer selectToken(@Param("token")String token);
}

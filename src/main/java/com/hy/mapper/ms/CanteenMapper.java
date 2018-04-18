package com.hy.mapper.ms;

import com.hy.model.Canteen;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CanteenMapper {
    List<Canteen> selectCanteen(@Param("state") int state,
                                @Param("startRow") int startRow,
                                @Param("number") int number);
    Integer selectCountOfCanteen(@Param("state") int state);
}

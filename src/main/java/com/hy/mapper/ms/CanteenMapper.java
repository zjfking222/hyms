package com.hy.mapper.ms;

import com.hy.dto.CanteenDto;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CanteenMapper {
    List<CanteenDto> selectCanteen(@Param("state") int state);
}

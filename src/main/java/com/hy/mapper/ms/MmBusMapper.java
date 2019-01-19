package com.hy.mapper.ms;

import com.hy.model.MmBus;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MmBusMapper {
    List<MmBus> selectMmBus();
    int insertMmBus(MmBus mmBus);
    int updateMmBus(MmBus mmBus);
    int deleteMmBus(MmBus mmBus);
    List<MmBus> selectAllMmBus(@Param("filters")String filters, @Param("value") String value, @Param("sort") String sort,
                               @Param("dir") String dir, @Param("mid") int mid);
    int selectCountMmBus(@Param("filters")String filters, @Param("value") String value, @Param("mid") int mid);
    List<MmBus> selectInfoMmBus(@Param("mid") int mid);
}

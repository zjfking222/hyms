package com.hy.mapper.ms;

import com.hy.model.BiHotel;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface BiHotelMapper {
    List<BiHotel> selectBiHotel();
    int insertBiHotel(BiHotel biHotel);
    List<BiHotel> selectListBiHotel(@Param("startLine") int startLine,@Param("pageSize") int pageSize);
    int selectCountBiHotel();
    int updateBiHotel(BiHotel biHotel);
    int deleteBiHotel(BiHotel biHotel);
    List<BiHotel> selectAllBiHotel(@Param("filters")String filters, @Param("value") String value, @Param("sort") String sort,
                                   @Param("dir") String dir);
    Integer selectTotalBiHotel(@Param("filters")String filters, @Param("value") String value);
}

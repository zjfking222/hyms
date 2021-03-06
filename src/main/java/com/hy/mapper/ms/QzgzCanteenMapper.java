package com.hy.mapper.ms;

import com.hy.model.QzgzCanteen;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QzgzCanteenMapper {
    List<QzgzCanteen> selectCanteenToday(@Param("meal") int meal);
    Integer updateCanteenZan(QzgzCanteen canteen);
    List<QzgzCanteen> selectCanteen(@Param("filters")String filters, @Param("value") String value, @Param("date") String date, @Param("meal") Integer meal, @Param("sort") String sort, @Param("dir") String dir);
    Integer selectCanteenTotal(@Param("filters")String filters, @Param("value") String value, @Param("date") String date, @Param("meal") Integer meal);
    Integer deleteCanteen(@Param("id") int id);
    Integer insertCanteenList(List<QzgzCanteen> qzgzCanteens);
    List<QzgzCanteen> selectCanteenAll(@Param("maxDate") String maxDate, @Param("minDate") String minDate);
//    List<QzgzCanteen> selectCanteen(@Param("state") String state,
//                                    @Param("startRow") int startRow,
//                                    @Param("number") int number);
//    Integer selectCountOfCanteen(@Param("state") String state);
//    Integer insertCanteen(QzgzCanteen canteen);
//    Integer updateCanteenById(QzgzCanteen canteen);
//    Integer updateCanteenState(QzgzCanteen canteen);
//    List<QzgzCanteen> selectCanteenByName(@Param("name") String name,
//                                          @Param("state") String state);
//    QzgzCanteen selectCanteenById(int id);
}

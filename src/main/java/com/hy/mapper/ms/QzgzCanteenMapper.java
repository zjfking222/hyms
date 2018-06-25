package com.hy.mapper.ms;

import com.hy.model.QzgzCanteen;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QzgzCanteenMapper {
    List<QzgzCanteen> selectCanteen(@Param("state") String state,
                                    @Param("startRow") int startRow,
                                    @Param("number") int number);
    Integer selectCountOfCanteen(@Param("state") String state);
    Integer insertCanteen(QzgzCanteen canteen);
    Integer updateCanteenById(QzgzCanteen canteen);
    Integer updateCanteenState(QzgzCanteen canteen);
    List<QzgzCanteen> selectCanteenByName(@Param("name") String name,
                                          @Param("state") String state);
    QzgzCanteen selectCanteenById(int id);
}

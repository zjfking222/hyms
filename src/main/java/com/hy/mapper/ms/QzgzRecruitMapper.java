package com.hy.mapper.ms;

import com.hy.model.QzgzRecruit;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QzgzRecruitMapper {
    List<QzgzRecruit> selectRecruit(@Param("startRow")int startRow,
                                    @Param("number")int number);
    Integer selectCountOfRecruit();

    Integer insertRecruit(QzgzRecruit qzgzRecruit);

    Integer updateRecruit(QzgzRecruit qzgzRecruit);

    Integer deleteRecruit(int id);

    QzgzRecruit selectRecruitById(@Param("id") int id);
}

package com.hy.mapper.ms;

import com.hy.model.QzgzOpinion;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QzgzOpinionMapper {
    Integer insertOpinion(QzgzOpinion opinion);
    List<QzgzOpinion> selectOpinion(@Param("state")String state,
                                    @Param("startRow")int startRow,
                                    @Param("number")int number);
    Integer selectCountOfOpinion(@Param("state")String state);
    Integer updateStateOfOpinion(QzgzOpinion opinion);
}

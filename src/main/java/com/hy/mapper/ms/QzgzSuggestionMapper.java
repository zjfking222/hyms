package com.hy.mapper.ms;


import com.hy.dto.SuggestionDto;
import com.hy.model.QzgzSuggestion;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QzgzSuggestionMapper {
    Integer insertSuggestion(QzgzSuggestion qzgzSuggestion);
    List<QzgzSuggestion> selectSuggestion(@Param("state")String state,
                                    @Param("startRow")int startRow,
                                    @Param("number")int number);
    Integer selectCountOfSuggestion(@Param("state")String state);
    Integer updateStateOfSuggestion(QzgzSuggestion qzgzSuggestion);
}


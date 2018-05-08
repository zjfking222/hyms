package com.hy.mapper.ms;


import com.hy.dto.SuggestionDto;
import com.hy.model.QzgzSuggestion;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QzgzSuggestionMapper {
    boolean insertSuggestion(QzgzSuggestion suggestion);
    List<SuggestionDto> getSuggestion(@Param("pageNum") int pageNum);
    boolean deleteSuggestion(@Param("id") int id);
    boolean updateSuggestion(QzgzSuggestion suggestion);
    int totalPageS();
}


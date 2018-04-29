package com.hy.service.qzgz;

import com.hy.dto.SuggestionDto;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface SuggestionService {
    boolean insertSuggestion(SuggestionDto suggestionDto);
    List<SuggestionDto> getSuggestion();
    boolean deleteSuggestion(@Param("id") int state);
    boolean updateSuggestion(SuggestionDto suggestionDto);
}

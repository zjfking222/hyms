package com.hy.mapper.ms;

import com.hy.dto.SuggestionDto;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QzgzSuggestionMapper {
    List<SuggestionDto> selectSuggestion(int state);
}

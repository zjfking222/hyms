package com.hy.mapper.ms;

import com.hy.dto.SuggestionDto;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SuggestionMapper {
    List<SuggestionDto> selectSuggestion(int state);
}

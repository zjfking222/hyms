package com.hy.mapper.ms;


import com.hy.model.QzgzSuggestion;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QzgzSuggestionMapper {
    int insertSuggestion(QzgzSuggestion suggestion);
}
